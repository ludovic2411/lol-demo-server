package org.example.loldemoserver.champions.dtos.in;

import lombok.RequiredArgsConstructor;
import org.example.loldemoserver.champions.entities.ChampionSkinEntity;
import org.example.loldemoserver.champions.entities.ChampionSkinId;
import org.example.loldemoserver.champions.repositories.ChampionSkinRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

/**
 * Service to retrieve skins entities
 */
@Service
@RequiredArgsConstructor
public class ChampionSkinFetchingService {

    private ChampionSkinRepository championSkinRepository;

    public Optional<ChampionSkinEntity> getChampionSkinById(ChampionSkinId championSkinId) {
        return this.championSkinRepository.findById(championSkinId);
    }

    public List<ChampionSkinEntity> getAllChampionSkinsByUsername(String username) {
        return this.championSkinRepository.findAllyByIdUsername(username);
    }

    public List<ChampionSkinEntity> getAllChampionSkinsForUserAndChampion(String username, String championId) {
       return this.championSkinRepository.findAllByIdUsernameAndIdChampionId(username,championId );
    }

}
