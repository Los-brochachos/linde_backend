package com.linde.linde_backend.dto.cisterna;

import jakarta.validation.constraints.NotBlank;

public record EliminarCisternaRequest(
    @NotBlank(message = "El nombre de la cisterna es obligatorio") String nombre
) {
    
}
