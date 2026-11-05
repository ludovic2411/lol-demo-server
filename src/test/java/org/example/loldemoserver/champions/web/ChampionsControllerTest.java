package org.example.loldemoserver.champions.web;

import org.example.loldemoserver.champions.dtos.in.ChampionSkinsCommandService;
import org.example.loldemoserver.champions.dtos.out.ChampionsService;
import org.example.loldemoserver.champions.entities.ChampionSkinId;
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

    @Mock
    private ChampionSkinsCommandService championSkinsCommandService;


    @InjectMocks
    private  ChampionsController championsController;

    private final static Principal FAKER_PRINCIPAL =  () -> "faker";
    private final static String EVE = "Evelynn";
    private final static String DEFAULT_SKIN_ID  = "1001";

    @Test
    void testGetAllChampions() {
        Mockito.when(this.championsService.getAllChampions()).thenReturn(createFakeChampionListResponseDto());
        var response = this.championsController.getAllChampions(FAKER_PRINCIPAL);
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
        Mockito.when(this.championsService.getChampionByName(FAKER_PRINCIPAL.getName(),EVE)).thenReturn(createFakeChampionResponseOutDto());

        var response = this.championsController.getChampionByName(EVE,FAKER_PRINCIPAL);
        assertEquals(HttpStatus.OK,response.getStatusCode());
        assertTrue(response.hasBody());
    }

    @Test
    void testGetChampionByNameNoPrincipal() {
        var response = this.championsController.getChampionByName(EVE,null);
        assertEquals(HttpStatus.UNAUTHORIZED,response.getStatusCode());
        assertFalse(response.hasBody());
    }

    @Test
    void testPutChampionSkinOwnership() {
        //setup get champion by name
        Mockito.when(this.championsService.getChampionByName(FAKER_PRINCIPAL.getName(),EVE)).thenReturn(createFakeChampionResponseOutDto());
        var skinId = ChampionSkinId.builder().championId(EVE).skinId(DEFAULT_SKIN_ID).username(FAKER_PRINCIPAL.getName()).build();
        Mockito.when(this.championSkinsCommandService.addOwnedSkin(skinId)).thenReturn(true);
        var response = this.championsController.updateChampionSkinStatus(EVE,DEFAULT_SKIN_ID,FAKER_PRINCIPAL);
        assertEquals(HttpStatus.CREATED,response.getStatusCode());
    }

    @Test
    void testPutChampionSkinOwnershipAlreadyOwned() {
        //setup get champion by name
        Mockito.when(this.championsService.getChampionByName(FAKER_PRINCIPAL.getName(),EVE)).thenReturn(createFakeChampionResponseOutDto());
        var skinId = ChampionSkinId.builder().championId(EVE).skinId(DEFAULT_SKIN_ID).username(FAKER_PRINCIPAL.getName()).build();
        Mockito.when(this.championSkinsCommandService.addOwnedSkin(skinId)).thenReturn(false);
        var response = this.championsController.updateChampionSkinStatus(EVE,DEFAULT_SKIN_ID,FAKER_PRINCIPAL);
        assertEquals(HttpStatus.NO_CONTENT,response.getStatusCode());
    }

    @Test
    void testPutChampionNotFound() {
        //setup get champion by name
        Mockito.when(this.championsService.getChampionByName(FAKER_PRINCIPAL.getName(),EVE)).thenReturn(null);
        var response = this.championsController.updateChampionSkinStatus(EVE,DEFAULT_SKIN_ID,FAKER_PRINCIPAL);
        assertEquals(HttpStatus.NOT_FOUND,response.getStatusCode());
    }
}