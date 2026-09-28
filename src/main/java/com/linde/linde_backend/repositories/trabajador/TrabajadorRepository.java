package com.linde.linde_backend.repositories.trabajador;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.linde.linde_backend.entities.trabajador.Trabajador;
import com.linde.linde_backend.utils.Estado;

public interface TrabajadorRepository extends JpaRepository<Trabajador, Integer> {

    List<Trabajador> findByEstado(Estado estado);

    Optional<Trabajador> findByIdTrabajadorAndEstado(
            Integer idTrabajador,
            Estado estado
    );

    Optional<Trabajador> findByDni(String dni);

    Optional<Trabajador> findByDniAndEstado(
        String dni,
        Estado estado
);
}