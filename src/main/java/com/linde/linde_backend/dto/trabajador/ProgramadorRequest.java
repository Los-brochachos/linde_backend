package com.linde.linde_backend.dto.trabajador;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

public record ProgramadorRequest(

    @NotBlank(message = "El área es obligatoria")
    @Size(max = 100, message = "El área no puede superar los 100 caracteres")
    String area,

    @NotBlank(message = "El turno es obligatorio")
    @Size(max = 30, message = "El turno no puede superar los 30 caracteres")
    String turno

) {}