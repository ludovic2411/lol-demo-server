package org.example.loldemoserver.champions.out;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/**
 * Dto to expose skins infos from riot api
 */
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ChampionSkinOutDto {
    private String id;
    private int num;
    private String name;
    private boolean chromas;
    private String skinURI;
}
