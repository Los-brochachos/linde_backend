package com.linde.linde_backend.controllers.usuario;

import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;
import com.linde.linde_backend.dto.usuario.CambiarClaveRequest;
import com.linde.linde_backend.services.usuario.SeguridadCuentaService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

@RestController @RequestMapping("/api/v1/cuenta") @RequiredArgsConstructor
public class SeguridadCuentaRestController {
    private final SeguridadCuentaService service;
    @PatchMapping("/clave")
    public ResponseEntity<Void> cambiarClave(Authentication authentication, @Valid @RequestBody CambiarClaveRequest request) {
        service.cambiarClave(authentication.getName(), request);
        return ResponseEntity.noContent().build();
    }
}
