package org.example.loldemoserver.champions.dtos.out;

import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ChampionOutResponseDto {
    private String version;
    private String id;
    private String key;
    private String name;
    private String title;
    private String blurb;
    private String lore;
    private ChampionInfoDto info;
    private ChampionStatsDto stats;
    private List<ChampionSkinOutDto> skins;
    private List<String> tags;
    private List<String> allytips;
    private List<String> ennemytips;
    private String partype;
}