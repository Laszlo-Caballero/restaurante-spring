package com.restaurante.restaurante.categoria.response;

import java.util.List;

import com.restaurante.restaurante.categoria.entity.Categoria;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class CategoriaRaw {
    private Long id;
    private String nombre;

    public static List<CategoriaRaw> toListResponse(List<Categoria> categorias) {
        return categorias.stream()
                .map(cat -> new CategoriaRaw(cat.getId(), cat.getNombre()))
                .toList();
    }

    public static CategoriaRaw fromEntity(Categoria categoria) {
        return new CategoriaRaw(categoria.getId(), categoria.getNombre());
    }
}
