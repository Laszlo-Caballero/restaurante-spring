package com.restaurante.restaurante.pedido.dto;

import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class CompletarPedidoDto {

    @NotNull(message = "El estado no puede ser nulo")
    private String metodoPago;
}
