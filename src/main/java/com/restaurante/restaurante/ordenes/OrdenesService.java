package com.restaurante.restaurante.ordenes;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.restaurante.restaurante.mesas.repository.MesaRepository;
import com.restaurante.restaurante.mesas.response.MesaRaw;
import com.restaurante.restaurante.ordenes.dto.OrderDto;

import jakarta.annotation.PostConstruct;
import lombok.extern.slf4j.Slf4j;

@Service
@Slf4j
public class OrdenesService {

    @Autowired
    private MesaRepository mesaRepository;

    List<OrderDto> ordenesCache = new ArrayList<>();

    @PostConstruct
    public void init() {

        var mesas = mesaRepository.findByDisponibleTrue();
        mesas.forEach(m -> {

            var order = OrderDto.builder()
                    .mesa(MesaRaw.fromEntity(m))
                    .comidas(new ArrayList<>())
                    .build();
            ordenesCache.add(order);
        });
    }

    public List<OrderDto> agregarOrden(OrderDto orden) {

        var ordenesFiltradas = ordenesCache.stream()
                .map(o -> {
                    var mesaId = o.getMesa().getMesaId();
                    if (mesaId.equals(orden.getMesa().getMesaId())) {
                        orden.getMesa().setDisponible(false);
                        return orden;
                    }

                    return o;
                })
                .toList();
        ordenesCache = ordenesFiltradas;
        return ordenesFiltradas;
    }

    public List<OrderDto> obtenerTodasLasOrdenes() {
        log.info("Obteniendo todas las ordenes: {}", ordenesCache.size());

        var mesas = mesaRepository.findByDisponibleTrue();
        mesas.forEach(m -> {
            ordenesCache.clear();
            var order = OrderDto.builder()
                    .mesa(MesaRaw.fromEntity(m))
                    .comidas(new ArrayList<>())
                    .build();
            ordenesCache.add(order);
        });

        return ordenesCache;
    }
}
