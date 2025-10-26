package com.restaurante.restaurante.categoria.entity;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonView;
import com.restaurante.restaurante.comida.entity.Comida;
import com.restaurante.restaurante.view.Views;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.Table;
import lombok.Data;

@Entity
@Table(name = "categorias")
@Data
public class Categoria {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @JsonView({ Views.CategoriaView.class, Views.ComidaView.class, Views.PedidoView.class })
    private Long id;

    @JsonView({ Views.CategoriaView.class, Views.ComidaView.class, Views.PedidoView.class })
    private String nombre;

    @ManyToMany(mappedBy = "categorias", cascade = CascadeType.ALL)
    @JsonView(Views.CategoriaView.class)
    private List<Comida> comidas;
}
