package org.example.loldemoserver.champions.out;

public record ChampionStatsDto(
        double hp,
        double hpperlevel,
        double mp,
        double mpperlevel,
        double movespeed,
        double armor,
        double armorperlevel,
        double spellblock,
        double spellblockperlevel,
        double attackrange,
        double hpregen,
        double hpregenperlevel,
        double mpregen,
        double mpregenperlevel,
        double crit,
        double critperlevel,
        double attackdamage,
        double attackdamageperlevel,
        double attackspeedperlevel,
        double attackspeed
) {}
