package com.linde.linde_backend.dto.atencion.alerta;


import java.time.LocalDateTime;

import com.linde.linde_backend.utils.atencion.EstadoAlerta;
import com.linde.linde_backend.utils.atencion.TipoAlerta;

public record AlertaResponse(

        Integer idAlerta,
        TipoAlerta tipo,
        String mensaje,
        LocalDateTime fechaHora,
        EstadoAlerta estado,
        Integer idAtencion

) {}