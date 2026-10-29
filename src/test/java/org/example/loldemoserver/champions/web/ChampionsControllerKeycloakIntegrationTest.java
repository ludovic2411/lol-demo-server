package org.example.loldemoserver.champions.web;

import dasniko.testcontainers.keycloak.KeycloakContainer;
import io.restassured.RestAssured;
import org.example.loldemoserver.TestContainersPostgresConfiguration;
import org.junit.jupiter.api.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.webmvc.test.autoconfigure.AutoConfigureMockMvc;
import org.springframework.context.annotation.Import;
import org.springframework.test.context.DynamicPropertyRegistry;
import org.springframework.test.context.DynamicPropertySource;
import org.springframework.test.web.servlet.MockMvc;
import org.testcontainers.junit.jupiter.Container;
import org.testcontainers.junit.jupiter.Testcontainers;

import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;

@Import(TestContainersPostgresConfiguration.class)
@SpringBootTest
@AutoConfigureMockMvc
@Testcontainers
class ChampionsControllerKeycloakIntegrationTest {

    private final static Logger LOGGER = LoggerFactory.getLogger(ChampionsControllerKeycloakIntegrationTest.class);

    @Autowired
    private MockMvc mockMvc;

    @Container
    static KeycloakContainer keycloak = new KeycloakContainer("quay.io/keycloak/keycloak:latest")
            .withRealmImportFile("keycloak/lol-demo-server-realm-realm.json");// ton export de realm !

    @DynamicPropertySource
    static void overrideProperties(DynamicPropertyRegistry registry) {
        // Surcharge l'issuer-uri pour pointer vers le Keycloak du container de test
        LOGGER.debug("-------Overriding issuer-uri to point to keycloak container instance--------");
        registry.add(
                "spring.security.oauth2.resourceserver.jwt.issuer-uri",
                () -> keycloak.getAuthServerUrl() + "/realms/lol-demo-server-realm"
        );
    }

    @Test
    void getAllChampions_shouldReturn200_withRealKeycloakToken() throws Exception {
        // Obtient un vrai token depuis le Keycloak de test
        String token = getTokenFromKeycloak();
        LOGGER.debug(String.format("Token retrieved: %s",token));
        mockMvc.perform(get("/champions")
                        .header("Authorization", "Bearer " + token))
                .andExpect(status().isOk());
    }

    @Test
    void getAllChampions_shouldReturn401_withNoKeycloakToken() throws Exception {
        // Obtient un vrai token depuis le Keycloak de test
        String token = getTokenFromKeycloak();
        LOGGER.debug(String.format("Token retrieved: %s",token));
        mockMvc.perform(get("/champions")
                        .header("Authorization", "Bearer "))
                .andExpect(status().isUnauthorized());
    }

    @Test
    void getChampionsByName_shouldReturn200_withRealKeycloakToken() throws Exception {
        // Obtient un vrai token depuis le Keycloak de test
        String token = getTokenFromKeycloak();
        LOGGER.debug(String.format("Token retrieved: %s",token));
        mockMvc.perform(get("/champions/Elise")
                        .header("Authorization", "Bearer " + token))
                .andExpect(status().isOk());
    }

    @Test
    void getChampionsByName_shouldReturn404_withWrongName() throws Exception {
        // Obtient un vrai token depuis le Keycloak de test
        String token = getTokenFromKeycloak();
        LOGGER.debug(String.format("Token retrieved: %s",token));
        mockMvc.perform(get("/champions/ObiWan")
                        .header("Authorization", "Bearer " + token))
                .andExpect(status().isNotFound());
    }

    private String getTokenFromKeycloak() {
        LOGGER.debug("-------Getting token from Keycloak--------");
        return RestAssured.given()
                .formParam("grant_type", "password")
                .formParam("client_id", "lol-demo-server-client")
                .formParam("username", "naughty")
                .formParam("password", "sushi")
                .post(keycloak.getAuthServerUrl() + "/realms/lol-demo-server-realm/protocol/openid-connect/token")
                .jsonPath().getString("access_token");
    }
}