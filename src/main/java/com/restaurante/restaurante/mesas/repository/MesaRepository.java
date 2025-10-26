package com.restaurante.restaurante.mesas.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.restaurante.restaurante.mesas.entity.Mesa;

public interface MesaRepository extends JpaRepository<Mesa, Long> {

}
