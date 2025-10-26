package com.restaurante.restaurante.comida;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import com.restaurante.restaurante.categoria.respository.CategoriaRepository;
import com.restaurante.restaurante.comida.dto.ComidaDto;
import com.restaurante.restaurante.comida.entity.Comida;
import com.restaurante.restaurante.comida.respository.ComidaRepository;
import com.restaurante.restaurante.utils.ApiResponse;

@Service
public class ComidaService {
    @Autowired
    private ComidaRepository comidaRepository;

    @Autowired
    private CategoriaRepository categoriaRepository;

    public ApiResponse<List<Comida>> getAllComidas() {
        List<Comida> comidas = comidaRepository.findAll();
        return new ApiResponse<>(200, "Lista de comidas obtenida con éxito", comidas);
    }

    public ResponseEntity<ApiResponse<Comida>> getComidaById(Long id) {
        Comida comida = comidaRepository.findById(id).orElse(null);
        if (comida == null) {
            return ResponseEntity.status(404).body(new ApiResponse<>(404, "Comida no encontrada", null));
        }
        return ResponseEntity.ok(new ApiResponse<>(200, "Comida obtenida con éxito", comida));
    }

    public ResponseEntity<ApiResponse<Comida>> createComida(ComidaDto comidaDto) {
        Comida newComida = comidaDto.toEntity();

        var categoriaIds = comidaDto.getCategoriaIds();

        var findCategorias = categoriaRepository.findByIdIn(categoriaIds);

        if (findCategorias.isEmpty() || findCategorias.size() != categoriaIds.size()) {
            return ResponseEntity.status(400).body(new ApiResponse<>(400, "Algunas categorías no existen", null));
        }

        newComida.setCategorias(findCategorias);

        Comida nuevaComida = comidaRepository.save(newComida);
        return ResponseEntity.status(201).body(new ApiResponse<>(201, "Comida creada con éxito", nuevaComida));
    }

    public ResponseEntity<ApiResponse<Comida>> updateComida(Long id, ComidaDto comidaDto) {
        Comida existingComida = comidaRepository.findById(id).orElse(null);
        if (existingComida == null) {
            return ResponseEntity.status(404).body(new ApiResponse<>(404, "Comida no encontrada", null));
        }
        existingComida.setNombre(comidaDto.getNombre());
        existingComida.setDescripcion(comidaDto.getDescripcion());
        existingComida.setPrecio(comidaDto.getPrecio());
        existingComida.setDisponible(comidaDto.getDisponible());
        Comida updatedComida = comidaRepository.save(existingComida);
        return ResponseEntity.ok(new ApiResponse<>(200, "Comida actualizada con éxito", updatedComida));
    }

    public ResponseEntity<ApiResponse<Void>> deleteComida(Long id) {
        if (!comidaRepository.existsById(id)) {
            return ResponseEntity.status(404).body(new ApiResponse<>(404, "Comida no encontrada", null));
        }
        comidaRepository.deleteById(id);
        return ResponseEntity.ok(new ApiResponse<>(200, "Comida eliminada con éxito", null));
    }

}
