package org.example.loldemoserver.champions.in;

import org.example.loldemoserver.champions.out.ChampionListRecordDto;
import org.example.loldemoserver.common.RiotApiException;
import org.example.loldemoserver.config.RiotApiConfig;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestClient;
import tools.jackson.databind.ObjectMapper;

import java.util.LinkedHashMap;

@Service
public class ChampionFetchingService {


    private RiotApiConfig riotApiConfig;
    private final RestClient restClient;
    private final ObjectMapper objectMapper;

    public ChampionFetchingService(RiotApiConfig riotApiConfig, RestClient.Builder restClientBuilder) {
        this.riotApiConfig = riotApiConfig;
        this.restClient = restClientBuilder.baseUrl(riotApiConfig.getDataDragonBaseUrl()).build();
        this.objectMapper = new ObjectMapper();
    }

    /**
     * get all champions
     * @return
     */
    public ChampionListRecordDto getChampionResponse() {
        //https://ddragon.leagueoflegends.com/cdn/16.18.1/data/en_US/champion.json
        return this.restClient.get()
                .uri("{version}/data/{language}/champion.json",this.riotApiConfig.getApiDefaultVersion(),this.riotApiConfig.getApiDefaultLanguage())
                .retrieve()
                .body(ChampionListRecordDto.class);
    }

    public ChampionInResponseDto getChampion(String name) throws RiotApiException {
        //https://ddragon.leagueoflegends.com/cdn/16.18.1/data/en_US/champion/Aatrox.json
        var apiResponse =  this.restClient.get()
                .uri("{version}/data/{language}/champion/{name}.json",this.riotApiConfig.getApiDefaultVersion(),this.riotApiConfig.getApiDefaultLanguage(),name)
                .retrieve()
                .onStatus(httpStatusCode -> httpStatusCode.value() == 404,
                        (request, response) -> {
                            throw new RiotApiException(String.format("Cannot find champion: %s", name));
                        })
                .onStatus(httpStatusCode -> httpStatusCode.is4xxClientError() || httpStatusCode.is5xxServerError(),
                        (request, response) -> {
                            throw new RiotApiException(String.format("Riot API Error - Status %d: %s",
                                    response.getStatusCode().value(), response.getStatusText()));
                        })
                .body(ChampionListRecordDto.class);
        if(apiResponse != null) {
            LinkedHashMap<String, Object>  dataMap = (LinkedHashMap<String, Object>) apiResponse.data().get(name);
            if(dataMap != null) {
                return this.objectMapper.convertValue(dataMap, ChampionInResponseDto.class);
            } else {
                return null;
            }
        } else {
            throw new RiotApiException(String.format("Cannot find champion:%s",name));
        }
    }
}
