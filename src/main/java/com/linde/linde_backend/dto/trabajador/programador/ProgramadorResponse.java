package com.linde.linde_backend.dto.trabajador.programador;

import java.time.LocalDate;
import java.time.LocalDateTime;

import com.linde.linde_backend.utils.Estado;
import com.linde.linde_backend.utils.RolesEnum;
import com.linde.linde_backend.utils.trabajador.NivelIngles;
import com.linde.linde_backend.utils.trabajador.Turno;

public record ProgramadorResponse(

    // Datos del trabajador
    Integer idTrabajador,
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

    // Datos del programador
    NivelIngles nivelIngles,
    Turno turno,

    // Auditoria
    LocalDateTime fechaCreacion,
    LocalDateTime fechaActualizacion
) {}
