package com.restaurante.restaurante.comida.entity;

import java.util.Set;

import com.restaurante.restaurante.categoria.entity.Categoria;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.JoinTable;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.Setter;

@Entity
@Table
@Getter
@Setter
public class Comida {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long comidaId;

    private String nombre;

    private String descripcion;

    private Double precio;

    private Boolean disponible;

    @ManyToMany
    @JoinTable(name = "comida_categoria", joinColumns = @JoinColumn(name = "comida_id"), inverseJoinColumns = @JoinColumn(name = "categoria_id"))
    Set<Categoria> categorias;
}
