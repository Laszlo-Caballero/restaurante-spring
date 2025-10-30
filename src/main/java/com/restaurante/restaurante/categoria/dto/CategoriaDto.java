package com.restaurante.restaurante.categoria.dto;

import java.util.List;

import com.restaurante.restaurante.categoria.entity.Categoria;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class CategoriaDto {
    @NotBlank(message = "El nombre de la categoría no puede estar vacío")
    private String nombre;

    @NotBlank(message = "El nombre de la categoría no puede estar vacío")
    private String descripcion;

    @Min(value = 1, message = "El ID del recurso debe ser un número positivo")
    @NotNull(message = "El ID del recurso no puede ser nulo")
    private Long recursoId;

    public Categoria toEntity() {
        Categoria categoria = new Categoria();
        categoria.setNombre(this.nombre);
        categoria.setDescripcion(this.descripcion);
        categoria.setComidas(List.of());
        return categoria;
    }
}
