package com.linde.linde_backend.controllers.pedido;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.linde.linde_backend.dto.pedido.seguimientopedido.CancelarPedidoRequest;
import com.linde.linde_backend.dto.pedido.pedido.PedidoRequest;
import com.linde.linde_backend.dto.pedido.pedido.PedidoResponse;
import com.linde.linde_backend.dto.pedido.seguimientopedido.CambioEstadoPedidoRequest;
import com.linde.linde_backend.services.pedido.PedidoService;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/api/v1/pedidos")
@RequiredArgsConstructor
@Validated
public class PedidoRestController {

    private final PedidoService service;

    @GetMapping
    public ResponseEntity<List<PedidoResponse>> listar() {
        return ResponseEntity.ok(service.listar());
    }

    @GetMapping("/mis-pedidos")
    public ResponseEntity<List<PedidoResponse>> listarMisPedidos(
            Authentication authentication) {

        return ResponseEntity.ok(
                service.listarMisPedidos(authentication.getName())
        );
    }

    @GetMapping("/mis-pedidos/{id}")
    public ResponseEntity<PedidoResponse> buscarMiPedido(
            @PathVariable Integer id,
            Authentication authentication) {

        return ResponseEntity.ok(
                service.buscarMiPedido(
                        id,
                        authentication.getName()
                )
        );
    }

    @PatchMapping("/mis-pedidos/{id}/cancelar")
    public ResponseEntity<PedidoResponse> cancelarMiPedido(
            @PathVariable Integer id,
            Authentication authentication,
            @Valid @RequestBody CancelarPedidoRequest request) {

        return ResponseEntity.ok(
                service.cancelarMiPedido(
                        id,
                        request,
                        authentication.getName()
                )
        );
    }

    @GetMapping("/{id}")
    public ResponseEntity<PedidoResponse> buscarPorId(
            @PathVariable Integer id) {

        return ResponseEntity.ok(service.buscarPorId(id));
    }

    @PostMapping
    public ResponseEntity<PedidoResponse> insertar(
            Authentication authentication, 
            @Valid @RequestBody PedidoRequest request) {

        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(service.insertar(request,authentication.getName()));
    }

    @PatchMapping("/{id}/estado")
    public ResponseEntity<PedidoResponse> cambiarEstado(
            @PathVariable Integer id,
            @Valid @RequestBody CambioEstadoPedidoRequest request) {

        return ResponseEntity.ok(
                service.cambiarEstado(id, request)
        );
    }

    @PatchMapping("/{id}/cancelar")
    public ResponseEntity<PedidoResponse> cancelar(
            @PathVariable Integer id,
            @Valid @RequestBody CancelarPedidoRequest request) {

        return ResponseEntity.ok(
                service.cancelar(id, request)
        );
    }
}