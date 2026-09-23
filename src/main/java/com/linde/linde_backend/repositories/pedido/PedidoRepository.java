package com.linde.linde_backend.repositories.pedido;

import org.springframework.data.jpa.repository.JpaRepository;

import com.linde.linde_backend.entities.pedido.Pedido;

public interface PedidoRepository extends JpaRepository<Pedido, Integer> {

}