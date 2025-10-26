package com.restaurante.restaurante.mesas.entity;

import java.util.List;

import com.restaurante.restaurante.pedido.entity.Pedido;

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
    private Long mesaId;

    private Integer numero;

    private Integer capacidad;

    @Column(columnDefinition = "BOOLEAN DEFAULT TRUE")
    private Boolean disponible;

    @OneToMany(mappedBy = "mesa")
    private List<Pedido> pedidos;
}
