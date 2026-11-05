package org.example.loldemoserver.common.utils;

import org.example.loldemoserver.champions.entities.ChampionSkinEntity;
import org.example.loldemoserver.champions.entities.ChampionSkinId;

public class FakeChampionSkinFactoryUtil implements TestsConstants {

    public static ChampionSkinId createFakeChampionSkinId(){
        return ChampionSkinId.builder().skinId(DEFAULT_SKIN_ID).username(DEFAULT_USERNAME).championId(EVE).build();
    }

    public static ChampionSkinEntity createFakeChampionSkinEntity(){
        return new ChampionSkinEntity(createFakeChampionSkinId());
    }
}
