package com.linde.linde_backend.controllers;

import java.time.Duration;
import java.util.Map;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseCookie;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.csrf.CsrfToken;
import org.springframework.web.bind.annotation.*;
import com.linde.linde_backend.config.JwtConfig;
import com.linde.linde_backend.services.AuthService;
import com.linde.linde_backend.utils.auth.AuthRequest;
import com.linde.linde_backend.utils.auth.AuthResponse;
import com.linde.linde_backend.utils.auth.AuthTokens;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/auth")
@RequiredArgsConstructor
public class AuthRestController {
  private final AuthService authService;
  private final JwtConfig jwtConfig;
  @Value("${application.auth.cookie-secure:true}") private boolean cookieSecure;
  @Value("${application.auth.cookie-same-site:Strict}") private String sameSite;

  @GetMapping("/csrf")
  public ResponseEntity<?> csrf(CsrfToken token) {
    return ResponseEntity.ok().header(HttpHeaders.CACHE_CONTROL, "no-store")
        .body(Map.of("token", token.getToken(), "headerName", token.getHeaderName()));
  }

  @PostMapping("/login")
  public ResponseEntity<?> authenticate(@Valid @RequestBody AuthRequest request) {
    try {
      return authenticated(authService.authenticate(request));
    } catch (AuthenticationException ex) {
      return ResponseEntity.status(401).header(HttpHeaders.CACHE_CONTROL, "no-store")
          .body(Map.of("message", "Credenciales invalidas"));
    }
  }

  @PostMapping("/refresh-token")
  public ResponseEntity<?> refreshToken(@CookieValue(name = "linde_refresh", required = false) String refreshToken) {
    if (refreshToken == null || refreshToken.isBlank()) return invalidSession();
    try {
      return authenticated(authService.refreshToken(refreshToken));
    } catch (AuthenticationException ex) {
      return invalidSession();
    }
  }

  @PostMapping("/logout")
  public ResponseEntity<Void> logout(@CookieValue(name = "linde_refresh", required = false) String refreshToken) {
    authService.logout(refreshToken);
    return ResponseEntity.noContent().header(HttpHeaders.CACHE_CONTROL, "no-store")
        .header(HttpHeaders.SET_COOKIE, cookie("", Duration.ZERO).toString()).build();
  }

  private ResponseEntity<AuthResponse> authenticated(AuthTokens tokens) {
    return ResponseEntity.ok().header(HttpHeaders.CACHE_CONTROL, "no-store")
        .header(HttpHeaders.SET_COOKIE, cookie(tokens.refreshToken(),
            Duration.ofDays(jwtConfig.getRefreshTokenExpirationAfterDays())).toString())
        .body(new AuthResponse(tokens.accessToken()));
  }

  private ResponseEntity<?> invalidSession() {
    return ResponseEntity.status(401).header(HttpHeaders.CACHE_CONTROL, "no-store")
        .header(HttpHeaders.SET_COOKIE, cookie("", Duration.ZERO).toString())
        .body(Map.of("message", "Sesion invalida"));
  }

  private ResponseCookie cookie(String value, Duration age) {
    return ResponseCookie.from("linde_refresh", value).httpOnly(true).secure(cookieSecure)
        .sameSite(sameSite).path("/auth").maxAge(age).build();
  }
}