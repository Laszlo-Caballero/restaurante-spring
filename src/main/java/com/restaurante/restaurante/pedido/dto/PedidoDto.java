package com.restaurante.restaurante.pedido.dto;

import java.time.LocalDateTime;
import java.util.List;

import com.restaurante.restaurante.pedido.entity.Pedido;
import com.restaurante.restaurante.pedido.enums.PedidoEnum;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Data;

@Data
public class PedidoDto {

    @NotNull(message = "La mesaId no puede ser nula")
    private Long mesaId;

    @NotNull(message = "Las Comidas son obligatorias")
    @Size(min = 1, message = "La comidaId no puede estar vacía")
    private List<@NotNull(message = "La comidaId no puede ser nula") Long> comidas;

    public Pedido toEntity() {
        Pedido pedido = new Pedido();
        pedido.setFechaCreacion(LocalDateTime.now());
        pedido.setEstado(PedidoEnum.PENDIENTE);
        return pedido;
    }

}
