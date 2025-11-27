package com.restaurante.restaurante.pedido.repository;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.restaurante.restaurante.pedido.entity.Pedido;

public interface PedidoRepository extends JpaRepository<Pedido, Long> {
        @Query("""
                        SELECT p FROM Pedido p
                        WHERE p.fechaCreacion >= :inicio AND p.fechaCreacion < :fin
                        """)
        List<Pedido> findAllByFechaCreacionHoy(
                        @Param("inicio") LocalDateTime inicio,
                        @Param("fin") LocalDateTime fin);

        @Query("SELECT p FROM Pedido p LEFT JOIN FETCH p.pedidoComidas WHERE p.id = :id")
        Optional<Pedido> findByIdWithItems(@Param("id") Long id);

}
