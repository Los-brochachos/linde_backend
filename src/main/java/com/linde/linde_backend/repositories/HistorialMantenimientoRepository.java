package com.linde.linde_backend.repositories;

import java.time.LocalDateTime;
import java.util.List;

import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;

import com.linde.linde_backend.entities.Cisterna.HistorialMantenimiento;

public interface HistorialMantenimientoRepository extends JpaRepository<HistorialMantenimiento, Integer>{
    @EntityGraph(attributePaths = {"cisterna", "tecnico"})
    List<HistorialMantenimiento> findByCisterna_IdCisterna(Integer idCisterna);

    @Override
    @EntityGraph(attributePaths = {"cisterna", "tecnico"})
    List<HistorialMantenimiento> findAll();

    /**
     * Devuelve los datos del historial de mantenimiento de la fecha indicada
     * @param fecha fecha de búsqueda
     * @return datos del historial registrados en la fecha indicada
     */
    @EntityGraph(attributePaths = {"cisterna", "tecnico"})
    List<HistorialMantenimiento> findByFechaBetween(LocalDateTime inicio, LocalDateTime fin);
}
