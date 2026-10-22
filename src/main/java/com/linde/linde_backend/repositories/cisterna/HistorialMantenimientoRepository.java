package com.linde.linde_backend.repositories.cisterna;

import java.time.LocalDateTime;
import java.util.List;

import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;

import com.linde.linde_backend.entities.cisterna.HistorialMantenimiento;

public interface HistorialMantenimientoRepository extends JpaRepository<HistorialMantenimiento, Integer> {

    @EntityGraph(attributePaths = {
            "falla",
            "falla.cisterna",
            "falla.tecnico"
    })
    List<HistorialMantenimiento> findByFalla_Cisterna_IdCisterna(
            Integer idCisterna
    );

    @Override
    @EntityGraph(attributePaths = {
            "falla",
            "falla.cisterna",
            "falla.tecnico"
    })
    List<HistorialMantenimiento> findAll();

    /**
     * Devuelve los historiales de mantenimiento registrados
     * dentro del rango de fecha y hora indicado.
     */
    @EntityGraph(attributePaths = {
            "falla",
            "falla.cisterna",
            "falla.tecnico"
    })
    List<HistorialMantenimiento> findByFechaGreaterThanEqualAndFechaLessThan(
            LocalDateTime inicio,
            LocalDateTime fin
    );

    @EntityGraph(attributePaths = {
            "falla",
            "falla.cisterna",
            "falla.tecnico"
    })
    List<HistorialMantenimiento> findByFalla_Cisterna_Nombre(
            String nombre
    );
}