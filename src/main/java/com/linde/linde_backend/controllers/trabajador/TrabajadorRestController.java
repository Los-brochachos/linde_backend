package com.linde.linde_backend.controllers.trabajador;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.linde.linde_backend.dto.trabajador.TrabajadorRequest;
import com.linde.linde_backend.dto.trabajador.TrabajadorResponse;
import com.linde.linde_backend.services.trabajador.TrabajadorService;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("api/v1/trabajador")
@RequiredArgsConstructor
public class TrabajadorRestController {

    private final TrabajadorService trabajadorService;

    @GetMapping
    public ResponseEntity<List<TrabajadorResponse>> listar() {
        return ResponseEntity.ok(trabajadorService.listar());
    }

    @GetMapping("/{id}")
    public ResponseEntity<TrabajadorResponse> buscarPorId(
            @PathVariable Integer id) {

        return ResponseEntity.ok(
                trabajadorService.buscarPorId(id)
        );
    }

    @PostMapping
    public ResponseEntity<TrabajadorResponse> insertar(
            @Valid @RequestBody TrabajadorRequest request) {

        return ResponseEntity.status(HttpStatus.CREATED)
                .body(trabajadorService.insertar(request));
    }

    @PutMapping("/{id}")
    public ResponseEntity<TrabajadorResponse> actualizar(
            @PathVariable Integer id,
            @Valid @RequestBody TrabajadorRequest request) {

        return ResponseEntity.ok(
                trabajadorService.actualizar(id, request)
        );
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> eliminar(
            @PathVariable Integer id) {

        trabajadorService.eliminar(id);

        return ResponseEntity.noContent().build();
    }
}