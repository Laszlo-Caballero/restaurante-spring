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

import com.fasterxml.jackson.annotation.JsonView;
import com.restaurante.restaurante.pedido.dto.PedidoDto;
import com.restaurante.restaurante.view.Views;

import jakarta.validation.Valid;

@RestController
@RequestMapping("/api/v1/pedidos")
public class PedidoController {
    @Autowired
    private PedidoService pedidoService;

    @GetMapping
    @JsonView(Views.PedidoView.class)
    public ResponseEntity<String> obtenerPedidos() {
        return ResponseEntity.ok("Lista de pedidos");
    }

    @GetMapping("/detalle/{id}")
    @JsonView(Views.PedidoDetalleView.class)
    public ResponseEntity<?> obtenerDetallePedido(@PathVariable Long id) {
        return pedidoService.detalles(id);
    }

    @PostMapping
    @JsonView(Views.PedidoView.class)
    public ResponseEntity<?> crearPedido(@Valid @RequestBody PedidoDto pedidoDto) {
        return pedidoService.crearPedido(pedidoDto);
    }

    @PutMapping("/agregar-item/{id}")
    @JsonView(Views.PedidoView.class)
    public ResponseEntity<String> agregarItemPedido(@PathVariable Long id) {
        return ResponseEntity.ok("Item agregado al pedido " + id);
    }

    @PutMapping("/completar/{id}")
    @JsonView(Views.PedidoView.class)
    public ResponseEntity<String> completarPedido(@PathVariable Long id) {
        return ResponseEntity.ok("Pedido " + id + " completado");
    }

    @DeleteMapping("/cancelar/{id}")
    @JsonView(Views.PedidoView.class)
    public ResponseEntity<String> cancelarPedido(@PathVariable Long id) {
        return ResponseEntity.ok("Pedido " + id + " cancelado");
    }
}
