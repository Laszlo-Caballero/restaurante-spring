package com.restaurante.restaurante.pedido.dto;

import java.util.List;

import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class AgregarItemDto {
    @NotNull(message = "Los items no pueden ser nulos")
    @Size(min = 1, message = "Debe haber al menos un item")
    public List<@NotNull(message = "Los items no pueden ser nulos") @Valid PedidoComidaDto> items;
}
