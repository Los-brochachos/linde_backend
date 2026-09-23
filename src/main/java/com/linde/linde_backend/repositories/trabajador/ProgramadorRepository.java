package com.linde.linde_backend.repositories.trabajador;

import org.springframework.data.jpa.repository.JpaRepository;

import com.linde.linde_backend.entities.trabajador.Programador;

public interface ProgramadorRepository
        extends JpaRepository<Programador, Integer> {
}