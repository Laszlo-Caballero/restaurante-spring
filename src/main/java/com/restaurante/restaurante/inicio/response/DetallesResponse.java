package com.restaurante.restaurante.inicio.response;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class DetallesResponse {
    private Double totalVentas;
    private int totalPedidos;
}
