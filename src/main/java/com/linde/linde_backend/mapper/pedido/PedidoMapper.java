package com.linde.linde_backend.mapper.pedido;

import org.springframework.stereotype.Component;

import com.linde.linde_backend.dto.pedido.PedidoRequest;
import com.linde.linde_backend.dto.pedido.PedidoResponse;
import com.linde.linde_backend.entities.cliente.Cliente;
import com.linde.linde_backend.entities.pedido.Pedido;
import com.linde.linde_backend.utils.EstadoPedido;

@Component
public class PedidoMapper {

    public Pedido toEntity(
            PedidoRequest request,
            Cliente cliente) {

        return Pedido.builder()
                .fechaEntregaEstimada(request.fechaEntregaEstimada())
                .prioridad(request.prioridad())
                .estado(EstadoPedido.RECIBIDO)
                .cliente(cliente)
                .build();
    }

    public PedidoResponse toResponse(Pedido pedido) {

        return new PedidoResponse(
                pedido.getIdPedido(),
                pedido.getFechaRegistro(),
                pedido.getEstado(),
                pedido.getFechaEntregaEstimada(),
                pedido.getPrioridad(),
                pedido.getCliente().getIdCliente()
        );
    }
}