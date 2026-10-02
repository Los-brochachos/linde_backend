package com.linde.linde_backend.controllers.atencion;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.linde.linde_backend.dto.atencion.atencion.AtencionRequest;
import com.linde.linde_backend.dto.atencion.atencion.AtencionResponse;
import com.linde.linde_backend.dto.atencion.atencion.CancelarAtencionRequest;
import com.linde.linde_backend.services.atencion.AtencionService;
import com.linde.linde_backend.utils.atencion.EstadoAtencion;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/api/v1/atenciones")
@RequiredArgsConstructor
public class AtencionRestController {

    private final AtencionService service;

    @GetMapping
    public ResponseEntity<List<AtencionResponse>> listar() {
        return ResponseEntity.ok(service.listar());
    }

    @GetMapping("/{id}")
    public ResponseEntity<AtencionResponse> buscarPorId(
            @PathVariable Integer id) {

        return ResponseEntity.ok(
                service.buscarPorId(id)
        );
    }

    @GetMapping("/estado")
    public ResponseEntity<List<AtencionResponse>> listarPorEstado(
            @RequestParam EstadoAtencion estado) {

        return ResponseEntity.ok(
                service.listarPorEstado(estado)
        );
    }

    @GetMapping("/pedido/{idPedido}")
    public ResponseEntity<List<AtencionResponse>> listarPorPedido(
            @PathVariable Integer idPedido) {

        return ResponseEntity.ok(
                service.listarPorPedido(idPedido)
        );
    }

    @PostMapping
    public ResponseEntity<AtencionResponse> crear(
            @Valid @RequestBody AtencionRequest request,
            org.springframework.security.core.Authentication authentication) {

        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(
                        service.crear(
                                request,
                                authentication.getName()
                        )
                );
    }

    @PatchMapping("/{id}/iniciar")
    public ResponseEntity<AtencionResponse> iniciar(
            @PathVariable Integer id) {

        return ResponseEntity.ok(
                service.iniciar(id)
        );
    }

    @PatchMapping("/{id}/finalizar")
    public ResponseEntity<AtencionResponse> finalizar(
            @PathVariable Integer id) {

        return ResponseEntity.ok(
                service.finalizar(id)
        );
    }

    @PatchMapping("/{id}/cancelar")
    public ResponseEntity<AtencionResponse> cancelar(
            @PathVariable Integer id,
            @Valid @RequestBody CancelarAtencionRequest request) {

        return ResponseEntity.ok(
                service.cancelar(id, request)
        );
    }
}