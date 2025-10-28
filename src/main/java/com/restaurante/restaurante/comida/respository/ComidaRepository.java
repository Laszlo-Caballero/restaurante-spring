package com.restaurante.restaurante.comida.respository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.restaurante.restaurante.comida.entity.Comida;
import com.restaurante.restaurante.comida.records.ComidaRecord;

public interface ComidaRepository extends JpaRepository<Comida, Long> {
    public List<Comida> findByComidaIdIn(List<Long> ids);

    @Query("""
            select c, count(pc.comida) as cantidadPedidos, sum(pc.cantidad) as totalVentas from Comida c left join c.pedidoComidas pc
            group by c
            """)
    public List<ComidaRecord> findAllWithCantidadTotal();

    @Query("""
            select c, count(pc.comida) as cantidadPedidos, sum(pc.cantidad) as totalVentas
            from Comida c left join c.pedidoComidas pc
            where c.comidaId = :id
            group by c
            """)
    public Optional<ComidaRecord> findByIdWithCantidadTotal(Long id);
}
