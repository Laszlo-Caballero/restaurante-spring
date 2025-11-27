package com.restaurante.restaurante.pedido;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.restaurante.restaurante.pedido.dto.AgregarItemDto;
import com.restaurante.restaurante.pedido.dto.CompletarPedidoDto;
import com.restaurante.restaurante.pedido.dto.PedidoDto;
import com.restaurante.restaurante.pedido.enums.EstadoPedido;

import jakarta.validation.Valid;

@RestController
@RequestMapping("/api/v1/pedidos")
public class PedidoController {
    @Autowired
    private PedidoService pedidoService;

    @GetMapping
    public ResponseEntity<?> obtenerPedidos() {
        return pedidoService.listarPedidos();
    }

    @GetMapping("/detalle/{id}")
    public ResponseEntity<?> obtenerDetallePedido(@PathVariable Long id) {
        return pedidoService.detalles(id);
    }

    @PostMapping
    public ResponseEntity<?> crearPedido(@Valid @RequestBody PedidoDto pedidoDto) {
        return pedidoService.crearPedido(pedidoDto);
    }

    @PutMapping("/agregar-item/{id}")
    public ResponseEntity<?> agregarItemPedido(@PathVariable Long id,
            @Valid @RequestBody AgregarItemDto agregarItemDto) {
        return pedidoService.agregarItem(id, agregarItemDto);
    }

    @PutMapping("/cambiar-estado/{id}/{nuevoEstado}")
    public ResponseEntity<?> cambiarEstadoPedidoComida(@PathVariable Long id,
            @PathVariable EstadoPedido nuevoEstado) {
        return pedidoService.actualizarEstadoPedidoComida(id, nuevoEstado);
    }

    @PutMapping("/completar/{id}")
    public ResponseEntity<?> completarPedido(@PathVariable Long id,
            @Valid @RequestBody CompletarPedidoDto completarPedidoDto) {
        return pedidoService.completarPedido(id, completarPedidoDto);
    }

    @DeleteMapping("/cancelar/{id}")
    public ResponseEntity<?> cancelarPedido(@PathVariable Long id) {
        return pedidoService.cancelarPedido(id);
    }
}
