package com.linde.linde_backend.controllers.pedido;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.linde.linde_backend.dto.pedido.ProductoRequest;
import com.linde.linde_backend.dto.pedido.ProductoResponse;
import com.linde.linde_backend.services.pedido.ProductoService;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/api/v1/productos")
@RequiredArgsConstructor
@Validated
public class ProductoRestController {

    private final ProductoService service;

    @GetMapping
    public ResponseEntity<List<ProductoResponse>> listar() {

        return ResponseEntity.ok(
                service.listar()
        );
    }

    @GetMapping("/{id}")
    public ResponseEntity<ProductoResponse> buscarPorId(
            @PathVariable Integer id) {

        return ResponseEntity.ok(
                service.buscarPorId(id)
        );
    }

    @PostMapping
    public ResponseEntity<ProductoResponse> insertar(
            @Valid @RequestBody ProductoRequest request) {

        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(service.insertar(request));
    }

    @PutMapping("/{id}")
    public ResponseEntity<ProductoResponse> actualizar(
            @PathVariable Integer id,
            @Valid @RequestBody ProductoRequest request) {

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