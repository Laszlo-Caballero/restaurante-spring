package com.restaurante.restaurante.mesas.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.restaurante.restaurante.mesas.entity.Mesa;

public interface MesaRepository extends JpaRepository<Mesa, Long> {
    public List<Mesa> findByDisponibleTrue();
}
