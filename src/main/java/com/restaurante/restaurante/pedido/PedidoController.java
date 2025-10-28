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
import com.restaurante.restaurante.pedido.dto.PedidoDto;

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

    @PutMapping("/completar/{id}")
    public ResponseEntity<String> completarPedido(@PathVariable Long id) {
        return ResponseEntity.ok("Pedido " + id + " completado");
    }

    @DeleteMapping("/cancelar/{id}")
    public ResponseEntity<String> cancelarPedido(@PathVariable Long id) {
        return ResponseEntity.ok("Pedido " + id + " cancelado");
    }
}
