package com.restaurante.restaurante.pedido;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import com.restaurante.restaurante.comida.respository.ComidaRepository;
import com.restaurante.restaurante.mesas.repository.MesaRepository;
import com.restaurante.restaurante.pedido.dto.PedidoDto;
import com.restaurante.restaurante.pedido.entity.Pedido;
import com.restaurante.restaurante.pedido.repository.PedidoRepository;
import com.restaurante.restaurante.utils.ApiResponse;

@Service
public class PedidoService {
    @Autowired
    private PedidoRepository pedidoRepository;
    @Autowired
    private MesaRepository mesaRepository;
    @Autowired
    private ComidaRepository comidaRepository;

    public ResponseEntity<ApiResponse<List<Pedido>>> listarPedidos() {
        var pedidos = pedidoRepository.findAll();
        ApiResponse<List<Pedido>> response = new ApiResponse<>(200, "Lista de pedidos", pedidos);
        return ResponseEntity.ok(response);
    }

    public ResponseEntity<ApiResponse<Pedido>> detalles(Long id) {
        var pedido = pedidoRepository.findById(id).orElse(null);
        if (pedido == null) {
            ApiResponse<Pedido> response = new ApiResponse<>(404, "Pedido no encontrado", null);
            return ResponseEntity.status(404).body(response);
        }

        ApiResponse<Pedido> response = new ApiResponse<>(200, "Detalle del pedido", pedido);
        return ResponseEntity.ok(response);
    }

    public ResponseEntity<ApiResponse<Pedido>> crearPedido(PedidoDto pedidoDto) {

        var mesaId = pedidoDto.getMesaId();
        var comidasIds = pedidoDto.getComidas();

        var mesa = mesaRepository.findById(mesaId).orElse(null);

        if (mesa == null) {
            ApiResponse<Pedido> response = new ApiResponse<>(404, "Mesa no encontrada", null);
            return ResponseEntity.status(404).body(response);
        }

        var comidas = comidaRepository.findByComidaIdIn(comidasIds);

        if (comidas.size() != comidasIds.size()) {
            ApiResponse<Pedido> response = new ApiResponse<>(404, "Algunos items de comida no fueron encontrados",
                    null);
            return ResponseEntity.status(404).body(response);
        }

        Pedido nuevoPedido = pedidoDto.toEntity();
        nuevoPedido.setMesa(mesa);
        nuevoPedido.setComidas(comidas);

        pedidoRepository.save(nuevoPedido);
        ApiResponse<Pedido> response = new ApiResponse<>(201, "Pedido creado exitosamente", nuevoPedido);
        return ResponseEntity.status(201).body(response);
    }
}
