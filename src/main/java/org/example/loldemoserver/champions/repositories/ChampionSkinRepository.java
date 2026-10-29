package org.example.loldemoserver.champions.repositories;

import org.example.loldemoserver.champions.entities.ChampionSkinEntity;
import org.example.loldemoserver.champions.entities.ChampionSkinId;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ChampionSkinRepository extends JpaRepository<ChampionSkinEntity, ChampionSkinId> {

    List<ChampionSkinEntity> findAllyByIdUsername(String username);


    List<ChampionSkinEntity> findAllByIdUsernameAndIdChampionId(String username, String championId );
}
