package com.restaurante.restaurante.comida;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import com.restaurante.restaurante.categoria.respository.CategoriaRepository;
import com.restaurante.restaurante.comida.dto.ComidaDto;
import com.restaurante.restaurante.comida.entity.Comida;
import com.restaurante.restaurante.comida.records.ComidaRecord;
import com.restaurante.restaurante.comida.response.ComidaResponse;
import com.restaurante.restaurante.comida.respository.ComidaRepository;
import com.restaurante.restaurante.recursos.repository.RecursoRepository;
import com.restaurante.restaurante.utils.ApiResponse;

@Service
public class ComidaService {
    @Autowired
    private ComidaRepository comidaRepository;

    @Autowired
    private CategoriaRepository categoriaRepository;

    @Autowired
    private RecursoRepository recursoRepository;

    public ApiResponse<List<ComidaResponse>> getAllComidas() {
        List<Comida> comidas = ComidaRecord.toResponse(comidaRepository.findAllWithCantidadTotal());
        List<ComidaResponse> comidaResponses = ComidaResponse.toResponse(comidas);
        return new ApiResponse<>(200, "Lista de comidas obtenida con éxito", comidaResponses);
    }

    public ResponseEntity<ApiResponse<ComidaResponse>> getComidaById(Long id) {
        Comida comida = ComidaRecord.fromEntity(
                comidaRepository.findByIdWithCantidadTotal(id).orElse(null));
        if (comida == null) {
            return ResponseEntity.status(404).body(new ApiResponse<>(404, "Comida no encontrada", null));
        }

        ComidaResponse comidaResponse = ComidaResponse.fromEntity(comida);
        return ResponseEntity.ok(new ApiResponse<>(200, "Comida obtenida con éxito", comidaResponse));
    }

    public ResponseEntity<ApiResponse<ComidaResponse>> createComida(ComidaDto comidaDto) {
        Comida newComida = comidaDto.toEntity();

        var categoriaIds = comidaDto.getCategoriaIds();

        var findCategorias = categoriaRepository.findByIdIn(categoriaIds);

        if (findCategorias.isEmpty() || findCategorias.size() != categoriaIds.size()) {
            return ResponseEntity.status(400).body(new ApiResponse<>(400, "Algunas categorías no existen", null));
        }

        newComida.setCategorias(findCategorias);

        var findRecurso = recursoRepository.findById(comidaDto.getRecursoId()).orElse(null);

        if (findRecurso == null) {
            return ResponseEntity.status(400).body(new ApiResponse<>(400, "Recurso no encontrado", null));
        }

        newComida.setRecurso(findRecurso);

        Comida nuevaComida = comidaRepository.save(newComida);
        ComidaResponse comidaResponse = ComidaResponse.fromEntity(nuevaComida);
        return ResponseEntity.status(201).body(new ApiResponse<>(201, "Comida creada con éxito", comidaResponse));
    }

    public ResponseEntity<ApiResponse<ComidaResponse>> updateComida(Long id, ComidaDto comidaDto) {
        Comida existingComida = comidaRepository.findById(id).orElse(null);
        var categoriaIds = comidaDto.getCategoriaIds();
        if (existingComida == null) {
            return ResponseEntity.status(404).body(new ApiResponse<>(404, "Comida no encontrada", null));
        }

        var findCategorias = categoriaRepository.findByIdIn(categoriaIds);

        if (findCategorias.isEmpty() || findCategorias.size() != categoriaIds.size()) {
            return ResponseEntity.status(400).body(new ApiResponse<>(400, "Algunas categorías no existen", null));
        }

        var findRecurso = recursoRepository.findById(comidaDto.getRecursoId()).orElse(null);

        if (findRecurso == null) {
            return ResponseEntity.status(400).body(new ApiResponse<>(400, "Recurso no encontrado", null));
        }

        existingComida.setNombre(comidaDto.getNombre());
        existingComida.setDescripcion(comidaDto.getDescripcion());
        existingComida.setPrecio(comidaDto.getPrecio());
        existingComida.setDisponible(comidaDto.getDisponible());
        existingComida.setCategorias(findCategorias);
        existingComida.setRecurso(findRecurso);
        Comida updatedComida = comidaRepository.save(existingComida);
        ComidaResponse comidaResponse = ComidaResponse.fromEntity(updatedComida);
        return ResponseEntity.ok(new ApiResponse<>(200, "Comida actualizada con éxito", comidaResponse));
    }

    public ResponseEntity<ApiResponse<Void>> deleteComida(Long id) {
        if (!comidaRepository.existsById(id)) {
            return ResponseEntity.status(404).body(new ApiResponse<>(404, "Comida no encontrada", null));
        }
        comidaRepository.deleteById(id);
        return ResponseEntity.ok(new ApiResponse<>(200, "Comida eliminada con éxito", null));
    }

}
