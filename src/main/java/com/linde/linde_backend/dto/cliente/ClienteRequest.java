package com.linde.linde_backend.dto.cliente;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;

public record ClienteRequest(
    @NotBlank (message = "El Ruc es obligatorio") 
    @Pattern (regexp = "\\d{11}", message = ("El Ruc debe tener 11 caracteres"))
    String ruc,

    @NotBlank(message = "Campo obligatorio")  
    @Size(max = 100, message = "La Razon Social debe tener 100 carateres ") 
    String rsocial,

    @NotBlank(message = "Campo obligatorio") 
    @Size (max = 150, message = "La dirección debe tener 150 carateres ") 
    String direccion,

    @NotBlank(message =  "Campo obligatorio")  
    @Size (max = 20, message = "El Telefóno debe tener 20 carateres ") 
    String telefono,

    @NotBlank(message = "Correo obligatorio") 
    @Email (message = "El correo no tiene formato") 
    @Size (max = 100, message = "El Correo debe tener  carateres ") 
    String correo 
) {

}
