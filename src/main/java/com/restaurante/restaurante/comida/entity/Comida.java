package com.restaurante.restaurante.comida.entity;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonView;
import com.restaurante.restaurante.categoria.entity.Categoria;
import com.restaurante.restaurante.pedido.entity.Pedido;
import com.restaurante.restaurante.view.Views;

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
    @JsonView({ Views.ComidaView.class, Views.CategoriaView.class })
    private Long comidaId;

    @JsonView({ Views.ComidaView.class, Views.CategoriaView.class })
    private String nombre;

    @JsonView({ Views.ComidaView.class, Views.CategoriaView.class })
    private String descripcion;

    @JsonView({ Views.ComidaView.class, Views.CategoriaView.class })
    private Double precio;

    @JsonView({ Views.ComidaView.class, Views.CategoriaView.class })
    private Boolean disponible;

    @ManyToMany(cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    @JoinTable(name = "comida_categoria", joinColumns = @JoinColumn(name = "comida_id"), inverseJoinColumns = @JoinColumn(name = "categoria_id"))
    @JsonView(Views.ComidaView.class)
    List<Categoria> categorias;

    @ManyToMany(cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    @JoinTable(name = "pedido_comida", joinColumns = @JoinColumn(name = "comida_id"), inverseJoinColumns = @JoinColumn(name = "pedido_id"))
    @JsonView(Views.ComidaView.class)
    List<Pedido> pedidos;
}
