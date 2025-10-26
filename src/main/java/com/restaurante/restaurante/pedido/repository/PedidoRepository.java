package com.restaurante.restaurante.pedido.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.restaurante.restaurante.pedido.entity.Pedido;

public interface PedidoRepository extends JpaRepository<Pedido, Long> {

}
