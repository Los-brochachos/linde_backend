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

import com.linde.linde_backend.dto.trabajador.ProgramadorRequest;
import com.linde.linde_backend.dto.trabajador.ProgramadorResponse;
import com.linde.linde_backend.services.trabajador.ProgramadorService;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/api/v1/programadores")
@RequiredArgsConstructor
public class ProgramadorRestController {

    private final ProgramadorService programadorService;

    @GetMapping
    public ResponseEntity<List<ProgramadorResponse>> listar() {

        return ResponseEntity.ok(
                programadorService.listar()
        );
    }

    @GetMapping("/{idTrabajador}")
    public ResponseEntity<ProgramadorResponse> buscarPorId(
            @PathVariable Integer idTrabajador) {

        return ResponseEntity.ok(
                programadorService.buscarPorId(idTrabajador)
        );
    }

    @PostMapping("/{idTrabajador}")
    public ResponseEntity<ProgramadorResponse> insertar(
            @PathVariable Integer idTrabajador,
            @Valid @RequestBody ProgramadorRequest request) {

        return ResponseEntity.status(HttpStatus.CREATED)
                .body(
                        programadorService.insertar(
                                idTrabajador,
                                request
                        )
                );
    }

    @PutMapping("/{idTrabajador}")
    public ResponseEntity<ProgramadorResponse> actualizar(
            @PathVariable Integer idTrabajador,
            @Valid @RequestBody ProgramadorRequest request) {

        return ResponseEntity.ok(
                programadorService.actualizar(
                        idTrabajador,
                        request
                )
        );
    }


}