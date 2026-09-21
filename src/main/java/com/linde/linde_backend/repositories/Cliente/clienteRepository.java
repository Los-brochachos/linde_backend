package com.linde.linde_backend.repositories.cliente;

import org.springframework.data.jpa.repository.JpaRepository;

import com.linde.linde_backend.entities.cliente.Cliente;

public interface ClienteRepository extends JpaRepository<Cliente,Integer>{

    
} 