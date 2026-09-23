package com.linde.linde_backend.controllers.usuario;

import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.linde.linde_backend.dto.usuario.UsuarioResponse;
import com.linde.linde_backend.dto.usuario.UsuarioUpdateRequest;
import com.linde.linde_backend.entities.usuario.Usuario;
import com.linde.linde_backend.repositories.usuario.UsuarioRepository;
import com.linde.linde_backend.services.usuario.UsuarioService;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/api/v1/usuarios")
@RequiredArgsConstructor
public class UsuarioRestController {

    private final UsuarioService service;
    private final UsuarioRepository repository;

    @GetMapping
    public ResponseEntity<List<UsuarioResponse>> findAll() {

        return ResponseEntity.ok(service.findAll());
    }

    @GetMapping("/{id}")
    public ResponseEntity<UsuarioResponse> findById(
            @PathVariable Integer id) {

        return ResponseEntity.ok(service.findById(id));
    }

    @GetMapping("/me")
    public ResponseEntity<UsuarioResponse> me(
            Authentication authentication) {

        Usuario usuario = repository.findByCorreo(authentication.getName())
                .orElseThrow(() -> new RuntimeException("Usuario no encontrado"));

        return ResponseEntity.ok(service.findById(usuario.getIdUsuario()));
    }

    @PutMapping("/{id}")
    public ResponseEntity<UsuarioResponse> actualizar(
            @PathVariable Integer id,
            @Valid @RequestBody UsuarioUpdateRequest request) {

        return ResponseEntity.ok(
                service.actualizar(id, request)
        );
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> desactivar(
            @PathVariable Integer id) {

        service.desactivar(id);

        return ResponseEntity.noContent().build();
    }
}