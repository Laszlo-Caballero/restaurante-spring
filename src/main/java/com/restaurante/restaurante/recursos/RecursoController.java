package com.restaurante.restaurante.recursos;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

@RestController
@RequestMapping("/api/v1/recursos")
public class RecursoController {

    @Autowired
    private RecursoService recursoService;

    @PostMapping
    public ResponseEntity<?> uploadFile(@RequestParam("file") MultipartFile file) {
        return recursoService.crearRecurso(file);
    }

    @GetMapping
    public ResponseEntity<?> getAllRecursos() {
        return recursoService.obtenerTodosLosRecursos();
    }

}
