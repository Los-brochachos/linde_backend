package com.linde.linde_backend.dto.cliente;

public record ClienteResponse(
        Integer id,
        String ruc,
        String rsocial,
        String direccion,
        String telefono,
        String correo ) {

}
