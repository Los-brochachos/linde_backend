package com.linde.linde_backend.repositories.trabajador;

import org.springframework.data.jpa.repository.JpaRepository;

import com.linde.linde_backend.entities.trabajador.Tecnico;

public interface TecnicoRepository extends JpaRepository<Tecnico, Integer> {
}