package com.linde.linde_backend.repositories.trabajador;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.linde.linde_backend.entities.trabajador.Conductor;
import com.linde.linde_backend.utils.Estado;

public interface ConductorRepository extends JpaRepository<Conductor, Integer> {

    List<Conductor> findByTrabajadorEstado(Estado estado);

    Optional<Conductor> findByIdAndTrabajadorEstado(
            Integer idTrabajador,
            Estado estado
    );

    Optional<Conductor> findByLicenciaConducir(String licenciaConducir);
}

