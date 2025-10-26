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
import com.restaurante.restaurante.pedido.response.PedidoResponse;
import com.restaurante.restaurante.utils.ApiResponse;

@Service
public class PedidoService {
    @Autowired
    private PedidoRepository pedidoRepository;
    @Autowired
    private MesaRepository mesaRepository;
    @Autowired
    private ComidaRepository comidaRepository;

    public ResponseEntity<ApiResponse<List<PedidoResponse>>> listarPedidos() {
        var pedidos = pedidoRepository.findAll();
        ApiResponse<List<PedidoResponse>> response = new ApiResponse<>(200, "Lista de pedidos",
                PedidoResponse.toResponse(pedidos));
        return ResponseEntity.ok(response);
    }

    public ResponseEntity<ApiResponse<PedidoResponse>> detalles(Long id) {
        var pedido = pedidoRepository.findById(id).orElse(null);
        if (pedido == null) {
            ApiResponse<PedidoResponse> response = new ApiResponse<>(404, "Pedido no encontrado", null);
            return ResponseEntity.status(404).body(response);
        }

        ApiResponse<PedidoResponse> response = new ApiResponse<>(200, "Detalle del pedido",
                PedidoResponse.fromEntity(pedido));
        return ResponseEntity.ok(response);
    }

    public ResponseEntity<ApiResponse<PedidoResponse>> crearPedido(PedidoDto pedidoDto) {

        var mesaId = pedidoDto.getMesaId();
        var comidasIds = pedidoDto.getComidas();

        var mesa = mesaRepository.findById(mesaId).orElse(null);

        if (mesa == null) {
            ApiResponse<PedidoResponse> response = new ApiResponse<>(404, "Mesa no encontrada", null);
            return ResponseEntity.status(404).body(response);
        }

        var comidas = comidaRepository.findByComidaIdIn(comidasIds);

        if (comidas.size() != comidasIds.size()) {
            ApiResponse<PedidoResponse> response = new ApiResponse<>(404,
                    "Algunos items de comida no fueron encontrados",
                    null);
            return ResponseEntity.status(404).body(response);
        }

        Pedido nuevoPedido = pedidoDto.toEntity();
        nuevoPedido.setMesa(mesa);
        nuevoPedido.setComidas(comidas);

        pedidoRepository.save(nuevoPedido);
        ApiResponse<PedidoResponse> response = new ApiResponse<>(201, "Pedido creado exitosamente",
                PedidoResponse.fromEntity(nuevoPedido));
        return ResponseEntity.status(201).body(response);
    }
}
