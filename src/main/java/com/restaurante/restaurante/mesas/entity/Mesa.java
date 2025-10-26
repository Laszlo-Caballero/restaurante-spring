package com.restaurante.restaurante.mesas.entity;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonView;
import com.restaurante.restaurante.pedido.entity.Pedido;
import com.restaurante.restaurante.view.Views;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import lombok.Data;

@Entity
@Table(name = "mesas")
@Data
public class Mesa {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @JsonView({ Views.MesaView.class, Views.PedidoView.class })
    private Long mesaId;

    @JsonView({ Views.MesaView.class, Views.PedidoView.class })
    private Integer numero;

    @JsonView({ Views.MesaView.class, Views.PedidoView.class })
    private Integer capacidad;

    @Column(columnDefinition = "BOOLEAN DEFAULT TRUE")
    @JsonView({ Views.MesaView.class, Views.PedidoView.class })
    private Boolean disponible;

    @OneToMany(mappedBy = "mesa")
    @JsonView({ Views.MesaView.class })
    private List<Pedido> pedidos;
}
