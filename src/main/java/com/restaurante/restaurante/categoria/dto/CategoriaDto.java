package com.restaurante.restaurante.categoria.dto;

import java.util.List;

import com.restaurante.restaurante.categoria.entity.Categoria;

import jakarta.validation.constraints.NotBlank;
import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class CategoriaDto {
    @NotBlank(message = "El nombre de la categoría no puede estar vacío")
    private String nombre;

    public Categoria toEntity() {
        Categoria categoria = new Categoria();
        categoria.setNombre(this.nombre);
        categoria.setComidas(List.of());
        return categoria;
    }
}
