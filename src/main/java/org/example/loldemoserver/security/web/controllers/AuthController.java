package org.example.loldemoserver.security.web;

import lombok.RequiredArgsConstructor;
import org.example.loldemoserver.security.JwtUtil;
import org.example.loldemoserver.security.in.LoginRequest;
import org.example.loldemoserver.security.out.JwtResponse;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/auth")
@RequiredArgsConstructor
public class AuthController {

    private final AuthenticationManager authManager;
    private final JwtUtil jwtUtil;

    @PostMapping("/login")
    public ResponseEntity<JwtResponse> login(@RequestBody LoginRequest request) {
        authManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                        request.username(), request.password())
        );
        String token = jwtUtil.generateToken(request.username());
        return ResponseEntity.ok(new JwtResponse(token));
    }
}
