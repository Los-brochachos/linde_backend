package com.linde.linde_backend.repositories.trabajador;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.linde.linde_backend.entities.trabajador.Programador;
import com.linde.linde_backend.utils.Estado;

public interface ProgramadorRepository extends JpaRepository<Programador, Integer> {

    List<Programador> findByTrabajadorEstado(Estado estado);

    Optional<Programador> findByIdTrabajadorAndTrabajadorEstado(
            Integer idTrabajador,
            Estado estado
    );
}

