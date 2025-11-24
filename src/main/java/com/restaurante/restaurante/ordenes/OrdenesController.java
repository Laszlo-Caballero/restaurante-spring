package com.restaurante.restaurante.ordenes;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.stereotype.Controller;

import com.restaurante.restaurante.pedido.response.PedidoResponse;

@Controller
public class OrdenesController {

    @Autowired
    private OrdenesService ordenesService;

    @MessageMapping("/ordenes")
    @SendTo("/topic/ordenes")
    public PedidoResponse recibirOrden(PedidoResponse orden) {
        return ordenesService.agregarOrden(orden);
    }

    @MessageMapping("/todas-las-ordenes")
    @SendTo("/topic/todas-las-ordenes")
    public java.util.List<PedidoResponse> obtenerTodasLasOrdenes() {
        return ordenesService.obtenerTodasLasOrdenes();
    }
}
