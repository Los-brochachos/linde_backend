package com.linde.linde_backend.mapper.pedido;

import org.springframework.stereotype.Component;

import com.linde.linde_backend.dto.pedido.SeguimientoPedidoResponse;
import com.linde.linde_backend.entities.pedido.SeguimientoPedido;

@Component
public class SeguimientoPedidoMapper {

    public SeguimientoPedidoResponse toResponse(
            SeguimientoPedido seguimiento) {

        return new SeguimientoPedidoResponse(
                seguimiento.getIdSeguimiento(),
                seguimiento.getFechaHora(),
                seguimiento.getEstado(),
                seguimiento.getObservacion(),
                seguimiento.getPedido().getIdPedido()
        );
    }
}