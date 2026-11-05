package org.example.loldemoserver.common;

import org.example.loldemoserver.champions.entities.ChampionSkinId;

public class SkinNotFoundException extends RuntimeException {
    public SkinNotFoundException(ChampionSkinId skinId) {
        super(String.format("Cannot find skin with id %s",skinId.toString()));
    }
}
