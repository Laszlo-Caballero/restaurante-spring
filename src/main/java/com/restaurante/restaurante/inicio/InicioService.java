package com.restaurante.restaurante.inicio;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import com.restaurante.restaurante.comida.entity.Comida;
import com.restaurante.restaurante.comida.response.ComidaResponse;
import com.restaurante.restaurante.comida.respository.ComidaRepository;
import com.restaurante.restaurante.inicio.response.DetallesResponse;
import com.restaurante.restaurante.mesas.repository.MesaRepository;
import com.restaurante.restaurante.pedido.entity.Pedido;
import com.restaurante.restaurante.pedido.enums.PedidoEnum;
import com.restaurante.restaurante.pedido.repository.PedidoRepository;
import com.restaurante.restaurante.pedido.response.PedidoResponse;
import com.restaurante.restaurante.utils.ApiResponse;

@Service
public class InicioService {
        @Autowired
        private PedidoRepository pedidoRepository;

        @Autowired
        private MesaRepository mesaRepository;

        @Autowired
        private ComidaRepository comidaRepository;

        public ResponseEntity<?> obtenerDetalles() {
                LocalDate hoy = LocalDate.now();
                LocalDateTime inicio = hoy.atStartOfDay();
                LocalDateTime fin = hoy.plusDays(1).atStartOfDay();

                List<Pedido> pedidosHoy = pedidoRepository.findAllByFechaCreacionHoy(
                                inicio,
                                fin,
                                PedidoEnum.PAGADO);

                Long totalMesas = mesaRepository.count();

                List<Pedido> ultimosPedidos = pedidoRepository.lastThreeByFecha();

                var totalPedidos = pedidosHoy.size();
                var totalIngresos = pedidosHoy.stream()
                                .mapToDouble(Pedido::getTotal)
                                .sum();
                List<Comida> comidas = comidaRepository.findAll();

                var detallesResponse = DetallesResponse.builder()
                                .totalPedidos(totalPedidos)
                                .totalVentas(totalIngresos)
                                .totalMesas(totalMesas)
                                .ultimosPedidos(ultimosPedidos.stream().map(p -> PedidoResponse.fromEntity(p)).toList())
                                .comidas(comidas.stream().map(c -> ComidaResponse.fromEntity(c))
                                                .toList())
                                .build();

                return ResponseEntity.ok(ApiResponse.builder()
                                .data(detallesResponse)
                                .message("Detalles del día obtenidos con éxito").code(200)
                                .build());
        }
}
