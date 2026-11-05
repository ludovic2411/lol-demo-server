package org.example.loldemoserver.champions.in;

import org.example.loldemoserver.TestContainersPostgresConfiguration;
import org.example.loldemoserver.champions.dtos.in.ChampionFetchingService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.Import;
import org.testcontainers.postgresql.PostgreSQLContainer;

import static org.junit.jupiter.api.Assertions.*;

@Import(TestContainersPostgresConfiguration.class)
@SpringBootTest
class ChampionFetchingServiceTest {

    @Autowired
    private ChampionFetchingService championFetchingService;

    @Autowired
    private PostgreSQLContainer postgreSQLContainer;

    @Test
    void testGetChampions() {
        var response = this.championFetchingService.getChampionResponse();
        assertNotNull(response);
    }

    @Test
    void testGetChampion() {
        var response = this.championFetchingService.getChampionByName("Evelynn");
        assertNotNull(response);
        assertEquals("Evelynn",response.name());
    }
}