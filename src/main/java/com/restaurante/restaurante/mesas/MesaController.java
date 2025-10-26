package com.restaurante.restaurante.mesas;

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

import com.restaurante.restaurante.mesas.dto.MesaDto;

import jakarta.validation.Valid;

@RestController
@RequestMapping("/api/v1/mesas")
public class MesaController {
    @Autowired
    private MesaService mesaService;

    @GetMapping
    public ResponseEntity<?> listarMesas() {
        return mesaService.listarMesas();
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> obtenerMesaPorId(@PathVariable Long id) {
        return mesaService.obtenerMesaPorId(id);
    }

    @PostMapping
    public ResponseEntity<?> crearMesa(@Valid @RequestBody MesaDto mesa) {
        return mesaService.crearMesa(mesa);
    }

    @PutMapping("/{id}")
    public ResponseEntity<?> actualizarMesa(@PathVariable Long id, @Valid @RequestBody MesaDto mesa) {
        return mesaService.actualizarMesa(id, mesa);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> eliminarMesa(@PathVariable Long id) {
        return mesaService.eliminarMesa(id);
    }
}
