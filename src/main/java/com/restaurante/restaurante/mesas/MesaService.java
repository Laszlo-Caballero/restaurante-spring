package com.restaurante.restaurante.mesas;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import com.restaurante.restaurante.mesas.dto.MesaDto;
import com.restaurante.restaurante.mesas.entity.Mesa;
import com.restaurante.restaurante.mesas.repository.MesaRepository;
import com.restaurante.restaurante.utils.ApiResponse;

@Service
public class MesaService {
    @Autowired
    private MesaRepository mesaRepository;

    public ResponseEntity<ApiResponse<List<Mesa>>> listarMesas() {
        List<Mesa> mesas = mesaRepository.findAll();
        ApiResponse<List<Mesa>> response = new ApiResponse<>(200, "Mesas obtenidas exitosamente", mesas);
        return ResponseEntity.ok(response);
    }

    public ResponseEntity<ApiResponse<Mesa>> obtenerMesaPorId(Long id) {
        Mesa mesa = mesaRepository.findById(id).orElse(null);
        if (mesa == null) {
            ApiResponse<Mesa> response = new ApiResponse<>(404, "Mesa no encontrada", null);
            return ResponseEntity.status(404).body(response);
        }
        ApiResponse<Mesa> response = new ApiResponse<>(200, "Mesa obtenida exitosamente", mesa);
        return ResponseEntity.ok(response);
    }

    public ResponseEntity<ApiResponse<Mesa>> crearMesa(MesaDto mesa) {
        Mesa nuevaMesa = mesa.toEntity();
        mesaRepository.save(nuevaMesa);
        ApiResponse<Mesa> response = new ApiResponse<>(201, "Mesa creada exitosamente", nuevaMesa);
        return ResponseEntity.status(201).body(response);
    }

    public ResponseEntity<ApiResponse<Mesa>> actualizarMesa(Long id, MesaDto mesaDto) {
        Mesa mesaExistente = mesaRepository.findById(id).orElse(null);
        if (mesaExistente == null) {
            ApiResponse<Mesa> response = new ApiResponse<>(404, "Mesa no encontrada", null);
            return ResponseEntity.status(404).body(response);
        }
        mesaExistente.setNumero(mesaDto.getNumeroMesa());
        mesaExistente.setCapacidad(mesaDto.getCapacidad());
        mesaRepository.save(mesaExistente);
        ApiResponse<Mesa> response = new ApiResponse<>(200, "Mesa actualizada exitosamente", mesaExistente);
        return ResponseEntity.ok(response);
    }

    public ResponseEntity<ApiResponse<Void>> eliminarMesa(Long id) {
        Mesa mesaExistente = mesaRepository.findById(id).orElse(null);
        if (mesaExistente == null) {
            ApiResponse<Void> response = new ApiResponse<>(404, "Mesa no encontrada", null);
            return ResponseEntity.status(404).body(response);
        }
        mesaRepository.deleteById(id);
        ApiResponse<Void> response = new ApiResponse<>(200, "Mesa eliminada exitosamente", null);
        return ResponseEntity.ok(response);
    }
}
