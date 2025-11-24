package com.restaurante.restaurante.ordenes.dto;

import java.util.List;

import com.restaurante.restaurante.comida.response.ComidaRaw;
import com.restaurante.restaurante.mesas.response.MesaRaw;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class OrderDto {
    private MesaRaw mesa;

    private List<ComidaRaw> comidas;
}
