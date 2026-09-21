package com.linde.linde_backend.controllers;

import java.util.Map;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.linde.linde_backend.services.AuthService;
import com.linde.linde_backend.utils.AuthRequest;
import com.linde.linde_backend.utils.AuthResponse;
import com.linde.linde_backend.utils.RefreshTokenRequest;
import com.linde.linde_backend.utils.RegisterRequest;
import com.linde.linde_backend.utils.RegisterResponse;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("auth")
@RequiredArgsConstructor
public class AuthController {

    private final AuthService authService;

    @PostMapping("/register")
    public ResponseEntity<RegisterResponse> register(
            @Valid @RequestBody RegisterRequest request) {

        return ResponseEntity.ok(authService.register(request));
    }

    @PostMapping("/login")
    public ResponseEntity<AuthResponse> authenticate(
            @Valid @RequestBody AuthRequest request) {

        return ResponseEntity.ok(authService.authenticate(request));
    }

    @PostMapping("/refresh-token")
    public ResponseEntity<?> refreshToken(
            @Valid @RequestBody RefreshTokenRequest request) {

        try {
            return ResponseEntity.ok(authService.refreshToken(request));

        } catch (RuntimeException ex) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED)
                    .body(Map.of("Error", ex.getMessage()));
        }
    }
}