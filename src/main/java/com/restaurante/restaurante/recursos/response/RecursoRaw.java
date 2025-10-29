package com.restaurante.restaurante.recursos.response;

import java.util.List;

import com.restaurante.restaurante.recursos.entity.Recurso;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class RecursoRaw {

    private Long recursoId;
    private String nombre;

    public static RecursoRaw fromEntity(Recurso recurso) {
        return new RecursoRaw(
                recurso.getRecursoId(),
                recurso.getNombre());
    }

    public static List<RecursoRaw> toResponse(List<Recurso> recursos) {
        return recursos.stream()
                .map(RecursoRaw::fromEntity)
                .toList();
    }

}
