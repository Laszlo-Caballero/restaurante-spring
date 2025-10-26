package com.restaurante.restaurante.comida.entity;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.restaurante.restaurante.categoria.entity.Categoria;
import com.restaurante.restaurante.pedido.entity.Pedido;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.JoinTable;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.Table;
import lombok.Data;

@Entity
@Table
@Data
public class Comida {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long comidaId;

    private String nombre;

    private String descripcion;

    private Double precio;

    private Boolean disponible;

    @ManyToMany(cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    @JoinTable(name = "comida_categoria", joinColumns = @JoinColumn(name = "comida_id"), inverseJoinColumns = @JoinColumn(name = "categoria_id"))
    List<Categoria> categorias;

    @ManyToMany(cascade = CascadeType.ALL, fetch = FetchType.LAZY, mappedBy = "comidas")
    @JsonIgnore
    List<Pedido> pedidos;
}
