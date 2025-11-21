package com.restaurante.restaurante.comida.response;

import java.util.List;
import java.util.stream.Collectors;

import com.restaurante.restaurante.categoria.response.CategoriaRaw;
import com.restaurante.restaurante.comida.entity.Comida;
import com.restaurante.restaurante.recursos.response.RecursoRaw;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Data
@Builder
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
        return comidas.stream().map(comida -> fromEntity(comida))
                .collect(Collectors.toList());
    }

    public static ComidaResponse fromEntity(Comida comida) {

        var cantidadPedidos = comida.getPedidoComidas() != null
                ? comida.getPedidoComidas().stream().count()
                : 0L;
        var ventasTotales = comida.getPedidoComidas() != null
                ? comida.getPedidoComidas().stream()
                        .mapToLong(pc -> pc.getCantidad())
                        .sum()
                : 0L;

        return ComidaResponse.builder()
                .comidaId(comida.getComidaId())
                .nombre(comida.getNombre())
                .descripcion(comida.getDescripcion())
                .precio(comida.getPrecio())
                .disponible(comida.getDisponible())
                .cantidadPedidos(cantidadPedidos)
                .ventasTotales(ventasTotales)
                .categorias(CategoriaRaw.toListResponse(comida.getCategorias()))
                .recurso(RecursoRaw.fromEntity(comida.getRecurso()))
                .build();
    }
}
