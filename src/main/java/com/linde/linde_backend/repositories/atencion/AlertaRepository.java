package com.linde.linde_backend.repositories.atencion;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.linde.linde_backend.entities.atencion.Alerta;
import com.linde.linde_backend.utils.atencion.EstadoAlerta;
import com.linde.linde_backend.utils.atencion.TipoAlerta;

public interface AlertaRepository
        extends JpaRepository<Alerta, Integer> {

    List<Alerta> findByAtencionIdAtencion(
            Integer idAtencion
    );

    List<Alerta> findByEstado(
            EstadoAlerta estado
    );

    List<Alerta> findByTipo(
            TipoAlerta tipo
    );

    List<Alerta> findByAtencionIdAtencionAndEstado(
            Integer idAtencion,
            EstadoAlerta estado
    );
}