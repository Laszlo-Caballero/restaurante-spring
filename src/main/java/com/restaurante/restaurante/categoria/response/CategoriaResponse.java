package com.restaurante.restaurante.categoria.response;

import java.util.List;

import com.restaurante.restaurante.categoria.entity.Categoria;
import com.restaurante.restaurante.comida.response.ComidaRaw;
import com.restaurante.restaurante.recursos.response.RecursoRaw;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Data
@Builder
public class CategoriaResponse {
    private Long id;
    private String nombre;
    private String descripcion;
    private List<ComidaRaw> comidas;
    private RecursoRaw recurso;
    private Integer totalComidas;
    private Boolean estado;

    public static List<CategoriaResponse> toResponse(List<Categoria> categorias) {
        return categorias.stream()
                .map(CategoriaResponse::fromEntity)
                .toList();
    }

    public static CategoriaResponse fromEntity(Categoria categoria) {
        return CategoriaResponse.builder()
                .id(categoria.getId())
                .nombre(categoria.getNombre())
                .descripcion(categoria.getDescripcion())
                .comidas(ComidaRaw.toResponse(categoria.getComidas()))
                .recurso(RecursoRaw.fromEntity(categoria.getRecurso()))
                .totalComidas(categoria.getComidas() != null ? categoria.getComidas().size() : 0)
                .estado(categoria.getEstado())
                .build();
    }
}
