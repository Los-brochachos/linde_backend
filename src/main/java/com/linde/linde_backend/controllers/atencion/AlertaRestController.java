package com.linde.linde_backend.controllers.atencion;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import com.linde.linde_backend.dto.atencion.alerta.AlertaRequest;
import com.linde.linde_backend.dto.atencion.alerta.AlertaResponse;
import com.linde.linde_backend.services.atencion.AlertaService;
import com.linde.linde_backend.utils.atencion.EstadoAlerta;
import com.linde.linde_backend.utils.atencion.TipoAlerta;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/api/v1/alertas")
@RequiredArgsConstructor
public class AlertaRestController {

    private final AlertaService service;

    @GetMapping
    public ResponseEntity<List<AlertaResponse>> listar() {
        return ResponseEntity.ok(service.listar());
    }

    @GetMapping("/{id}")
    public ResponseEntity<AlertaResponse> buscarPorId(
            @PathVariable Integer id) {
        return ResponseEntity.ok(service.buscarPorId(id));
    }

    @GetMapping("/atencion/{idAtencion}")
    public ResponseEntity<List<AlertaResponse>> listarPorAtencion(
            @PathVariable Integer idAtencion) {
        return ResponseEntity.ok(
                service.listarPorAtencion(idAtencion)
        );
    }

    @GetMapping("/estado")
    public ResponseEntity<List<AlertaResponse>> listarPorEstado(
            @RequestParam EstadoAlerta estado) {
        return ResponseEntity.ok(
                service.listarPorEstado(estado)
        );
    }

    @GetMapping("/tipo")
    public ResponseEntity<List<AlertaResponse>> listarPorTipo(
            @RequestParam TipoAlerta tipo) {
        return ResponseEntity.ok(
                service.listarPorTipo(tipo)
        );
    }

    @PostMapping
    public ResponseEntity<AlertaResponse> crear(
            @Valid @RequestBody AlertaRequest request,
            Authentication authentication) {

        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(
                        service.crear(
                                request,
                                authentication.getName()
                        )
                );
    }

    @PatchMapping("/{id}/revisar")
    public ResponseEntity<AlertaResponse> marcarRevisada(
            @PathVariable Integer id) {
        return ResponseEntity.ok(
                service.marcarRevisada(id)
        );
    }

    @PatchMapping("/{id}/atender")
    public ResponseEntity<AlertaResponse> marcarAtendida(
            @PathVariable Integer id) {
        return ResponseEntity.ok(
                service.marcarAtendida(id)
        );
    }
}