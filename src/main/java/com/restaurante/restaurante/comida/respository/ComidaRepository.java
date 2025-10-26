package com.restaurante.restaurante.comida.respository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.restaurante.restaurante.comida.entity.Comida;

public interface ComidaRepository extends JpaRepository<Comida, Long> {
    
}
