package com.restaurante.restaurante.pedido;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import com.restaurante.restaurante.auth.entity.Usuario;
import com.restaurante.restaurante.comida.respository.ComidaRepository;
import com.restaurante.restaurante.mesas.repository.MesaRepository;
import com.restaurante.restaurante.pedido.dto.AgregarItemDto;
import com.restaurante.restaurante.pedido.dto.CompletarPedidoDto;
import com.restaurante.restaurante.pedido.dto.PedidoDto;
import com.restaurante.restaurante.pedido.entity.Pedido;
import com.restaurante.restaurante.pedido.entity.PedidoComida;
import com.restaurante.restaurante.pedido.enums.PedidoEnum;
import com.restaurante.restaurante.pedido.repository.PedidoComidaRepository;
import com.restaurante.restaurante.pedido.repository.PedidoRepository;
import com.restaurante.restaurante.pedido.response.PedidoComidaResponse;
import com.restaurante.restaurante.pedido.response.PedidoRaw;
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
    @Autowired
    private PedidoComidaRepository pedidoComidaRepository;

    public ResponseEntity<ApiResponse<List<PedidoRaw>>> listarPedidos() {
        var pedidos = pedidoRepository.findAll();
        ApiResponse<List<PedidoRaw>> response = new ApiResponse<>(200, "Lista de pedidos",
                PedidoRaw.toResponse(pedidos));
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
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

        Usuario usuario = (Usuario) authentication.getPrincipal();

        var mesaId = pedidoDto.getMesaId();
        var mesa = mesaRepository.findById(mesaId).orElse(null);

        if (mesa == null) {
            ApiResponse<PedidoResponse> response = new ApiResponse<>(404, "Mesa no encontrada", null);
            return ResponseEntity.status(404).body(response);
        }

        List<Long> comidasIds = pedidoDto.getComidas().stream()
                .map(t -> t.getComidaId())
                .toList();

        var comidas = comidaRepository.findByComidaIdIn(comidasIds);

        if (comidas.size() != comidasIds.size()) {
            ApiResponse<PedidoResponse> response = new ApiResponse<>(404,
                    "Algunos items de comida no fueron encontrados",
                    null);
            return ResponseEntity.status(404).body(response);
        }

        Pedido nuevoPedido = pedidoDto.toEntity();
        nuevoPedido.setMesa(mesa);

        pedidoRepository.save(nuevoPedido);

        var comidaPedidos = comidas.stream()
                .map(c -> {
                    var cantidad = pedidoDto.getComidas().stream()
                            .filter(pc -> pc.getComidaId().equals(c.getComidaId()))
                            .findFirst()
                            .get()
                            .getCantidad();
                    PedidoComida pc = new PedidoComida();
                    pc.setComida(c);
                    pc.setCantidad(cantidad);
                    pc.setPedido(nuevoPedido);
                    pc.setUsuario(usuario);
                    pedidoComidaRepository.save(pc);
                    return pc;
                }).toList();
        nuevoPedido.setPedidoComidas(comidaPedidos);

        ApiResponse<PedidoResponse> response = new ApiResponse<>(201, "Pedido creado exitosamente",
                PedidoResponse.fromEntity(nuevoPedido));
        return ResponseEntity.status(201).body(response);
    }

    public ResponseEntity<ApiResponse<List<PedidoComidaResponse>>> agregarItem(Long id, AgregarItemDto items) {

        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

        Usuario usuario = (Usuario) authentication.getPrincipal();

        var findPedido = pedidoRepository.findById(id).orElse(null);
        if (findPedido == null) {
            ApiResponse<List<PedidoComidaResponse>> response = new ApiResponse<>(404, "Pedido no encontrado", null);
            return ResponseEntity.status(404).body(response);
        }

        var estado = findPedido.getEstado();
        if (estado != PedidoEnum.PENDIENTE) {
            ApiResponse<List<PedidoComidaResponse>> response = new ApiResponse<>(400,
                    "No se pueden agregar items a un pedido que no está pendiente", null);
            return ResponseEntity.status(400).body(response);
        }

        var newItems = new ArrayList<PedidoComida>();

        for (var item : items.getItems()) {
            var existing = findPedido.getPedidoComidas().stream()
                    .filter(pc -> pc.getComida().getComidaId().equals(item.getComidaId()))
                    .findFirst();

            if (existing.isPresent()) {
                var pedidoComida = existing.get();
                int nuevaCantidad = pedidoComida.getCantidad() + item.getCantidad();
                pedidoComida.setCantidad(nuevaCantidad);
                pedidoComida.setUsuario(usuario);
                pedidoComidaRepository.save(pedidoComida);
                newItems.add(pedidoComida);
            } else {
                var nueva = new PedidoComida();
                var comida = comidaRepository.findById(item.getComidaId())
                        .orElseThrow(() -> new RuntimeException("Comida no encontrada"));

                nueva.setComida(comida);
                nueva.setCantidad(item.getCantidad());
                nueva.setPedido(findPedido);
                nueva.setUsuario(usuario);
                pedidoComidaRepository.save(nueva);
                newItems.add(nueva);
            }
        }

        ApiResponse<List<PedidoComidaResponse>> response = new ApiResponse<>(200, "Items agregados al pedido",
                PedidoComidaResponse.toResponse(newItems));
        return ResponseEntity.ok(response);
    }

    public ResponseEntity<ApiResponse<PedidoRaw>> completarPedido(Long id, CompletarPedidoDto completarPedidoDto) {
        var pedido = pedidoRepository.findById(id).orElse(null);
        if (pedido == null) {
            ApiResponse<PedidoRaw> response = new ApiResponse<>(404, "Pedido no encontrado", null);
            return ResponseEntity.status(404).body(response);
        }

        pedido.setEstado(PedidoEnum.PAGADO);
        pedido.setMetodoPago(completarPedidoDto.getMetodoPago());

        pedidoRepository.save(pedido);

        ApiResponse<PedidoRaw> response = new ApiResponse<>(200, "Pedido completado", PedidoRaw.fromEntity(pedido));
        return ResponseEntity.ok(response);
    }

    public ResponseEntity<ApiResponse<PedidoRaw>> cancelarPedido(Long id) {
        var pedido = pedidoRepository.findById(id).orElse(null);
        if (pedido == null) {
            ApiResponse<PedidoRaw> response = new ApiResponse<>(404, "Pedido no encontrado", null);
            return ResponseEntity.status(404).body(response);
        }

        pedido.setEstado(PedidoEnum.CANCELADO);
        pedidoRepository.save(pedido);

        ApiResponse<PedidoRaw> response = new ApiResponse<>(200, "Pedido cancelado", PedidoRaw.fromEntity(pedido));
        return ResponseEntity.ok(response);
    }

}
