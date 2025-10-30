package com.restaurante.restaurante.categoria.entity;

import java.util.List;

import com.restaurante.restaurante.comida.entity.Comida;
import com.restaurante.restaurante.recursos.entity.Recurso;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.Data;

@Entity
@Table(name = "categorias")
@Data
public class Categoria {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String nombre;

    @ManyToMany(mappedBy = "categorias", cascade = CascadeType.ALL)
    private List<Comida> comidas;

    @ManyToOne
    @JoinColumn(name = "recurso_id")
    private Recurso recurso;
}
