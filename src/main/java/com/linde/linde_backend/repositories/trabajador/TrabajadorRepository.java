
package com.linde.linde_backend.repositories.trabajador;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.linde.linde_backend.entities.trabajador.Trabajador;

public interface TrabajadorRepository extends JpaRepository<Trabajador, Integer> {

    Optional<Trabajador> findByDni(String dni);

}
