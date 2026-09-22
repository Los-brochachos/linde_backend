package com.linde.linde_backend.controllers.pedido;

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

import com.linde.linde_backend.dto.pedido.DetallePedidoRequest;
import com.linde.linde_backend.dto.pedido.DetallePedidoResponse;
import com.linde.linde_backend.services.pedido.DetallePedidoService;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/api/v1/pedidos")
@RequiredArgsConstructor
public class DetallePedidoRestController {

    private final DetallePedidoService detallePedidoService;

    @PostMapping("/{idPedido}/detalles")
    public ResponseEntity<DetallePedidoResponse> crear(
            @PathVariable Integer idPedido,
            @Valid @RequestBody DetallePedidoRequest request) {

        DetallePedidoResponse response =
                detallePedidoService.crear(idPedido, request);

        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(response);
    }

    @GetMapping("/{idPedido}/detalles")
    public ResponseEntity<List<DetallePedidoResponse>> listarPorPedido(
            @PathVariable Integer idPedido) {

        List<DetallePedidoResponse> response =
                detallePedidoService.listarPorPedido(idPedido);

        return ResponseEntity.ok(response);
    }

    @PutMapping("/{idPedido}/detalles/{idDetalle}")
    public ResponseEntity<DetallePedidoResponse> actualizar(@PathVariable Integer idPedido, @PathVariable Integer idDetalle, @Valid @RequestBody DetallePedidoRequest request) {

        DetallePedidoResponse response =
                detallePedidoService.actualizar(
                        idPedido,
                        idDetalle,
                        request
                );

        return ResponseEntity.ok(response);
    }
}