package com.restaurante.restaurante.ordenes;

import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Service;

import com.restaurante.restaurante.pedido.response.PedidoResponse;

@Service
public class OrdenesService {

    List<PedidoResponse> pedidosCache = new ArrayList<>();

    public PedidoResponse agregarOrden(PedidoResponse orden) {
        pedidosCache.add(orden);
        return orden;
    }

    public List<PedidoResponse> obtenerTodasLasOrdenes() {
        return pedidosCache;
    }

}
