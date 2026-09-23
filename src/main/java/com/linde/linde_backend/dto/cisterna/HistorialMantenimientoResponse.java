package com.linde.linde_backend.dto.cisterna;

import java.math.BigDecimal;
import java.time.LocalDateTime;

public record HistorialMantenimientoResponse(
    Integer idHistorial,
    LocalDateTime fecha,
    String descripcion,
    String resultado,
    String observaciones,
    BigDecimal costo,
    Integer idCisterna,
    Integer idFalla,
    Integer idTecnico 
) {

}
