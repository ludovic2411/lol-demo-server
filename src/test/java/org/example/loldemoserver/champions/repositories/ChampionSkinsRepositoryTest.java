package org.example.loldemoserver.champions.repositories;

import org.example.loldemoserver.TestContainersPostgresConfiguration;
import org.example.loldemoserver.champions.entities.ChampionSkinEntity;
import org.example.loldemoserver.champions.entities.ChampionSkinId;
import org.junit.jupiter.api.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.data.jpa.test.autoconfigure.DataJpaTest;
import org.springframework.context.annotation.Import;
import org.testcontainers.postgresql.PostgreSQLContainer;

import static org.junit.jupiter.api.Assertions.*;

@Import(TestContainersPostgresConfiguration.class)
@DataJpaTest
public class ChampionSkinsRepositoryTest {

    private final static Logger LOGGER = LoggerFactory.getLogger(ChampionSkinsRepositoryTest.class);

    @Autowired
    private PostgreSQLContainer postgreSQLContainer;

    @Autowired
    private ChampionSkinRepository championSkinRepository;

    @Test
    void testGetAll() {
        LOGGER.info("Connected to database {}", this.postgreSQLContainer.getDatabaseName());
        var rows = this.championSkinRepository.findAll();
        assertFalse(rows.isEmpty());
    }

    @Test
    void testGetById() {
        var id = ChampionSkinId
                .builder()
                .username("naughty")
                .championId("Syndra")
                .skinId("134034")
                .build();
        var row = this.championSkinRepository.findById(id);
        assertTrue(row.isPresent());
    }
    @Test
    void testGetByUsername() {
        var rows = this.championSkinRepository.findAllyByIdUsername("naughty");
        assertFalse(rows.isEmpty());
    }

    @Test
    void testGetByUsernameAndChampion() {
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
    void testInsertDelete() {
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
