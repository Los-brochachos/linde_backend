package com.linde.linde_backend.dto.cisterna;

import java.math.BigDecimal;
import java.time.LocalDateTime;

import jakarta.validation.constraints.Digits;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import jakarta.validation.constraints.Size;

public record HistorialMantenimientoRequest(
    @NotNull  LocalDateTime fecha,
    @NotBlank @Size (max= 250) String descripcion,
    @NotBlank @Size (max= 250) String resultado,
    @Size (max= 250) String observaciones,
    @NotNull @Positive @Digits (integer = 5, fraction = 2, message = "Precio inválido") BigDecimal costo,
    @NotNull Integer idCisterna,
    @NotNull Integer idFalla,
    @NotNull Integer idTrabajador
) {

}
