package org.example.loldemoserver.champions.web;

import org.example.loldemoserver.champions.dtos.in.ChampionSkinsCommandService;
import org.example.loldemoserver.champions.dtos.out.ChampionsService;
import org.example.loldemoserver.common.utils.TestsConstants;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.webmvc.test.autoconfigure.AutoConfigureMockMvc;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;

import static org.hamcrest.Matchers.hasSize;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.example.loldemoserver.common.utils.FakeChampionResponseFactoryUtil.createFakeChampionResponseOutDto;
import static org.example.loldemoserver.common.utils.FakeChampionResponseFactoryUtil.createFakeChampionListResponseDto;
import static org.example.loldemoserver.common.utils.FakeChampionSkinFactoryUtil.createFakeChampionSkinId;

@SpringBootTest
@AutoConfigureMockMvc
class ChampionsControllerMockMvcTest implements TestsConstants {

    @Autowired
    private MockMvc mockMvc;

    @MockitoBean
    private ChampionsService championsService;

    @MockitoBean
    private ChampionSkinsCommandService championSkinsCommandService;


    @Test
    @WithMockUser(username = DEFAULT_USERNAME, roles = {"user"})
    void getAllChampions_shouldReturn200_whenAuthenticated() throws Exception {
        // GIVEN
        when(championsService.getAllChampions()).thenReturn(createFakeChampionListResponseDto());

        // WHEN / THEN
        mockMvc.perform(get("/champions"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.data.data.name").value(EVE));
    }


    @Test
    @WithMockUser(username = DEFAULT_USERNAME, roles = {"user"})
    void getChampionByName_shouldReturn200_whenAuthenticated() throws Exception {
        // GIVEN
        when(championsService.getChampionByName(DEFAULT_USERNAME, EVE)).thenReturn(createFakeChampionResponseOutDto());

        // WHEN / THEN
        mockMvc.perform(get("/champions/" + EVE))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.name").value(EVE))
                .andExpect(jsonPath("$.tags", hasSize(3)));
    }

    @Test
    @WithMockUser(username = DEFAULT_USERNAME, roles = {"user"})
    void getChampionByName_shouldReturn404_whenWrongName() throws Exception {
        // GIVEN
        when(championsService.getChampionByName(DEFAULT_USERNAME, WRONG_EVE)).thenReturn(null);

        // WHEN / THEN
        mockMvc.perform(get("/champions/" + WRONG_EVE))
                .andExpect(status().isNotFound());
    }

    /***** Region skins ****/

    @Test
    @WithMockUser(username = DEFAULT_USERNAME, roles = {"user"})
    void putChampionSkinOwnership_shouldReturn201_when_created() throws Exception {
        // GIVEN
        when(championsService.getChampionByName(DEFAULT_USERNAME, EVE)).thenReturn(createFakeChampionResponseOutDto());
        when(championSkinsCommandService.addOwnedSkin(createFakeChampionSkinId())).thenReturn(true);

        // WHEN / THEN
        mockMvc.perform(put("/champions/" + EVE + "/skins/" + DEFAULT_SKIN_ID + "/ownership"))
                .andExpect(status().isCreated());
    }

    @Test
    @WithMockUser(username = DEFAULT_USERNAME, roles = {"user"})
    void putChampionSkinOwnership_shouldReturn204_when_alreadyOwned() throws Exception {
        // GIVEN
        when(championsService.getChampionByName(DEFAULT_USERNAME, EVE)).thenReturn(createFakeChampionResponseOutDto());
        when(championSkinsCommandService.addOwnedSkin(createFakeChampionSkinId())).thenReturn(false);

        // WHEN / THEN
        mockMvc.perform(put("/champions/" + EVE + "/skins/" + DEFAULT_SKIN_ID + "/ownership"))
                .andExpect(status().isNoContent());
    }

    @Test
    @WithMockUser(username = DEFAULT_USERNAME, roles = {"user"})
    void putChampionSkinOwnership_shouldReturn404_whenWrongName() throws Exception {
        // GIVEN
        when(championsService.getChampionByName(DEFAULT_USERNAME, WRONG_EVE)).thenReturn(null);

        // WHEN / THEN
        mockMvc.perform(put("/champions/" + WRONG_EVE + "/skins/" + DEFAULT_SKIN_ID + "/ownership"))
                .andExpect(status().isNotFound());
    }

    @Test
    @WithMockUser(username = DEFAULT_USERNAME, roles = {"user"})
    void deleteChampionSkinOwnership_shouldReturn204_when_deleted() throws Exception {
        // WHEN / THEN
        //test on an existing skin
        mockMvc.perform(delete("/champions/" + SYNDRA + "/skins/" + SYNDRA_SKIN_ID + "/ownership"))
                .andExpect(status().isNoContent());
    }

    @Test
    @WithMockUser(username = DEFAULT_USERNAME, roles = {"user"})
    void deleteChampionSkinOwnership_shouldReturn204_when_skin_not_exists() throws Exception {
        // WHEN / THEN
        //should return 204 to avoid creating noise on client side
        mockMvc.perform(delete("/champions/" + WRONG_EVE + "/skins/" + DEFAULT_SKIN_ID + "/ownership"))
                .andExpect(status().isNoContent());
    }
}
