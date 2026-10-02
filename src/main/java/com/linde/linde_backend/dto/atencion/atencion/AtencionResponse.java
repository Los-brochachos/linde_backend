package com.linde.linde_backend.dto.atencion.atencion;

import java.time.LocalDateTime;

import com.linde.linde_backend.utils.atencion.EstadoAtencion;

public record AtencionResponse(

        Integer idAtencion,

        LocalDateTime fechaInicioProgramada,

        LocalDateTime fechaFinProgramada,

        LocalDateTime fechaInicio,

        LocalDateTime fechaFin,

        EstadoAtencion estado,

        String observaciones,

        Integer idPedido,

        Integer idConductor,

        Integer idCisterna,

        Integer idProgramador

) {}