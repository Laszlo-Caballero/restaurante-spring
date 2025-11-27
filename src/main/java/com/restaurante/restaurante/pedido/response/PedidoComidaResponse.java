package com.restaurante.restaurante.pedido.response;

import java.util.List;

import com.restaurante.restaurante.auth.response.UsuarioRaw;
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
public class PedidoComidaResponse {
    private ComidaRaw comida;
    private Integer cantidad;
    private UsuarioRaw usuario;
    private EstadoPedido estado;

    public static PedidoComidaResponse fromEntity(PedidoComida pedidoComida) {
        var comidaRaw = ComidaRaw.fromEntity(pedidoComida.getComida());
        return PedidoComidaResponse.builder()
                .comida(comidaRaw)
                .cantidad(pedidoComida.getCantidad())
                .usuario(UsuarioRaw.fromEntity(pedidoComida.getUsuario()))
                .estado(pedidoComida.getEstado())
                .build();
    }

    public static List<PedidoComidaResponse> toResponse(List<PedidoComida> pedidoComidas) {
        return pedidoComidas.stream()
                .map(PedidoComidaResponse::fromEntity)
                .toList();
    }
}
