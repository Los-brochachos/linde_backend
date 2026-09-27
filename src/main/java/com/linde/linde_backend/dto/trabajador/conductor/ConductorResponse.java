package com.linde.linde_backend.dto.trabajador.conductor;

import java.time.LocalDate;
import java.time.LocalDateTime;

import com.linde.linde_backend.utils.Estado;
import com.linde.linde_backend.utils.RolesEnum;

public record ConductorResponse(

    Integer idTrabajador,

    // Datos del trabajador
    String nombres,
    String apellidos,
    String dni,
    String telefono,
    String direccion,
    LocalDate fechaIngreso,
    Estado estado,

    // Datos del usuario
    String correo,
    RolesEnum rol,

    // Datos del conductor
    String licenciaConducir,
    String categoriaLicencia,
    LocalDate fechaVencimientoLicencia,

    // Auditoría
    LocalDateTime fechaCreacion,
    LocalDateTime fechaActualizacion

) {}

