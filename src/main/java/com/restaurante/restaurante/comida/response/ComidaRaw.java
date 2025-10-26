package com.restaurante.restaurante.comida.response;

import java.util.List;

import com.restaurante.restaurante.comida.entity.Comida;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class ComidaRaw {
    private Long id;
    private String nombre;
    private String descripcion;
    private Double precio;
    private Boolean disponible;

    public static List<ComidaRaw> toResponse(List<Comida> comidas) {
        return comidas.stream()
                .map(comida -> new ComidaRaw(
                        comida.getComidaId(),
                        comida.getNombre(),
                        comida.getDescripcion(),
                        comida.getPrecio(),
                        comida.getDisponible()))
                .toList();
    }

    public static ComidaRaw fromEntity(Comida comida) {
        return new ComidaRaw(
                comida.getComidaId(),
                comida.getNombre(),
                comida.getDescripcion(),
                comida.getPrecio(),
                comida.getDisponible());
    }
}
