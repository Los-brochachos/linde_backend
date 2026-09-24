package com.linde.linde_backend.repositories;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.linde.linde_backend.entities.cisterna.Falla;

public interface FallaRepository extends JpaRepository<Falla, Integer> {
    List<Falla> findByCisterna_IdCisterna(Integer idCisterna);
    List<Falla> findByEstado(String estado);
}