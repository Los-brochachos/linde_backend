package com.linde.linde_backend.dto.cisterna;

import java.math.BigDecimal;

public record CisternaResponse(
    Integer idCisterna,
    String placa,
    String nombre,
    BigDecimal capacidad,
    String estado
){}