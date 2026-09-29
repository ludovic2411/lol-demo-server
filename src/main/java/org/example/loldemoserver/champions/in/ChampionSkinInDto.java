package org.example.loldemoserver.champions.in;

/**
 * Dto to receive skins infos from riot api
 * @param id
 * @param num
 * @param name
 * @param chromas
 */
public record ChampionSkinInDto(String id, int num, String name, boolean chromas) {
}
