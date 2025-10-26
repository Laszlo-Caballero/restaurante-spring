package com.restaurante.restaurante.categoria;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import com.restaurante.restaurante.categoria.dto.CategoriaDto;
import com.restaurante.restaurante.categoria.entity.Categoria;
import com.restaurante.restaurante.categoria.respository.CategoriaRepository;
import com.restaurante.restaurante.utils.ApiResponse;

@Service
public class CategoriaService {
    @Autowired
    private CategoriaRepository categoriaRepository;

    public ResponseEntity<ApiResponse<List<Categoria>>> getAllCategorias() {
        List<Categoria> categorias = categoriaRepository.findAll();
        ApiResponse<List<Categoria>> response = new ApiResponse<>(200, "Categorias retrieved successfully", categorias);
        return ResponseEntity.ok(response);
    }

    public ResponseEntity<ApiResponse<Categoria>> getCategoriaById(Long id) {
        Categoria categoria = categoriaRepository.findById(id).orElse(null);
        if (categoria == null) {
            ApiResponse<Categoria> response = new ApiResponse<>(404, "Categoria not found", null);
            return ResponseEntity.status(404).body(response);
        }
        ApiResponse<Categoria> response = new ApiResponse<>(200, "Categoria retrieved successfully", categoria);
        return ResponseEntity.ok(response);
    }

    public ResponseEntity<ApiResponse<Categoria>> createCategoria(CategoriaDto categoria) {
        Categoria newCategoria = categoria.toEntity();
        categoriaRepository.save(newCategoria);
        ApiResponse<Categoria> response = new ApiResponse<>(201, "Categoria created successfully", newCategoria);
        return ResponseEntity.status(201).body(response);
    }

    public ResponseEntity<ApiResponse<Categoria>> updateCategoria(Long id, CategoriaDto categoriaDto) {
        Categoria existingCategoria = categoriaRepository.findById(id).orElse(null);
        if (existingCategoria == null) {
            ApiResponse<Categoria> response = new ApiResponse<>(404, "Categoria not found", null);
            return ResponseEntity.status(404).body(response);
        }
        existingCategoria.setNombre(categoriaDto.getNombre());
        categoriaRepository.save(existingCategoria);
        ApiResponse<Categoria> response = new ApiResponse<>(200, "Categoria updated successfully", existingCategoria);
        return ResponseEntity.ok(response);
    }

    public ResponseEntity<ApiResponse<Void>> deleteCategoria(Long id) {
        Categoria existingCategoria = categoriaRepository.findById(id).orElse(null);
        if (existingCategoria == null) {
            ApiResponse<Void> response = new ApiResponse<>(404, "Categoria not found", null);
            return ResponseEntity.status(404).body(response);
        }
        categoriaRepository.deleteById(id);
        ApiResponse<Void> response = new ApiResponse<>(200, "Categoria deleted successfully", null);
        return ResponseEntity.ok(response);
    }
}
