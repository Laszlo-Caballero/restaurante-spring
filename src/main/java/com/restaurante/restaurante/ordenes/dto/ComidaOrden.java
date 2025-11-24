package com.restaurante.restaurante.ordenes.dto;

import com.restaurante.restaurante.comida.response.ComidaRaw;

import lombok.Data;

@Data
public class ComidaOrden {
    private ComidaRaw comida;
    private Long cantidad;
}
