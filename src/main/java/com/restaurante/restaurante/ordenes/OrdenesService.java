package com.restaurante.restaurante.ordenes;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.restaurante.restaurante.mesas.repository.MesaRepository;
import com.restaurante.restaurante.mesas.response.MesaRaw;
import com.restaurante.restaurante.ordenes.dto.OrderDto;
import com.restaurante.restaurante.ordenes.enums.EstadoOrden;

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
                    .estado(EstadoOrden.EN_ESPERA)
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
        var mesas = mesaRepository.findByDisponibleTrue();
        ordenesCache = mesas.stream().map(m -> {
            var ordenExistente = ordenesCache.stream()
                    .filter(o -> o.getMesa().getMesaId().equals(m.getMesaId()))
                    .findFirst()
                    .orElse(null);
            if (ordenExistente == null) {
                var order = OrderDto.builder()
                        .mesa(MesaRaw.fromEntity(m))
                        .comidas(new ArrayList<>())
                        .build();
                return order;
            }
            var order = OrderDto.builder()
                    .mesa(MesaRaw.fromEntity(m))
                    .comidas(ordenExistente.getComidas())
                    .ordenId(ordenExistente.getOrdenId())
                    .estado(ordenExistente.getEstado())
                    .build();
            return order;
        }).toList();

        return ordenesCache;
    }
}
