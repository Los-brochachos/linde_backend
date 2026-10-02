package com.linde.linde_backend.dto.cisterna.cisterna;

import java.math.BigDecimal;

import com.linde.linde_backend.utils.Estado;

public record CisternaResponse(
    Integer idCisterna,
    String placa,
    String nombre,
    BigDecimal capacidad,
    Estado estado
){}