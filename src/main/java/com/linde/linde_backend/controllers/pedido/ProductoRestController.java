package com.linde.linde_backend.controllers.pedido;

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

import com.linde.linde_backend.dto.pedido.producto.ProductoCreateRequest;
import com.linde.linde_backend.dto.pedido.producto.ProductoResponse;
import com.linde.linde_backend.dto.pedido.producto.ProductoUpdateRequest;
import com.linde.linde_backend.services.pedido.ProductoService;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/api/v1/productos")
@RequiredArgsConstructor
public class ProductoRestController {

    private final ProductoService productoService;

    @GetMapping
    public ResponseEntity<List<ProductoResponse>> listarTodos() {
        return ResponseEntity.ok(
                productoService.listarTodos()
        );
    }

    @GetMapping("/activos")
    public ResponseEntity<List<ProductoResponse>> listarActivos() {
        return ResponseEntity.ok(
                productoService.listarActivos()
        );
    }

    @GetMapping("/{id}")
    public ResponseEntity<ProductoResponse> buscarPorId(
            @PathVariable Integer id) {

        return ResponseEntity.ok(
                productoService.buscarPorId(id)
        );
    }

    @GetMapping("/{id}/activo")
    public ResponseEntity<ProductoResponse> buscarActivoPorId(
            @PathVariable Integer id) {

        return ResponseEntity.ok(
                productoService.buscarActivoPorId(id)
        );
    }

    @PostMapping
    public ResponseEntity<ProductoResponse> crearProducto(
            @Valid @RequestBody ProductoCreateRequest request) {

        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(productoService.crearProducto(request));
    }

    @PutMapping("/{id}")
    public ResponseEntity<ProductoResponse> actualizarProducto(
            @PathVariable Integer id,
            @Valid @RequestBody ProductoUpdateRequest request) {

        return ResponseEntity.ok(
                productoService.actualizarProducto(id, request)
        );
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> eliminarProducto(
            @PathVariable Integer id) {

        productoService.eliminarProducto(id);

        return ResponseEntity.noContent().build();
    }
}