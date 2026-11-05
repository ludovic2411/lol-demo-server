package org.example.loldemoserver.champions.web;

import org.example.loldemoserver.champions.dtos.in.ChampionSkinsCommandService;
import org.example.loldemoserver.champions.dtos.out.ChampionsService;
import org.example.loldemoserver.champions.entities.ChampionSkinId;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;
import java.util.Collections;
import java.util.Optional;

@RequestMapping("/champions")
@RestController
public class ChampionsController {

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
    public ResponseEntity<?> updateChampionSkinStatus(@PathVariable String name, @PathVariable String skin, Principal principal) {
        if (principal == null) return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
        var champion = championsService.getChampionByName(principal.getName(), name);
        if (champion == null) return ResponseEntity.notFound().build();

        var skinId = ChampionSkinId
                .builder()
                .championId(name)
                .skinId(skin)
                .username(principal.getName())
                .build();
        var created = this.championSkinsCommandService.addOwnedSkin(skinId);
       return created ?  ResponseEntity.status(HttpStatus.CREATED).build() : ResponseEntity.status(HttpStatus.NO_CONTENT).build();
    }

}
