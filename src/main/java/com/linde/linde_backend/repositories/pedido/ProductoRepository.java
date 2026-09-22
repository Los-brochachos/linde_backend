package com.linde.linde_backend.repositories.pedido;

import org.springframework.data.jpa.repository.JpaRepository;

import com.linde.linde_backend.entities.pedido.Producto;

public interface ProductoRepository extends JpaRepository<Producto, Integer> {

}