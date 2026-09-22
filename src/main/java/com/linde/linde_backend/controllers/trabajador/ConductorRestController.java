package com.linde.linde_backend.controllers.trabajador;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.linde.linde_backend.dto.trabajador.ConductorRequest;
import com.linde.linde_backend.dto.trabajador.ConductorResponse;
import com.linde.linde_backend.services.trabajador.ConductorService;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/api/v1/conductores")
@RequiredArgsConstructor
public class ConductorRestController {

    private final ConductorService conductorService;

    @GetMapping
    public ResponseEntity<List<ConductorResponse>> listar() {

        return ResponseEntity.ok(
                conductorService.listar()
        );
    }

    @GetMapping("/{idTrabajador}")
    public ResponseEntity<ConductorResponse> buscarPorId(
            @PathVariable Integer idTrabajador) {

        return ResponseEntity.ok(
                conductorService.buscarPorId(idTrabajador)
        );
    }

    @PostMapping("/{idTrabajador}")
    public ResponseEntity<ConductorResponse> insertar(
            @PathVariable Integer idTrabajador,
            @Valid @RequestBody ConductorRequest request) {

        return ResponseEntity.status(HttpStatus.CREATED)
                .body(
                        conductorService.insertar(
                                idTrabajador,
                                request
                        )
                );
    }

    @PutMapping("/{idTrabajador}")
    public ResponseEntity<ConductorResponse> actualizar(
            @PathVariable Integer idTrabajador,
            @Valid @RequestBody ConductorRequest request) {

        return ResponseEntity.ok(
                conductorService.actualizar(
                        idTrabajador,
                        request
                )
        );
    }


}