package com.restaurante.restaurante.mesas.dto;

import com.restaurante.restaurante.mesas.entity.Mesa;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
public class MesaDto {
    @NotNull(message = "El número de mesa no puede ser nulo")
    @Min(value = 1, message = "El número de mesa debe ser mayor o igual a 1")
    private Integer numeroMesa;

    @NotNull(message = "La capacidad de la mesa no puede ser nula")
    @Min(value = 1, message = "La capacidad de la mesa debe ser mayor o igual a 1")
    private Integer capacidad;

    public Mesa toEntity() {
        Mesa mesa = new Mesa();
        mesa.setNumero(this.numeroMesa);
        mesa.setCapacidad(this.capacidad);
        mesa.setDisponible(true);
        return mesa;
    }
}
