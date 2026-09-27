package com.linde.linde_backend.dto.cliente;

import java.time.LocalDateTime;

import com.linde.linde_backend.utils.RolesEnum;

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