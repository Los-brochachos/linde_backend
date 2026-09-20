package com.linde.linde_backend.repositories.Cliente;

import org.springframework.data.jpa.repository.JpaRepository;

import com.linde.linde_backend.entities.Cliente.cliente;

public interface clienteRepository extends JpaRepository<cliente,Integer>{

    
} 