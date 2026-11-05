package org.example.loldemoserver.champions.dtos.in;

import org.example.loldemoserver.champions.entities.ChampionSkinEntity;
import org.example.loldemoserver.champions.entities.ChampionSkinId;
import org.example.loldemoserver.champions.repositories.ChampionSkinRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(MockitoExtension.class)
class ChampionSkinsCommandServiceTest {

    private final static Logger LOGGER = LoggerFactory.getLogger(ChampionSkinsCommandServiceTest.class);

   @Mock
   private ChampionSkinRepository championSkinRepository;

    @InjectMocks
    private ChampionSkinsCommandService commandService;

    @InjectMocks
    private ChampionSkinFetchingService fetchingService;


    @Test
    void addOwnedSkin() {
        var id = ChampionSkinId
                .builder()
                .username("Blubi")
                .championId("Zyra")
                .skinId("007")
                .build();
        var entity = new ChampionSkinEntity(id);

        Mockito.when(championSkinRepository.save(entity)).thenReturn(entity);
        //skin does not exist before creation
        Mockito.when(championSkinRepository.existsById(id)).thenReturn(false);

        var saved = this.commandService.addOwnedSkin(id);
        assertTrue(saved);
        Mockito.when(championSkinRepository.findById(id)).thenReturn(Optional.of(entity));
        assertTrue(this.fetchingService.getChampionSkinById(id).isPresent());
    }

    @Test
    void addAlreadyOwnedSkin() {
        var id = ChampionSkinId
                .builder()
                .username("Blubi")
                .championId("Zyra")
                .skinId("007")
                .build();

        //skin does  exist before creation
        Mockito.when(championSkinRepository.existsById(id)).thenReturn(true);

        var saved = this.commandService.addOwnedSkin(id);
        assertFalse(saved);
    }

    @Test
    void deleteChampionSkin() {
        var id = ChampionSkinId
                .builder()
                .username("Blubi")
                .championId("Zyra")
                .skinId("007")
                .build();
        var entity = new ChampionSkinEntity(id);
        //Before deletion
        Mockito.when(championSkinRepository.findById(id)).thenReturn(Optional.of(entity));
        Mockito.doNothing().when(championSkinRepository).delete(entity);

        this.commandService.deleteChampionSkin(id);
        //After deletion
        Mockito.when(championSkinRepository.findById(id)).thenReturn(Optional.empty());
        assertFalse(this.fetchingService.getChampionSkinById(id).isPresent());
    }
}