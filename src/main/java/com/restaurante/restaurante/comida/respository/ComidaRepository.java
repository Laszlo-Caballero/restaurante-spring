package com.restaurante.restaurante.comida.respository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.restaurante.restaurante.comida.entity.Comida;

public interface ComidaRepository extends JpaRepository<Comida, Long> {
        public List<Comida> findByComidaIdIn(List<Long> ids);

}
