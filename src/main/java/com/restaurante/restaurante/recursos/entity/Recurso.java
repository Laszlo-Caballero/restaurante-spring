package com.restaurante.restaurante.recursos.entity;

import java.time.LocalDateTime;
import java.util.List;

import com.restaurante.restaurante.comida.entity.Comida;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "recursos")
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Recurso {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long recursoId;

    private String nombre;

    @Column(columnDefinition = "timestamp default current_timestamp")
    private LocalDateTime fechaCreacion;

    @OneToMany(mappedBy = "recurso")
    private List<Comida> comidas;
}
