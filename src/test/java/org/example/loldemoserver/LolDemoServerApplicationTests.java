package org.example.loldemoserver;

import org.example.loldemoserver.config.RiotApiConfig;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.Import;
import org.testcontainers.postgresql.PostgreSQLContainer;

import static org.junit.jupiter.api.Assertions.*;

@Import(TestcontainersConfiguration.class)
//@SpringBootTest(properties = { "riot.api-key=my-awesome-key" })
@SpringBootTest
class LolDemoServerApplicationTests {

    @Autowired
    private RiotApiConfig riotApiConfig;

    @Test
    void contextLoads() {
        assertNotNull(this.riotApiConfig);
        //assertNotNull(this.riotApiConfig.getApiKey());
        assertTrue(this.riotApiConfig.getApiDefaultLanguage() != null && this.riotApiConfig.getApiDefaultLanguage().equals("en_US"));
    }

}
