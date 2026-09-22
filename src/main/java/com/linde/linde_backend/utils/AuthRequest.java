package com.linde.linde_backend.utils;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;

public record AuthRequest(

    @NotBlank(message = "El correo es obligatorio")
    @Email(message = "El correo debe tener un formato válido")
    String correo,

    @NotBlank(message = "La contraseña es obligatoria")
    String contraseña

) {
}