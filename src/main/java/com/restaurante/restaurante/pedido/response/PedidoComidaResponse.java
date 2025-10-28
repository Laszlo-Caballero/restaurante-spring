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
public class PedidoComidaResponse {
    private ComidaRaw comida;
    private Integer cantidad;


    public static PedidoComidaResponse fromEntity(PedidoComida pedidoComida) {
        var comidaRaw = ComidaRaw.fromEntity(pedidoComida.getComida());
        return new PedidoComidaResponse(comidaRaw, pedidoComida.getCantidad());
    }

    public static List<PedidoComidaResponse> toResponse(List<PedidoComida> pedidoComidas) {
        return pedidoComidas.stream()
                .map(PedidoComidaResponse::fromEntity)
                .toList();
    }
}
