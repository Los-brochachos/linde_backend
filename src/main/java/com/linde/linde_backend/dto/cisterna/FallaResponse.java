package com.linde.linde_backend.dto.cisterna;

import java.time.LocalDateTime;

public record FallaResponse(
    Integer idFalla,
    String descripcion,
    LocalDateTime fechaHora,
    String estado,
    Integer idCisterna,
    Integer idTrabajador
) {

}
