package com.linde.linde_backend.dto.trabajador;

public record TecnicoResponse(
    Integer idTrabajador,
    String nombres,
    String apellidos,
    String dni,
    String especialidad,
    String nivelTecnico,
    String certificacion
) {}