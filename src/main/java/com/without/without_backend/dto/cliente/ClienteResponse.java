package com.without.without_backend.dto.cliente;

import java.time.LocalDateTime;

import com.without.without_backend.utils.RolesEnum;

public record ClienteResponse(

    Integer idCliente,

    String ruc,

    String razonSocial,

    String direccion,

    String telefono,

    String correo,

    RolesEnum rol,

    LocalDateTime fechaCreacion,

    LocalDateTime fechaActualizacion

) {}