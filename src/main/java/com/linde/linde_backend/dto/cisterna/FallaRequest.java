package com.linde.linde_backend.dto.cisterna;

import java.time.LocalDateTime;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

public record FallaRequest(    
    @NotBlank @Size(max = 250) String descripcion,
    @NotNull  LocalDateTime fechaHora,
    @NotBlank @Size(max = 50 , message = "Estado inválido") String estado,
    @NotNull Integer idCisterna,
    @NotNull Integer idTrabajador
){}