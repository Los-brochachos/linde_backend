package com.linde.linde_backend.dto.cisterna.falla;

import java.time.LocalDateTime;

import com.linde.linde_backend.utils.cisterna.EstadoFalla;

public record FallaResponse(
    Integer idFalla,
    String descripcion,
    LocalDateTime fechaHora,
    EstadoFalla estado,
    Integer idCisterna,
    Integer idTrabajador
) {

}
