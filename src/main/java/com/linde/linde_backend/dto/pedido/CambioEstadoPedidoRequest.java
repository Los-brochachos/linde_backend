package com.linde.linde_backend.dto.pedido;

import com.linde.linde_backend.utils.EstadoPedido;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

public record CambioEstadoPedidoRequest(

    @NotNull(message = "El estado es obligatorio")
    EstadoPedido estado,

    @Size(max = 250, message = "La observación no puede superar los 250 caracteres")
    String observacion

) {}