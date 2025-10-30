package com.restaurante.restaurante.categoria;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import com.restaurante.restaurante.categoria.dto.CategoriaDto;
import com.restaurante.restaurante.categoria.entity.Categoria;
import com.restaurante.restaurante.categoria.response.CategoriaResponse;
import com.restaurante.restaurante.categoria.respository.CategoriaRepository;
import com.restaurante.restaurante.recursos.repository.RecursoRepository;
import com.restaurante.restaurante.utils.ApiResponse;

@Service
public class CategoriaService {
    @Autowired
    private CategoriaRepository categoriaRepository;

    @Autowired
    private RecursoRepository recursoRepository;

    public ResponseEntity<ApiResponse<List<CategoriaResponse>>> getAllCategorias() {
        List<Categoria> categorias = categoriaRepository.findAll();
        ApiResponse<List<CategoriaResponse>> response = new ApiResponse<>(200, "Categorias retrieved successfully",
                CategoriaResponse.toResponse(categorias));
        return ResponseEntity.ok(response);
    }

    public ResponseEntity<ApiResponse<CategoriaResponse>> getCategoriaById(Long id) {
        Categoria categoria = categoriaRepository.findById(id).orElse(null);
        if (categoria == null) {
            ApiResponse<CategoriaResponse> response = new ApiResponse<>(404, "Categoria not found", null);
            return ResponseEntity.status(404).body(response);
        }
        ApiResponse<CategoriaResponse> response = new ApiResponse<>(200, "Categoria retrieved successfully",
                CategoriaResponse.fromEntity(categoria));
        return ResponseEntity.ok(response);
    }

    public ResponseEntity<ApiResponse<CategoriaResponse>> createCategoria(CategoriaDto categoria) {
        Categoria newCategoria = categoria.toEntity();

        var recurso = recursoRepository.findById(categoria.getRecursoId()).orElse(null);

        if (recurso == null) {
            ApiResponse<CategoriaResponse> response = new ApiResponse<>(404, "Recurso not found", null);
            return ResponseEntity.status(404).body(response);
        }

        newCategoria.setRecurso(recurso);
        categoriaRepository.save(newCategoria);
        ApiResponse<CategoriaResponse> response = new ApiResponse<>(201, "Categoria created successfully",
                CategoriaResponse.fromEntity(newCategoria));
        return ResponseEntity.status(201).body(response);
    }

    public ResponseEntity<ApiResponse<CategoriaResponse>> updateCategoria(Long id, CategoriaDto categoriaDto) {
        Categoria existingCategoria = categoriaRepository.findById(id).orElse(null);
        if (existingCategoria == null) {
            ApiResponse<CategoriaResponse> response = new ApiResponse<>(404, "Categoria not found", null);
            return ResponseEntity.status(404).body(response);
        }
        existingCategoria.setNombre(categoriaDto.getNombre());
        categoriaRepository.save(existingCategoria);
        ApiResponse<CategoriaResponse> response = new ApiResponse<>(200, "Categoria updated successfully",
                CategoriaResponse.fromEntity(existingCategoria));
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
