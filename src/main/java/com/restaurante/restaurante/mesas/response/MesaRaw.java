package com.restaurante.restaurante.mesas.response;

import java.util.List;

import com.restaurante.restaurante.mesas.entity.Mesa;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class MesaRaw {
    private Long mesaId;
    private Integer numeroMesa;
    private Integer capacidad;
    private Boolean disponible;

    public static MesaRaw fromEntity(Mesa mesa) {
        return new MesaRaw(
                mesa.getMesaId(),
                mesa.getNumero(),
                mesa.getCapacidad(),
                mesa.getDisponible());
    }

    public static List<MesaRaw> toResponse(List<Mesa> mesas) {
        return mesas.stream()
                .map(MesaRaw::fromEntity)
                .toList();
    }
}
