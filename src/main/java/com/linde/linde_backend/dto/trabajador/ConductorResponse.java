package com.linde.linde_backend.dto.trabajador;

import java.time.LocalDate;

public record ConductorResponse(

    Integer idTrabajador,
    String nombres,
    String apellidos,
    String dni,
    String licenciaConducir,
    String categoriaLicencia,
    LocalDate fechaVencimientoLicencia

) {}