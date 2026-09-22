package com.linde.linde_backend.dto.trabajador;

public record ProgramadorResponse(
    Integer idTrabajador,
    String nombres,
    String apellidos,
    String dni,
    String area,
    String turno
) {}