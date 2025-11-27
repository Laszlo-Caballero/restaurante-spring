package com.restaurante.restaurante.categoria.response;

import java.util.List;

import com.restaurante.restaurante.categoria.entity.Categoria;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Data
@Builder
public class CategoriaRaw {
    private Long id;
    private String nombre;
    private String descripcion;
    private Boolean estado;

    public static List<CategoriaRaw> toListResponse(List<Categoria> categorias) {
        return categorias.stream()
                .map(CategoriaRaw::fromEntity)
                .toList();
    }

    public static CategoriaRaw fromEntity(Categoria categoria) {
        return CategoriaRaw.builder()
                .id(categoria.getId())
                .nombre(categoria.getNombre())
                .descripcion(categoria.getDescripcion())
                .estado(categoria.getEstado())
                .build();
    }
}
