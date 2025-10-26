package com.restaurante.restaurante.pedido.response;

import java.time.LocalDateTime;
import java.util.List;

import com.restaurante.restaurante.comida.response.ComidaResponse;
import com.restaurante.restaurante.mesas.response.MesaRaw;
import com.restaurante.restaurante.pedido.entity.Pedido;
import com.restaurante.restaurante.pedido.enums.PedidoEnum;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class PedidoResponse {
    private Long pedidoId;
    private LocalDateTime fechaCreacion;
    private String metodoPago;
    private PedidoEnum estado;
    private MesaRaw mesa;
    private Double total;
    private List<ComidaResponse> comida;

    public static PedidoResponse fromEntity(Pedido pedido) {
        return new PedidoResponse(
                pedido.getPedidoId(),
                pedido.getFechaCreacion(),
                pedido.getMetodoPago(),
                pedido.getEstado(),
                MesaRaw.fromEntity(pedido.getMesa()),
                pedido.getTotal(),
                ComidaResponse.toResponse(pedido.getComidas()));
    }

    public static List<PedidoResponse> toResponse(List<Pedido> pedidos) {
        return pedidos.stream().map(PedidoResponse::fromEntity).toList();
    }
}
