package com.restaurante.restaurante.pedido.entity;

import com.restaurante.restaurante.comida.entity.Comida;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "pedido_comida")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class PedidoComida {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long pedidoComidaId;

    @ManyToOne
    @JoinColumn(name = "pedido_id")
    private Pedido pedido;

    @ManyToOne
    @JoinColumn(name = "comida_id")
    private Comida comida;


    private Integer cantidad;
}
