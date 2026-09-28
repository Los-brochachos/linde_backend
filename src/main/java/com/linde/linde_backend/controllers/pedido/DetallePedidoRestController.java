package com.linde.linde_backend.controllers.pedido;

import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.linde.linde_backend.dto.pedido.detallepedido.AgregarCantidadDetalleRequest;
import com.linde.linde_backend.dto.pedido.detallepedido.DetallePedidoRequest;
import com.linde.linde_backend.dto.pedido.detallepedido.DetallePedidoResponse;
import com.linde.linde_backend.services.pedido.DetallePedidoService;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/api/v1/pedidos")
@RequiredArgsConstructor
public class DetallePedidoRestController {

        private final DetallePedidoService detallePedidoService;

        @GetMapping("/{idPedido}/detalles")
        public ResponseEntity<List<DetallePedidoResponse>> listarActivosPorPedido(
                @PathVariable Integer idPedido) {

                return ResponseEntity.ok(
                        detallePedidoService.listarActivosPorPedido(idPedido)
                );
        }

        @GetMapping("/{idPedido}/detalles/todos")
        public ResponseEntity<List<DetallePedidoResponse>> listarTodosPorPedido(
                @PathVariable Integer idPedido) {

                return ResponseEntity.ok(
                        detallePedidoService.listarTodosPorPedido(idPedido)
                );
        }

        @PostMapping("/{idPedido}/detalles")
        public ResponseEntity<DetallePedidoResponse> agregarDetalle(
                @PathVariable Integer idPedido,
                @Valid @RequestBody DetallePedidoRequest request) {

                return ResponseEntity.ok(
                        detallePedidoService.agregarDetalle(idPedido, request)
                );
        }

        @PatchMapping("/{idPedido}/detalles/{idDetalle}/cantidad")
        public ResponseEntity<DetallePedidoResponse> agregarCantidad(
                @PathVariable Integer idPedido,
                @PathVariable Integer idDetalle,
                @Valid @RequestBody AgregarCantidadDetalleRequest request) {

                return ResponseEntity.ok(
                        detallePedidoService.agregarCantidad(
                                idPedido,
                                idDetalle,
                                request
                        )
                );
        }

        @PatchMapping("/{idPedido}/detalles/{idDetalle}/cancelar")
        public ResponseEntity<DetallePedidoResponse> cancelar(
                @PathVariable Integer idPedido,
                @PathVariable Integer idDetalle) {

                return ResponseEntity.ok(
                        detallePedidoService.cancelar(
                                idPedido,
                                idDetalle
                        )
                );
        }
}