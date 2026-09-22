package com.linde.linde_backend.repositories.pedido;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.linde.linde_backend.entities.pedido.DetallePedido;

public interface DetallePedidoRepository extends JpaRepository<DetallePedido, Integer> {

    List<DetallePedido> findByPedidoIdPedido(Integer idPedido);

}