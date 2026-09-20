package com.linde.linde_backend.repositories;

import org.springframework.data.jpa.repository.JpaRepository;

import com.linde.linde_backend.entities.Usuario.Usuario;

public interface UsuarioRepository extends JpaRepository<Usuario,Integer>{

}
