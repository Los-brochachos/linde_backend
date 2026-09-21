package com.linde.linde_backend.repositories;

import java.util.List;

import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;

import com.linde.linde_backend.entities.cisterna.Falla;

public interface FallaRepository extends JpaRepository<Falla, Integer>{
   @EntityGraph(attributePaths = {"cisterna", "conductor"})
    List<Falla> findByCisterna_IdCisterna(Integer idCisterna);

    @Override
    @EntityGraph(attributePaths = {"cisterna", "conductor"})
    List<Falla> findAll();  
}