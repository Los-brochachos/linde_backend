package com.linde.linde_backend.dto.pedido;

import java.math.BigDecimal;

import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.NotNull;

public record DetallePedidoRequest(

    @NotNull
    @DecimalMin(value = "0.01")
    BigDecimal cantidad,

    @NotNull
    Integer idProducto

) {}