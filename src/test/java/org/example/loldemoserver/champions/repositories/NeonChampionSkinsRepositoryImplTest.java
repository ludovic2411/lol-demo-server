package org.example.loldemoserver.champions.repositories;

import org.example.loldemoserver.champions.entities.ChampionSkinEntity;
import org.example.loldemoserver.champions.entities.ChampionSkinId;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.data.jpa.test.autoconfigure.DataJpaTest;
import org.springframework.boot.jdbc.test.autoconfigure.AutoConfigureTestDatabase;
import org.springframework.core.env.Environment;
import org.springframework.test.context.ActiveProfiles;

import static org.junit.jupiter.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;

@DataJpaTest
@ActiveProfiles("qa")
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
@Tag("qa")
public class NeonChampionSkinsRepositoryImplTest implements ChampionSkinsRepositoryTest {

    private final static Logger LOGGER = LoggerFactory.getLogger(NeonChampionSkinsRepositoryImplTest.class);

    @Autowired
    private ChampionSkinRepository championSkinRepository;

    @Autowired
    private Environment env;

    @Test
    public void testGetAll() {
        LOGGER.info("Connected to database {} running on profiles {}", this.env.getProperty("spring.datasource.url"), this.env.getActiveProfiles());
        var rows = this.championSkinRepository.findAll();
        assertFalse(rows.isEmpty());
    }

    @Test
    public void testGetById() {
        var id = ChampionSkinId
                .builder()
                .username("naughty")
                .championId("Syndra")
                .skinId("134006")
                .build();
        var row = this.championSkinRepository.findById(id);
        assertTrue(row.isPresent());
    }
    @Test
    public void testGetByUsername() {
        var rows = this.championSkinRepository.findAllyByIdUsername("naughty");
        assertFalse(rows.isEmpty());
    }

    @Test
    public void testGetByUsernameAndChampion() {
        var id = ChampionSkinId
                .builder()
                .username("naughty")
                .championId("Syndra")
                .skinId(null)
                .build();

        var rows = this.championSkinRepository.findAllByIdUsernameAndIdChampionId(id.getUsername(),id.getChampionId());
        assertFalse(rows.isEmpty());
    }

    @Test
    public void testInsertDelete() {
        var id = ChampionSkinId
                .builder()
                .username("ducky")
                .championId("Riven")
                .skinId("1001")
                .build();
        var entity = new ChampionSkinEntity(id);
        var saved = this.championSkinRepository.save(entity);
        //save
        assertNotNull(saved);
        assertEquals(saved, entity);
        //delete
        this.championSkinRepository.delete(saved);
        assertFalse(this.championSkinRepository.existsById(id));
    }
}
