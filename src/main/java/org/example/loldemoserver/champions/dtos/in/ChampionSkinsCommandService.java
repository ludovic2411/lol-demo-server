package org.example.loldemoserver.champions.dtos.in;

import org.example.loldemoserver.champions.entities.ChampionSkinEntity;
import org.example.loldemoserver.champions.entities.ChampionSkinId;
import org.example.loldemoserver.champions.repositories.ChampionSkinRepository;
import org.example.loldemoserver.common.SkinNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class ChampionSkinsCommandService {

    public ChampionSkinsCommandService(ChampionSkinRepository championSkinRepository) {
        this.championSkinRepository = championSkinRepository;
    }

    private ChampionSkinRepository championSkinRepository;

    @Transactional
    public boolean addOwnedSkin(ChampionSkinId id) {
        if (this.championSkinRepository.existsById(id)) return false; // already owned
        this.championSkinRepository.save(new ChampionSkinEntity(id));
        return true;
    }

    @Transactional
    public void removeOwnedSkin(ChampionSkinId skinId) throws SkinNotFoundException {
        ChampionSkinEntity csk = championSkinRepository.findById(skinId).orElseThrow(() -> new SkinNotFoundException(skinId));
        this.championSkinRepository.delete(csk);
    }

}
