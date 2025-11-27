package com.restaurante.restaurante.categoria.specification;

import org.springframework.data.jpa.domain.Specification;

import com.restaurante.restaurante.categoria.entity.Categoria;

public class CategoriaSpecification {
    public static Specification<Categoria> hasEstado(Boolean estado) {
        return (root, query, criteriaBuilder) -> {
            if (estado == null) {
                return criteriaBuilder.conjunction();
            }
            return criteriaBuilder.equal(root.get("estado"), estado);
        };
    }
}
