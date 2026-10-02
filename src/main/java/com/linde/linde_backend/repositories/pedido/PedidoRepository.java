package com.linde.linde_backend.repositories.pedido;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.linde.linde_backend.entities.pedido.Pedido;

public interface PedidoRepository extends JpaRepository<Pedido, Integer> {
    
    List<Pedido> findByClienteIdCliente(Integer idCliente);

    Optional<Pedido> findByIdPedidoAndClienteIdCliente(
            Integer idPedido,
            Integer idCliente
    );
}