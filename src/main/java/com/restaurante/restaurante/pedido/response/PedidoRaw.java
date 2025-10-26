package com.restaurante.restaurante.pedido.response;

import java.time.LocalDateTime;
import java.util.List;

import com.restaurante.restaurante.pedido.entity.Pedido;
import com.restaurante.restaurante.pedido.enums.PedidoEnum;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class PedidoRaw {
    private Long pedidoId;

    private LocalDateTime fechaCreacion;

    private String metodoPago;

    private PedidoEnum estado;

    private Double total;

    public static PedidoRaw fromEntity(Pedido pedido) {
        return new PedidoRaw(
                pedido.getPedidoId(),
                pedido.getFechaCreacion(),
                pedido.getMetodoPago(),
                pedido.getEstado(),
                pedido.getTotal());
    }

    public static List<PedidoRaw> toResponse(List<Pedido> pedidos) {
        return pedidos.stream().map(PedidoRaw::fromEntity).toList();
    }
}
