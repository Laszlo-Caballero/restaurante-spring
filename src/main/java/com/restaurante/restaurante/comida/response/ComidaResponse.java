package com.restaurante.restaurante.comida.response;

import java.util.List;
import java.util.stream.Collectors;

import com.restaurante.restaurante.categoria.response.CategoriaRaw;
import com.restaurante.restaurante.comida.entity.Comida;
import com.restaurante.restaurante.recursos.response.RecursoRaw;

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
    private Long cantidadPedidos;
    private Long ventasTotales;
    private List<CategoriaRaw> categorias;
    private RecursoRaw recurso;

    public static List<ComidaResponse> toResponse(List<Comida> comidas) {
        return comidas.stream().map(comida -> new ComidaResponse(
                comida.getComidaId(),
                comida.getNombre(),
                comida.getDescripcion(),
                comida.getPrecio(),
                comida.getDisponible(),
                comida.getCantidadPedidos(),
                comida.getVentasTotales(),
                CategoriaRaw.toListResponse(comida.getCategorias()),
                RecursoRaw.fromEntity(comida.getRecurso())))
                .collect(Collectors.toList());
    }

    public static ComidaResponse fromEntity(Comida comida) {
        return new ComidaResponse(
                comida.getComidaId(),
                comida.getNombre(),
                comida.getDescripcion(),
                comida.getPrecio(),
                comida.getDisponible(),
                comida.getCantidadPedidos(),
                comida.getVentasTotales(),
                CategoriaRaw.toListResponse(comida.getCategorias()),
                RecursoRaw.fromEntity(comida.getRecurso()));
    }
}
