package org.example.loldemoserver.champions.web;

import lombok.RequiredArgsConstructor;
import org.example.loldemoserver.champions.in.ChampionFetchingService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.security.Principal;
import java.util.Collections;
import java.util.Optional;

@RequestMapping("/champions")
@RestController
public class ChampionsController {

    private final ChampionFetchingService championFetchingService;

    public ChampionsController(ChampionFetchingService championFetchingService) {
        this.championFetchingService = championFetchingService;
    }

    @GetMapping
    public ResponseEntity<?> getAllChampions(Principal principal) {
        //TODO implements advice to control check on user principal
        if(principal == null) return ResponseEntity.status(403).build();
        var optionalChampionsList = Optional.ofNullable(this.championFetchingService.getChampionResponse());
        return optionalChampionsList.isPresent() ? ResponseEntity.ok(optionalChampionsList.get()) :
                ResponseEntity.unprocessableContent().body(Collections.emptyList());
    }

    @GetMapping("/{name}")
    public ResponseEntity<?> getChampionByName(@PathVariable String name, Principal principal) {
        if(principal == null) return ResponseEntity.status(403).build();
        if(name == null || name.trim().isBlank()) return ResponseEntity.notFound().build();
        var optionalChampion = Optional.ofNullable(this.championFetchingService.getChampion(name));
        return optionalChampion.isPresent() ? ResponseEntity.ok(optionalChampion.get()) : ResponseEntity.notFound().build();
    }

}
