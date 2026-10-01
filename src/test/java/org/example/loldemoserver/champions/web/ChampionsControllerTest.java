package org.example.loldemoserver.champions.web;

import org.example.loldemoserver.champions.in.ChampionInResponseDto;
import org.example.loldemoserver.champions.in.ChampionSkinInDto;
import org.example.loldemoserver.champions.out.*;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpStatus;

import java.security.Principal;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(MockitoExtension.class)
class ChampionsControllerTest {

    @Mock
    private ChampionsService championsService;


    @InjectMocks
    private  ChampionsController championsController;

    @Test
    void testGetAllChampions() {
        Mockito.when(this.championsService.getAllChampions()).thenReturn(createFakeChampionListResponseDto());
        Principal principal = () -> "faker";
        var response = this.championsController.getAllChampions(principal);
        assertEquals(HttpStatus.OK,response.getStatusCode());
        assertTrue(response.hasBody());
    }

    @Test
    void testGetAllChampionsNoPrincipal() {
        var response = this.championsController.getAllChampions(null);
        assertEquals(HttpStatus.UNAUTHORIZED,response.getStatusCode());
        assertFalse(response.hasBody());
    }

    @Test
    void testGetChampionByName() {
        //setup get champion by name
        Mockito.when(this.championsService.getChampionByName("Evelynn")).thenReturn(createFakeChampionresponseOutDto());

        Principal principal = () -> "faker";
        var response = this.championsController.getChampionByName("Evelynn",principal);
        assertEquals(HttpStatus.OK,response.getStatusCode());
        assertTrue(response.hasBody());
    }

    @Test
    void testGetChampionByNameNoPrincipal() {
        var response = this.championsController.getChampionByName("Evelynn",null);
        assertEquals(HttpStatus.UNAUTHORIZED,response.getStatusCode());
        assertFalse(response.hasBody());
    }

    private static ChampionOutResponseDto createFakeChampionresponseOutDto() {
        //setup get champion by name
        var infos = new ChampionInfoDto(4, 2, 7, 10);
        var stats = new ChampionStatsDto(648, 92, 56, 23, 323, 45, 5, 46, 3, 10.5, 6.5, 2, 5, 3.75, 2, 0.15, 62, 2, 0.75, 1.225);
        var tags = Arrays.asList("tag1", "tag2", "tag3");
        var tips = Arrays.asList("tip1", "tip2", "tip3");
        var skinsInDto = Arrays.asList(new ChampionSkinInDto("id0", 0, "Base skin", false)
                , new ChampionSkinInDto("id1", 1, "Chromalight Evelynn", true));
        ChampionInResponseDto inResponseDto = new ChampionInResponseDto("1.0.0", "1", "key1", "Evelynn", "Title", "blurb", "lore", infos,
                stats, skinsInDto, tags, tips, tips, "Mana");
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
                .skins(Collections.singletonList(new ChampionSkinOutDto("id",0,"base skin",false,"https://ddragon.leagueoflegends.com/cdn/img/champion/splash/Evelynn_0.jpg")))
                .tags(inResponseDto.tags())
                .allytips(inResponseDto.allytips())
                .ennemytips(inResponseDto.ennemytips())
                .partype(inResponseDto.partype())
                .build();
    }

    private static ChampionListRecordDto createFakeChampionListResponseDto() {
        // setup get all champions
        Map<String,Object> data = new HashMap<>();
        data.put("data",createFakeChampionresponseOutDto());
        return new ChampionListRecordDto("type","format","1.0.0",data);
    }
}