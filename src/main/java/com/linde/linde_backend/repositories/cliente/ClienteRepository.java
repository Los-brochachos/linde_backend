package com.linde.linde_backend.repositories.cliente;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.linde.linde_backend.entities.cliente.Cliente;
import com.linde.linde_backend.utils.Estado;

public interface ClienteRepository extends JpaRepository<Cliente,Integer>{
    

    List<Cliente> findByUsuarioEstado(Estado estado);

    Optional<Cliente> findByIdClienteAndUsuarioEstado(
            Integer idCliente,
            Estado estado
    );
} 