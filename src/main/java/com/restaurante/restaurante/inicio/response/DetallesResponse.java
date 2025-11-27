package com.restaurante.restaurante.inicio.response;

import java.util.List;

import com.restaurante.restaurante.comida.response.ComidaResponse;
import com.restaurante.restaurante.pedido.response.PedidoResponse;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class DetallesResponse {
    private Double totalVentas;
    private int totalPedidos;
    private Long totalMesas;
    private List<PedidoResponse> ultimosPedidos;
    private List<ComidaResponse> comidas;
}
