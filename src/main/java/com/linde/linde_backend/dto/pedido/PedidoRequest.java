package com.linde.linde_backend.dto.pedido;

import java.time.LocalDate;

import com.linde.linde_backend.utils.PrioridadPedido;

import jakarta.validation.constraints.NotNull;

public record PedidoRequest(

    @NotNull(message = "La fecha de entrega estimada es obligatoria")
    LocalDate fechaEntregaEstimada,

    @NotNull(message = "La prioridad es obligatoria")
    PrioridadPedido prioridad,

    @NotNull(message = "El cliente es obligatorio")
    Integer idCliente

) {}