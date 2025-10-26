package com.restaurante.restaurante.comida;

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

import com.restaurante.restaurante.comida.dto.ComidaDto;
import com.restaurante.restaurante.utils.ApiResponse;

import jakarta.validation.Valid;

@RestController
@RequestMapping("api/v1/comidas")
public class ComidaController {

    @Autowired
    private ComidaService comidaService;

    @GetMapping
    public ApiResponse<?> getAllComidas() {
        return comidaService.getAllComidas();
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> getComidaById(@PathVariable Long id) {
        return comidaService.getComidaById(id);
    }

    @PostMapping()
    public ResponseEntity<?> createComida(@Valid @RequestBody ComidaDto comida) {
        return comidaService.createComida(comida);
    }

    @PutMapping("/{id}")
    public ResponseEntity<?> updateComida(@PathVariable Long id, @Valid @RequestBody ComidaDto comida) {
        return comidaService.updateComida(id, comida);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteComida(@PathVariable Long id) {
        return comidaService.deleteComida(id);
    }
}
