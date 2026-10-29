package org.example.loldemoserver.champions.web;

import org.example.loldemoserver.champions.dtos.out.ChampionsService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpStatus;

import java.security.Principal;

import static org.junit.jupiter.api.Assertions.*;
import static org.example.loldemoserver.common.utils.FakeChampionResponseFactoryUtil.createFakeChampionResponseOutDto;
import static org.example.loldemoserver.common.utils.FakeChampionResponseFactoryUtil.createFakeChampionListResponseDto;

@ExtendWith(MockitoExtension.class)
class ChampionsControllerTest {

    @Mock
    private ChampionsService championsService;


    @InjectMocks
    private  ChampionsController championsController;

    @Test
    void testGetAllChampions() {
        Mockito.when(this.championsService.getAllChampions()).thenReturn(createFakeChampionListResponseDto());
        Principal principal = () -> "faker";
        var response = this.championsController.getAllChampions(principal);
        assertEquals(HttpStatus.OK,response.getStatusCode());
        assertTrue(response.hasBody());
    }

    @Test
    void testGetAllChampionsNoPrincipal() {
        var response = this.championsController.getAllChampions(null);
        assertEquals(HttpStatus.UNAUTHORIZED,response.getStatusCode());
        assertFalse(response.hasBody());
    }

    @Test
    void testGetChampionByName() {
        //setup get champion by name
        Mockito.when(this.championsService.getChampionByName("Evelynn")).thenReturn(createFakeChampionResponseOutDto());

        Principal principal = () -> "faker";
        var response = this.championsController.getChampionByName("Evelynn",principal);
        assertEquals(HttpStatus.OK,response.getStatusCode());
        assertTrue(response.hasBody());
    }

    @Test
    void testGetChampionByNameNoPrincipal() {
        var response = this.championsController.getChampionByName("Evelynn",null);
        assertEquals(HttpStatus.UNAUTHORIZED,response.getStatusCode());
        assertFalse(response.hasBody());
    }
}