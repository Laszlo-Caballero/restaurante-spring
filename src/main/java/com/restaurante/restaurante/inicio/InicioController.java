package com.restaurante.restaurante.inicio;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("api/v1/inicio")
public class InicioController {

    @Autowired
    private InicioService inicioService;

    @GetMapping("/detalles")
    public ResponseEntity<?> obtenerDetalles() {
        return inicioService.obtenerDetalles();
    }
}
