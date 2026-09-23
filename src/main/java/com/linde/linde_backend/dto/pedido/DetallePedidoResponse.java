package com.linde.linde_backend.dto.pedido;

import java.math.BigDecimal;

public record DetallePedidoResponse(
    Integer idDetalle,
    BigDecimal cantidad,
    BigDecimal precioUnitario,
    Integer idProducto,
    String nombreProducto
) {}