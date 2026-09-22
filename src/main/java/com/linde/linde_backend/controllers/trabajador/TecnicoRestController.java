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

import com.linde.linde_backend.dto.trabajador.TecnicoRequest;
import com.linde.linde_backend.dto.trabajador.TecnicoResponse;
import com.linde.linde_backend.services.trabajador.TecnicoService;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/api/v1/tecnicos")
@RequiredArgsConstructor
public class TecnicoRestController {

    private final TecnicoService tecnicoService;

    @GetMapping
    public ResponseEntity<List<TecnicoResponse>> listar() {

        return ResponseEntity.ok(
                tecnicoService.listar()
        );
    }

    @GetMapping("/{idTrabajador}")
    public ResponseEntity<TecnicoResponse> buscarPorId(
            @PathVariable Integer idTrabajador) {

        return ResponseEntity.ok(
                tecnicoService.buscarPorId(idTrabajador)
        );
    }

    @PostMapping("/{idTrabajador}")
    public ResponseEntity<TecnicoResponse> insertar(
            @PathVariable Integer idTrabajador,
            @Valid @RequestBody TecnicoRequest request) {

        return ResponseEntity.status(HttpStatus.CREATED)
                .body(
                        tecnicoService.insertar(
                                idTrabajador,
                                request
                        )
                );
    }

    @PutMapping("/{idTrabajador}")
    public ResponseEntity<TecnicoResponse> actualizar(
            @PathVariable Integer idTrabajador,
            @Valid @RequestBody TecnicoRequest request) {

        return ResponseEntity.ok(
                tecnicoService.actualizar(
                        idTrabajador,
                        request
                )
        );
    }


}