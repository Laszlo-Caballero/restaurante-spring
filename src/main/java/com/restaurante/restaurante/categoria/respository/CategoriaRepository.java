package com.restaurante.restaurante.categoria.respository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.restaurante.restaurante.categoria.entity.Categoria;

public interface CategoriaRepository extends JpaRepository<Categoria, Long> {

}
