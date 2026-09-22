package com.linde.linde_backend.repositories;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.linde.linde_backend.entities.cisterna.Cisterna;

public interface CisternaRepository extends JpaRepository<Cisterna, Integer> {
    Optional<Cisterna> findByPlaca(String placa);
    Optional<Cisterna> findByNombre(String nombre);
}
