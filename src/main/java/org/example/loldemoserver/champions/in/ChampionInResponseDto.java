package org.example.loldemoserver.champions.in;

import org.example.loldemoserver.champions.out.ChampionInfoDto;
import org.example.loldemoserver.champions.out.ChampionStatsDto;

import java.util.List;

/**
 * Dto to receive champion informations from riot api
 * @param version
 * @param id
 * @param key
 * @param name
 * @param title
 * @param blurb
 * @param lore
 * @param info
 * @param stats
 * @param tags
 * @param allytips
 * @param ennemytips
 * @param partype
 */
public record ChampionInResponseDto(String version,
                                    String id,
                                    String key,
                                    String name,
                                    String title,
                                    String blurb,
                                    String lore,
                                    ChampionInfoDto info,
                                    ChampionStatsDto stats,
                                    List<ChampionSkinInDto> skins,
                                    List<String> tags,
                                    List<String> allytips,
                                    List<String> ennemytips,
                                    String partype) {
}
