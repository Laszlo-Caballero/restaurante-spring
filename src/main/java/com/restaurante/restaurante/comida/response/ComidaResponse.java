package com.restaurante.restaurante.comida.response;

import java.util.List;
import java.util.stream.Collectors;

import com.restaurante.restaurante.categoria.response.CategoriaRaw;
import com.restaurante.restaurante.comida.entity.Comida;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class ComidaResponse {
    private Long comidaId;
    private String nombre;
    private String descripcion;
    private Double precio;
    private Boolean disponible;
    private List<CategoriaRaw> categorias;

    public static List<ComidaResponse> toResponse(List<Comida> comidas) {
        return comidas.stream().map(comida -> new ComidaResponse(
                comida.getComidaId(),
                comida.getNombre(),
                comida.getDescripcion(),
                comida.getPrecio(),
                comida.getDisponible(),
                CategoriaRaw.toListResponse(comida.getCategorias())))
                .collect(Collectors.toList());
    }

    public static ComidaResponse fromEntity(Comida comida) {
        return new ComidaResponse(
                comida.getComidaId(),
                comida.getNombre(),
                comida.getDescripcion(),
                comida.getPrecio(),
                comida.getDisponible(),
                CategoriaRaw.toListResponse(comida.getCategorias()));
    }
}
