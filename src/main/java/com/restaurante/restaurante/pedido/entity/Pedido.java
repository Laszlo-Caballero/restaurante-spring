package com.restaurante.restaurante.pedido.entity;

import java.time.LocalDateTime;
import java.util.List;

import com.restaurante.restaurante.comida.entity.Comida;
import com.restaurante.restaurante.mesas.entity.Mesa;
import com.restaurante.restaurante.pedido.enums.PedidoEnum;

import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.JoinTable;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import jakarta.persistence.Transient;
import lombok.Data;

@Entity
@Table(name = "pedidos")
@Data
public class Pedido {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long pedidoId;

    private LocalDateTime fechaCreacion;

    @Enumerated(EnumType.ORDINAL)
    private PedidoEnum estado;

    private String metodoPago;

    @ManyToOne()
    @JoinColumn(name = "mesa_id")
    private Mesa mesa;

    @Transient
    private Double total;

    @ManyToMany
    @JoinTable(name = "pedido_comida", joinColumns = @JoinColumn(name = "pedido_id"), inverseJoinColumns = @JoinColumn(name = "comida_id"))
    private List<Comida> comidas;

    public Double getTotal() {
        return comidas != null ? comidas.stream().mapToDouble(Comida::getPrecio).sum() : 0.0;
    }
}
