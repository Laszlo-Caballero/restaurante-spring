package com.restaurante.restaurante.pedidoWebsocket;

import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.stereotype.Controller;

@Controller
public class PedidoWebSocketController {
    @MessageMapping("/ordenes")
    @SendTo("/topic/ordenes")
    public String recibirOrden(String orden) {
        // Aquí podrías procesar la orden si es necesario
        return orden; // Simplemente reenvía la orden a los suscriptores
    }
}
