package com.linde.linde_backend.controllers.cisterna;

import java.time.LocalDate;
import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.linde.linde_backend.dto.cisterna.historialmantenimiento.HistorialMantenimientoRequest;
import com.linde.linde_backend.dto.cisterna.historialmantenimiento.HistorialMantenimientoResponse;
import com.linde.linde_backend.services.cisterna.HistorialMantenimientoService;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/api/v1/historial")
@RequiredArgsConstructor
public class HistorialMantenimientoRestController {

    private final HistorialMantenimientoService service;

    @GetMapping
    public ResponseEntity<List<HistorialMantenimientoResponse>> listar() {
        return ResponseEntity.ok(service.listar());
    }

    @GetMapping("/cisterna/{idCisterna}")
    public ResponseEntity<List<HistorialMantenimientoResponse>> buscarPorCisterna(
            @PathVariable Integer idCisterna) {

        return ResponseEntity.ok(
                service.buscarPorCisterna(idCisterna)
        );
    }

    @GetMapping("/nombre/{nombre}")
    public ResponseEntity<List<HistorialMantenimientoResponse>> buscarPorNombreCisterna(
            @PathVariable String nombre) {

        return ResponseEntity.ok(
                service.buscarPorNombreCisterna(nombre)
        );
    }

    @GetMapping("/fecha/{fecha}")
    public ResponseEntity<List<HistorialMantenimientoResponse>> buscarPorFecha(
            @PathVariable LocalDate fecha) {

        return ResponseEntity.ok(
                service.buscarPorFecha(fecha)
        );
    }

    @PostMapping
    public ResponseEntity<HistorialMantenimientoResponse> crear(
            @Valid @RequestBody HistorialMantenimientoRequest request) {

        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(service.crear(request));
    }
}