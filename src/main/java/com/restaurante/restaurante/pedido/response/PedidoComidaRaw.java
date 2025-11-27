package com.restaurante.restaurante.pedido.response;

import java.util.List;

import com.restaurante.restaurante.comida.response.ComidaRaw;
import com.restaurante.restaurante.pedido.entity.PedidoComida;
import com.restaurante.restaurante.pedido.enums.EstadoPedido;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Data
@Builder
public class PedidoComidaRaw {
    private ComidaRaw comida;
    private Integer cantidad;
    private EstadoPedido estado;
    private Long pedidoComidaId;

    public static PedidoComidaRaw fromEntity(PedidoComida pedidoComida) {
        var comidaRaw = ComidaRaw.fromEntity(pedidoComida.getComida());
        return PedidoComidaRaw.builder()
                .comida(comidaRaw)
                .cantidad(pedidoComida.getCantidad())
                .estado(pedidoComida.getEstado())
                .pedidoComidaId(pedidoComida.getPedidoComidaId())
                .build();
    }

    public static List<PedidoComidaRaw> toResponse(List<PedidoComida> pedidoComidas) {
        return pedidoComidas.stream()
                .map(PedidoComidaRaw::fromEntity)
                .toList();
    }
}
