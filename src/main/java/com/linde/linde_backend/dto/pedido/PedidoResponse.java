package com.linde.linde_backend.dto.pedido;

import java.time.LocalDate;

import com.linde.linde_backend.utils.EstadoPedido;
import com.linde.linde_backend.utils.PrioridadPedido;

public record PedidoResponse(

    Integer idPedido,
    LocalDate fechaRegistro,
    EstadoPedido estado,
    LocalDate fechaEntregaEstimada,
    PrioridadPedido prioridad,
    Integer idCliente

) {}