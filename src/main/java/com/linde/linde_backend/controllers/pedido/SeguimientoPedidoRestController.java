package com.linde.linde_backend.controllers.pedido;

import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.linde.linde_backend.dto.pedido.seguimientopedido.SeguimientoPedidoResponse;
import com.linde.linde_backend.services.pedido.SeguimientoPedidoService;

import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/api/v1/pedidos")
@RequiredArgsConstructor
public class SeguimientoPedidoRestController {

    private final SeguimientoPedidoService seguimientoPedidoService;

    @GetMapping("/{idPedido}/seguimiento")
    public ResponseEntity<List<SeguimientoPedidoResponse>> listarPorPedido(
            @PathVariable Integer idPedido) {

        List<SeguimientoPedidoResponse> response =
                seguimientoPedidoService.listarPorPedido(idPedido);

        return ResponseEntity.ok(response);
    }
}