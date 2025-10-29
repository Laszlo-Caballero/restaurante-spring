package com.restaurante.restaurante.comida.entity;

import java.util.List;

import com.restaurante.restaurante.categoria.entity.Categoria;
import com.restaurante.restaurante.pedido.entity.PedidoComida;
import com.restaurante.restaurante.recursos.entity.Recurso;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.JoinTable;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import jakarta.persistence.Transient;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Comida {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long comidaId;

    private String nombre;

    private String descripcion;

    private Double precio;

    private Boolean disponible;

    @Transient
    private Long cantidadPedidos;

    @Transient
    private Long ventasTotales;

    @ManyToMany(cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    @JoinTable(name = "comida_categoria", joinColumns = @JoinColumn(name = "comida_id"), inverseJoinColumns = @JoinColumn(name = "categoria_id"))
    List<Categoria> categorias;

    @OneToMany(mappedBy = "comida")
    List<PedidoComida> pedidoComidas;

    @ManyToOne()
    @JoinColumn(name = "recurso_id")
    private Recurso recurso;

}
