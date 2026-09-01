package org.example.loldemoserver.security.web.controllers;

import org.springframework.context.annotation.Profile;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Profile("keycloak")
@RequestMapping("/api/auth")
@RestController
public class KeycloakAuthController {

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
