package com.linde.linde_backend.dto.trabajador;

import java.time.LocalDate;

import com.linde.linde_backend.utils.Estado;
import com.linde.linde_backend.utils.RolesEnum;

public record TrabajadorResponse(

    Integer idTrabajador,
    String nombres,
    String apellidos,
    String dni,
    String telefono,
    String direccion,
    LocalDate fechaIngreso,
    Estado estado,
    Integer idUsuario,
    String correo,
    RolesEnum rol

) {}