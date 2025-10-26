package com.restaurante.restaurante.categoria.respository;

import java.util.List;
import java.util.Set;

import org.springframework.data.jpa.repository.JpaRepository;

import com.restaurante.restaurante.categoria.entity.Categoria;

public interface CategoriaRepository extends JpaRepository<Categoria, Long> {
    List<Categoria> findByIdIn(Set<Long> ids);
}
