package com.linde.linde_backend.dto.pedido;

import java.time.LocalDateTime;

import com.linde.linde_backend.utils.EstadoPedido;

public record SeguimientoPedidoResponse(

    Integer idSeguimiento,
    LocalDateTime fechaHora,
    EstadoPedido estado,
    String observacion,
    Integer idPedido

) {}