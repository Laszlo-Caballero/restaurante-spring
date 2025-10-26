package com.restaurante.restaurante.categoria.response;

import java.util.List;

import com.restaurante.restaurante.categoria.entity.Categoria;
import com.restaurante.restaurante.comida.response.ComidaRaw;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class CategoriaResponse {
    private Long id;
    private String nombre;
    private List<ComidaRaw> comidas;

    public static List<CategoriaResponse> toResponse(List<Categoria> categorias) {
        return categorias.stream()
                .map(CategoriaResponse::fromEntity)
                .toList();
    }

    public static CategoriaResponse fromEntity(Categoria categoria) {
        return new CategoriaResponse(
                categoria.getId(),
                categoria.getNombre(),
                ComidaRaw.toResponse(categoria.getComidas()));
    }

}
