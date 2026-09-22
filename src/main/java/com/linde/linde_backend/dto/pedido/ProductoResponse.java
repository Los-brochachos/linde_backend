package com.linde.linde_backend.dto.pedido;

import java.math.BigDecimal;

import com.linde.linde_backend.utils.Estado;

public record ProductoResponse(

    Integer idProducto,
    String nombre,
    String tipoGas,
    String unidadMedida,
    BigDecimal precioUnitario,
    Estado estado

) {}