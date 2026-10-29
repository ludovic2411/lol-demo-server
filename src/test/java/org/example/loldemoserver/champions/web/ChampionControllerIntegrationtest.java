package org.example.loldemoserver.champions.web;

import org.example.loldemoserver.TestContainersPostgresConfiguration;
import org.example.loldemoserver.champions.dtos.out.ChampionsService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.webmvc.test.autoconfigure.AutoConfigureMockMvc;
import org.springframework.context.annotation.Import;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;

import static org.hamcrest.Matchers.hasSize;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.example.loldemoserver.common.utils.FakeChampionResponseFactoryUtil.createFakeChampionResponseOutDto;
import static org.example.loldemoserver.common.utils.FakeChampionResponseFactoryUtil.createFakeChampionListResponseDto;

@Import(TestContainersPostgresConfiguration.class)
@SpringBootTest
@AutoConfigureMockMvc
class ChampionsControllerIntegrationTest {

    @Autowired
    private MockMvc mockMvc;

    @MockitoBean
    private ChampionsService championsService;

    @Test
    @WithMockUser(username = "naughty", roles = {"user"})
    void getAllChampions_shouldReturn200_whenAuthenticated() throws Exception {
        // GIVEN
        when(championsService.getAllChampions()).thenReturn(createFakeChampionListResponseDto());

        // WHEN / THEN
        mockMvc.perform(get("/champions"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.data.data.name").value("Evelynn"));
    }


    @Test
    @WithMockUser(username = "naughty", roles = {"user"})
    void getChampionByName_shouldReturn200_whenAuthenticated() throws Exception {
        // GIVEN
        when(championsService.getChampionByName("Evelynn")).thenReturn(createFakeChampionResponseOutDto());

        // WHEN / THEN
        mockMvc.perform(get("/champions/Evelynn"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.name").value("Evelynn"))
                .andExpect(jsonPath("$.tags", hasSize(3)));
    }

    @Test
    @WithMockUser(username = "naughty", roles = {"user"})
    void getChampionByName_shouldReturn404_whenWrongName() throws Exception {
        // GIVEN
        when(championsService.getChampionByName("Evelynnaa")).thenReturn(null);

        // WHEN / THEN
        mockMvc.perform(get("/champions/Evelynnaa"))
                .andExpect(status().isNotFound());
    }
}
