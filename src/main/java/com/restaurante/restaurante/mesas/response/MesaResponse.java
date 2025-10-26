package com.restaurante.restaurante.mesas.response;

import java.util.List;

import com.restaurante.restaurante.mesas.entity.Mesa;
import com.restaurante.restaurante.pedido.response.PedidoRaw;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class MesaResponse {
    private Long mesaId;
    private Integer numeroMesa;
    private Integer capacidad;
    private Boolean disponible;
    private List<PedidoRaw> pedidos;

    public static MesaResponse fromEntity(Mesa mesa) {
        return new MesaResponse(
                mesa.getMesaId(),
                mesa.getNumero(),
                mesa.getCapacidad(),
                mesa.getDisponible(),
                PedidoRaw.toResponse(mesa.getPedidos()));
    }

    public static List<MesaResponse> toResponse(List<Mesa> mesas) {
        return mesas.stream().map(MesaResponse::fromEntity).toList();
    }
}
