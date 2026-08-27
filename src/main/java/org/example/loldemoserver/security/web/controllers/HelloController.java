package org.example.loldemoserver.security.web.controllers;

import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class HelloController {

    @GetMapping("/me")
    public String me(@AuthenticationPrincipal Jwt jwt) {
        return "Connecté en tant que : " + jwt.getClaimAsString("preferred_username");
    }

    @PreAuthorize("hasRole('USER')")
    @GetMapping("/public/dashboard")
    public String adminOnly() {
        return "Réservé aux users";
    }
}
