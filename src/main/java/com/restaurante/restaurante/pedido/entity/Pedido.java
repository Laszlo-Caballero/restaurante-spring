package com.restaurante.restaurante.pedido.entity;

import java.time.LocalDateTime;
import java.util.List;

import com.restaurante.restaurante.mesas.entity.Mesa;
import com.restaurante.restaurante.pedido.enums.PedidoEnum;

import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
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

    @OneToMany(mappedBy = "pedido")
    private List<PedidoComida> pedidoComidas;

    public Double getTotal() {
        if (pedidoComidas == null || pedidoComidas.isEmpty()) {
            return 0.0;
        }
        return pedidoComidas.stream()
                .mapToDouble(pc -> pc.getComida().getPrecio() * pc.getCantidad())
                .sum();
    }
}
