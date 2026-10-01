package org.example.loldemoserver.champions.out;

import org.example.loldemoserver.champions.in.ChampionFetchingService;
import org.example.loldemoserver.champions.in.ChampionInResponseDto;
import org.example.loldemoserver.champions.in.ChampionSkinInDto;
import org.example.loldemoserver.config.RiotApiConfig;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import java.util.Arrays;
import static org.junit.jupiter.api.Assertions.*;

public class ChampionsServiceTests {

    private final RiotApiConfig riotApiConfig = Mockito.mock(RiotApiConfig.class);
    private ChampionFetchingService championFetchingService = Mockito.mock(ChampionFetchingService.class);
    private final ChampionsService championsService = new ChampionsService(championFetchingService, riotApiConfig);

    @Test
    void testGetChampionByName() {
        var infos = new ChampionInfoDto(4, 2, 7, 10);
        var stats = new ChampionStatsDto(648, 92, 56, 23, 323, 45, 5, 46, 3, 10.5, 6.5, 2, 5, 3.75, 2, 0.15, 62, 2, 0.75, 1.225);
        var tags = Arrays.asList("tag1", "tag2", "tag3");
        var tips = Arrays.asList("tip1", "tip2", "tip3");
        var skinsInDto = Arrays.asList(new ChampionSkinInDto("id0", 0, "Base skin", false)
                , new ChampionSkinInDto("id1", 1, "Chromalight Evelynn", true));
        ChampionInResponseDto inResponseDto = new ChampionInResponseDto("1.0.0", "1", "key1", "Evelynn", "Title", "blurb", "lore", infos,
                stats, skinsInDto, tags, tips, tips, "Mana");

        Mockito.when(riotApiConfig.getDataSplashBaseUrl()).thenReturn("https://ddragon.leagueoflegends.com/cdn/img/champion/splash/");
        Mockito.when(championFetchingService.getChampionByName("Evelynn")).thenReturn(inResponseDto);
        ChampionOutResponseDto outResponseDto = this.championsService.getChampionByName("Evelynn");
        assertEquals(inResponseDto.name(),outResponseDto.getName());
        assertEquals("https://ddragon.leagueoflegends.com/cdn/img/champion/splash/Evelynn_0.jpg",outResponseDto.getSkins().getFirst().getSkinURI());

    }
}
