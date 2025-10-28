package com.restaurante.restaurante.pedido.response;

import java.util.List;

import com.restaurante.restaurante.comida.response.ComidaRaw;
import com.restaurante.restaurante.pedido.entity.PedidoComida;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class PedidoComidaRaw {
    private ComidaRaw comida;
    private Integer cantidad;

    public static PedidoComidaRaw fromEntity(PedidoComida pedidoComida) {
        var comidaRaw = ComidaRaw.fromEntity(pedidoComida.getComida());
        return new PedidoComidaRaw(comidaRaw, pedidoComida.getCantidad());
    }

    public static List<PedidoComidaRaw> toResponse(List<PedidoComida> pedidoComidas) {
        return pedidoComidas.stream()
                .map(PedidoComidaRaw::fromEntity)
                .toList();
    }
}
