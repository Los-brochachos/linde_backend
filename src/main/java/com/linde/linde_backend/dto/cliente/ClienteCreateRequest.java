package com.linde.linde_backend.dto.cliente;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;

public record ClienteCreateRequest(

    @NotBlank
    @Pattern(regexp = "\\d{11}")
    String ruc,

    @NotBlank
    @Size(max = 100)
    String razonSocial,

    @Size(max = 150)
    String direccion,

    @Size(max = 20)
    String telefono,

    @NotBlank
    @Email
    @Size(max = 100)
    String correo,

    @NotBlank
    @Size(min = 6, max = 100)
    String contraseña

) {}