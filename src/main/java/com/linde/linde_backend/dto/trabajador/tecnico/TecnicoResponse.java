package com.linde.linde_backend.dto.trabajador.tecnico;

import java.time.LocalDate;
import java.time.LocalDateTime;

import com.linde.linde_backend.utils.Estado;
import com.linde.linde_backend.utils.RolesEnum;
import com.linde.linde_backend.utils.trabajador.EspecialidadTecnico;
import com.linde.linde_backend.utils.trabajador.NivelTecnico;

public record TecnicoResponse(
    Integer idTrabajador,
    String nombres,
    String apellidos,
    String dni,
    String telefono,
    String direccion,
    LocalDate fechaIngreso,
    Estado estado,
    String correo,
    RolesEnum rol,
    EspecialidadTecnico especialidad,
    NivelTecnico nivelTecnico,
    LocalDateTime fechaCreacion,
    LocalDateTime fechaActualizacion
) {}
