package org.example.loldemoserver.champions.out;

import lombok.RequiredArgsConstructor;
import org.example.loldemoserver.champions.in.ChampionFetchingService;
import org.example.loldemoserver.champions.in.ChampionInResponseDto;
import org.example.loldemoserver.champions.in.ChampionSkinInDto;
import org.example.loldemoserver.config.RiotApiConfig;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * Service that handle the logic to convert response from Riot Api to custom api
 */
@Service
@RequiredArgsConstructor
public class ChampionsService {

    private final  ChampionFetchingService championFetchingService;
    private final RiotApiConfig riotApiConfig;

    public ChampionListRecordDto getAllChampions() {
        return this.championFetchingService.getChampionResponse();
    }

    public ChampionOutResponseDto getChampionByName(String name) {
        ChampionInResponseDto inResponseDto = this.championFetchingService.getChampionByName(name);
        return ChampionOutResponseDto
                .builder()
                .version(inResponseDto.version())
                .id(inResponseDto.id())
                .key(inResponseDto.key())
                .name(inResponseDto.name())
                .title(inResponseDto.title())
                .blurb(inResponseDto.blurb())
                .lore(inResponseDto.lore())
                .info(inResponseDto.info())
                .stats(inResponseDto.stats())
                .skins(fromSkinInToSkinOutDtos(inResponseDto.name(),inResponseDto.skins()))
                .tags(inResponseDto.tags())
                .allytips(inResponseDto.allytips())
                .ennemytips(inResponseDto.ennemytips())
                .partype(inResponseDto.partype())
                .build();
    }

    private List<ChampionSkinOutDto> fromSkinInToSkinOutDtos(String name,List<ChampionSkinInDto> inDtos) {
     return  inDtos.stream().map(inDto -> {
         final String formattedFileUrl = String.format("%s_%s.jpg",name,inDto.num());
         return ChampionSkinOutDto
                .builder()
                .id(inDto.id())
                .num(inDto.num())
                .name(inDto.name())
                .chromas(inDto.chromas())
                .skinURI(this.riotApiConfig.getDataSplashBaseUrl() + formattedFileUrl)
                .build();
       }).toList();
    }
}
