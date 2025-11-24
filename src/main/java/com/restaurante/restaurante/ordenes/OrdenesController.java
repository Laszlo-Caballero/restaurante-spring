package com.restaurante.restaurante.ordenes;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.stereotype.Controller;

import com.restaurante.restaurante.ordenes.dto.OrderDto;

@Controller
public class OrdenesController {

    @Autowired
    private OrdenesService ordenesService;

    @MessageMapping("/ordenes")
    @SendTo("/topic/ordenes")
    public List<OrderDto> recibirOrden(@Payload OrderDto orden) {
        return ordenesService.agregarOrden(orden);
    }

    @MessageMapping("/todas-las-ordenes")
    @SendTo("/topic/todas-las-ordenes")
    public List<OrderDto> obtenerTodasLasOrdenes() {
        return ordenesService.obtenerTodasLasOrdenes();
    }
}
