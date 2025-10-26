package com.restaurante.restaurante.pedido.entity;

import java.time.LocalDateTime;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonView;
import com.restaurante.restaurante.comida.entity.Comida;
import com.restaurante.restaurante.mesas.entity.Mesa;
import com.restaurante.restaurante.pedido.enums.PedidoEnum;
import com.restaurante.restaurante.view.Views;

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
    @JsonView({ Views.PedidoView.class, Views.MesaView.class, Views.ComidaView.class, Views.PedidoDetalleView.class })
    private Long pedidoId;

    @JsonView({ Views.PedidoView.class, Views.MesaView.class, Views.ComidaView.class, Views.PedidoDetalleView.class })
    private LocalDateTime fechaCreacion;

    @Enumerated(EnumType.ORDINAL)
    @JsonView({ Views.PedidoView.class, Views.MesaView.class, Views.ComidaView.class, Views.PedidoDetalleView.class })
    private PedidoEnum estado;

    @JsonView({ Views.PedidoView.class, Views.MesaView.class, Views.ComidaView.class, Views.PedidoDetalleView.class })
    private String metodoPago;

    @ManyToOne()
    @JoinColumn(name = "mesa_id")
    @JsonView({ Views.PedidoView.class, Views.ComidaView.class, Views.PedidoDetalleView.class })
    private Mesa mesa;

    @Transient
    @JsonView({ Views.PedidoView.class, Views.MesaView.class, Views.ComidaView.class, Views.PedidoDetalleView.class })
    private Double total;

    @ManyToMany
    @JoinTable(name = "pedido_comida", joinColumns = @JoinColumn(name = "pedido_id"), inverseJoinColumns = @JoinColumn(name = "comida_id"))
    @JsonView({ Views.PedidoDetalleView.class, Views.MesaView.class })
    private List<Comida> comidas;

    public Double getTotal() {
        return comidas != null ? comidas.stream().mapToDouble(Comida::getPrecio).sum() : 0.0;
    }
}
