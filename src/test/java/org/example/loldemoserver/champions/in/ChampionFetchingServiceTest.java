package org.example.loldemoserver.champions.in;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest(properties = { "riot.api-key=RGAPI-c8140615-c1a4-4380-a2f0-02f8b324ae65" })
class ChampionFetchingServiceTest {

    @Autowired
    private ChampionFetchingService championFetchingService;

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