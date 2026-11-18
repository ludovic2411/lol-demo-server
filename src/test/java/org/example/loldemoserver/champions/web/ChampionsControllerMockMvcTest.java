package org.example.loldemoserver.champions.web;

public sealed interface ChampionsControllerMockMvcTest permits ChampionsControllerMockMvcKeycloakImplTest, ChampionsControllerMockMvcQaTest {
    void getAllChampions_shouldReturn200_whenAuthenticated() throws Exception;
    void getChampionByName_shouldReturn200_whenAuthenticated() throws Exception;
    void getChampionByName_shouldReturn404_whenWrongName() throws Exception;
    void putChampionSkinOwnership_shouldReturn201_when_created() throws Exception;
    void putChampionSkinOwnership_shouldReturn204_when_alreadyOwned() throws Exception;
    void putChampionSkinOwnership_shouldReturn404_whenWrongName() throws Exception;
    void deleteChampionSkinOwnership_shouldReturn204_when_deleted() throws Exception;
    void deleteChampionSkinOwnership_shouldReturn204_when_skin_not_exists() throws Exception;
}
