package com.linde.linde_backend.dto.cliente;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;

public record ClienteRequest(

    @NotBlank(message = "El RUC es obligatorio")
    @Pattern(
        regexp = "\\d{11}",
        message = "El RUC debe tener 11 caracteres"
    )
    String ruc,

    @NotBlank(message = "Campo obligatorio")
    @Size(
        max = 100,
        message = "La Razón Social debe tener 100 caracteres"
    )
    String rsocial,

    @NotBlank(message = "Campo obligatorio")
    @Size(
        max = 150,
        message = "La dirección debe tener 150 caracteres"
    )
    String direccion,

    @NotBlank(message = "Campo obligatorio")
    @Size(
        max = 20,
        message = "El teléfono debe tener 20 caracteres"
    )
    String telefono,

    @NotBlank(message = "Correo obligatorio")
    @Email(message = "El correo no tiene formato")
    @Size(
        max = 100,
        message = "El correo debe tener máximo 100 caracteres"
    )
    String correo,

    @NotNull(message = "El usuario es obligatorio")
    Integer idUsuario

) {}
