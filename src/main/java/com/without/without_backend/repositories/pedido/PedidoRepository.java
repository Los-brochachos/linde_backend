package com.without.without_backend.repositories.pedido;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.without.without_backend.entities.pedido.Pedido;

public interface PedidoRepository extends JpaRepository<Pedido, Integer> {

    @Override
    @org.springframework.data.jpa.repository.EntityGraph(attributePaths = "cliente")
    List<Pedido> findAll();
    
    List<Pedido> findByClienteIdCliente(Integer idCliente);

    Optional<Pedido> findByIdPedidoAndClienteIdCliente(
            Integer idPedido,
            Integer idCliente
    );
}
