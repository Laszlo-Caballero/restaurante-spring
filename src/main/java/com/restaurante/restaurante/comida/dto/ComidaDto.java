package com.restaurante.restaurante.comida.dto;

import java.util.Set;

import com.restaurante.restaurante.comida.entity.Comida;

import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ComidaDto {

    @NotBlank(message = "El nombre no puede estar vacío")
    private String nombre;

    @NotNull(message = "El precio es obligatorio")
    @DecimalMin(value = "1.0", message = "El precio debe ser mayor o igual a 1.0")
    private Double precio;

    @NotBlank(message = "La descripción no puede estar vacía")
    private String descripcion;

    @NotNull(message = "El estado de disponibilidad es obligatorio")
    private Boolean disponible;

    @NotNull(message = "Las categorías son obligatorias")
    @Size(min = 1, message = "Debe haber al menos una categoría")
    private Set<@Min(value = 0, message = "El ID de categoría debe ser mayor o igual a 1") Long> categoriaIds;

    public Comida toEntity() {
        Comida comida = new Comida();
        comida.setNombre(this.nombre);
        comida.setPrecio(this.precio);
        comida.setDescripcion(this.descripcion);
        comida.setDisponible(this.disponible);
        return comida;
    }
}
