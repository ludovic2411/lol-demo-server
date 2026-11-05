package org.example.loldemoserver.champions.web;

import org.example.loldemoserver.champions.dtos.in.ChampionSkinsCommandService;
import org.example.loldemoserver.champions.dtos.out.ChampionsService;
import org.example.loldemoserver.champions.entities.ChampionSkinId;
import org.example.loldemoserver.common.SkinNotFoundException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;
import java.util.Collections;
import java.util.Optional;

@RequestMapping("/champions")
@RestController
public class ChampionsController {

    private static final Logger LOGGER = LoggerFactory.getLogger(ChampionsController.class);

    private final ChampionsService championsService;
    private final ChampionSkinsCommandService championSkinsCommandService;

    public ChampionsController(ChampionsService championsService, ChampionSkinsCommandService championSkinsCommandService) {
        this.championsService = championsService;
        this.championSkinsCommandService = championSkinsCommandService;
    }

    @GetMapping
    public ResponseEntity<?> getAllChampions(Principal principal) {
        //TODO implements advice to control check on user principal
        if (principal == null) return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
        var optionalChampionsList = Optional.ofNullable(this.championsService.getAllChampions());
        return optionalChampionsList.isPresent() ? ResponseEntity.ok(optionalChampionsList.get()) :
                ResponseEntity.unprocessableContent().body(Collections.emptyList());
    }

    @GetMapping("/{name}")
    public ResponseEntity<?> getChampionByName(@PathVariable String name, Principal principal) {
        if (principal == null) return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
        if (name == null || name.isBlank()) return ResponseEntity.notFound().build();
        var champion = championsService.getChampionByName(principal.getName(), name);
        return champion != null ? ResponseEntity.ok(champion) : ResponseEntity.notFound().build();
    }

    /**
     * Mark champion skin as owned by creating a line in champions_skin table
     * PUT is preferred over POST as the server does not create an id by itslef
     * so the frontend already know the id.
     *
     * @param name
     * @param skin
     * @param principal
     * @return
     */
    @PutMapping("/{name}/skins/{skin}/ownership")
    public ResponseEntity<?> markSkinAsOwned(@PathVariable String name, @PathVariable String skin, Principal principal) {
        if (principal == null) return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
        var champion = championsService.getChampionByName(principal.getName(), name);
        if (champion == null) return ResponseEntity.notFound().build();

        var created = this.championSkinsCommandService.addOwnedSkin(this.createChampionSkinId(principal.getName(),name,skin));
       return created ?  ResponseEntity.status(HttpStatus.CREATED).build() : ResponseEntity.status(HttpStatus.NO_CONTENT).build();
    }

    /**
     * Unmark an owned skin by removing it from champions_skin table
     * @param name
     * @param skin
     * @param principal
     * @return
     */
    @DeleteMapping("/{name}/skins/{skin}/ownership")
    public ResponseEntity<Void> markSkinAsNotOwned(@PathVariable String name,
                                                   @PathVariable String skin,
                                                   Principal principal) {
        if (principal == null) return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
        try {
            this.championSkinsCommandService.removeOwnedSkin(this.createChampionSkinId(principal.getName(),name, skin ));
        } catch (SkinNotFoundException e) {
            LOGGER.warn(e.getMessage());
        }
        return ResponseEntity.noContent().build(); // 204 even if champion does not exist to avoid noise in the frontend
    }

    private ChampionSkinId createChampionSkinId (String username, String champion,  String skin) {
        return ChampionSkinId
                .builder()
                .championId(champion)
                .skinId(skin)
                .username(username)
                .build();
    }

}
