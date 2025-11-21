package com.restaurante.restaurante.comida.records;

import java.util.List;

import org.springframework.lang.Nullable;

import com.restaurante.restaurante.comida.entity.Comida;

public record ComidaRecord(
        Comida comida,
        Long cantidadPedidos,
        Long ventasTotales) {
    public static Comida fromEntity(@Nullable ComidaRecord comidaRecord) {

        if (comidaRecord == null) {
            return null;
        }

        Comida comida = comidaRecord.getComida();

        return Comida.builder()
                .comidaId(comida.getComidaId())
                .nombre(comida.getNombre())
                .slug(comida.getSlug())
                .descripcion(comida.getDescripcion())
                .precio(comida.getPrecio())
                .disponible(comida.getDisponible())
                .categorias(comida.getCategorias())
                .pedidoComidas(comida.getPedidoComidas())
                .build();
    }

    public static List<Comida> toResponse(List<ComidaRecord> comidaRecords) {
        return comidaRecords.stream()
                .map(ComidaRecord::fromEntity)
                .toList();
    }

    public Comida getComida() {
        return comida;
    }

    public Long getCantidadPedidos() {
        return cantidadPedidos;
    }

    public Long getVentasTotales() {
        return ventasTotales;
    }
}
