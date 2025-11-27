package com.restaurante.restaurante.ordenes.dto;

import java.util.List;

import com.restaurante.restaurante.mesas.response.MesaRaw;
import com.restaurante.restaurante.ordenes.enums.EstadoOrden;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class OrderDto {

    private Long ordenId;

    private MesaRaw mesa;

    private List<ComidaOrden> comidas;

    private final EstadoOrden estado;
}
