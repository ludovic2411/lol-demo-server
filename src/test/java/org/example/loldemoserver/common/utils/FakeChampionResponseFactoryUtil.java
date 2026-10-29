package org.example.loldemoserver.common.utils;

import org.example.loldemoserver.champions.dtos.in.ChampionInResponseDto;
import org.example.loldemoserver.champions.dtos.in.ChampionSkinInDto;
import org.example.loldemoserver.champions.dtos.out.*;

import java.util.Arrays;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

/**
 * Factory class to create fake dtos for testing
 */
public class FakeChampionResponseFactoryUtil {

    //setup get champion by name
    public static ChampionOutResponseDto createFakeChampionResponseOutDto() {
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

    // setup get all champions
    public static ChampionListRecordDto createFakeChampionListResponseDto() {

        Map<String,Object> data = new HashMap<>();
        data.put("data",createFakeChampionResponseOutDto());
        return new ChampionListRecordDto("type","format","1.0.0",data);
    }
}
