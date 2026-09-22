package com.linde.linde_backend.dto.trabajador;

import java.time.LocalDate;

import jakarta.validation.constraints.Future;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

public record ConductorRequest(

    @NotBlank(message = "La licencia de conducir es obligatoria")
    @Size(max = 30, message = "La licencia de conducir no puede superar los 30 caracteres")
    String licenciaConducir,

    @NotBlank(message = "La categoría de licencia es obligatoria")
    @Size(max = 30, message = "La categoría de licencia no puede superar los 30 caracteres")
    String categoriaLicencia,

    @NotNull(message = "La fecha de vencimiento de la licencia es obligatoria")
    @Future(message = "La fecha de vencimiento debe ser posterior a la fecha actual")
    LocalDate fechaVencimientoLicencia

) {}