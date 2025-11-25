package com.restaurante.restaurante.inicio;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import com.restaurante.restaurante.inicio.response.DetallesResponse;
import com.restaurante.restaurante.pedido.entity.Pedido;
import com.restaurante.restaurante.pedido.repository.PedidoRepository;
import com.restaurante.restaurante.utils.ApiResponse;

@Service
public class InicioService {
    @Autowired
    private PedidoRepository pedidoRepository;

    public ResponseEntity<?> obtenerDetalles() {
        LocalDate hoy = LocalDate.now();
        LocalDateTime inicio = hoy.atStartOfDay();
        LocalDateTime fin = hoy.plusDays(1).atStartOfDay();

        List<Pedido> pedidosHoy = pedidoRepository.findAllByFechaCreacionHoy(
                inicio,
                fin);
        var totalPedidos = pedidosHoy.size();
        var totalIngresos = pedidosHoy.stream()
                .mapToDouble(Pedido::getTotal)
                .sum();
        var detallesResponse = DetallesResponse.builder()
                .totalPedidos(totalPedidos)
                .totalVentas(totalIngresos)
                .build();

        return ResponseEntity.ok(ApiResponse.builder()
                .data(detallesResponse)
                .message("Detalles del día obtenidos con éxito").code(200)
                .build());
    }
}
