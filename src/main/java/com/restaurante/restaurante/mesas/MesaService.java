package com.restaurante.restaurante.mesas;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import com.restaurante.restaurante.mesas.dto.MesaDto;
import com.restaurante.restaurante.mesas.entity.Mesa;
import com.restaurante.restaurante.mesas.repository.MesaRepository;
import com.restaurante.restaurante.mesas.response.MesaResponse;
import com.restaurante.restaurante.utils.ApiResponse;

@Service
public class MesaService {
    @Autowired
    private MesaRepository mesaRepository;

    public ResponseEntity<ApiResponse<List<MesaResponse>>> listarMesas() {
        List<Mesa> mesas = mesaRepository.findAll();
        ApiResponse<List<MesaResponse>> response = new ApiResponse<>(200, "Mesas obtenidas exitosamente",
                MesaResponse.toResponse(mesas));
        return ResponseEntity.ok(response);
    }

    public ResponseEntity<ApiResponse<MesaResponse>> obtenerMesaPorId(Long id) {
        Mesa mesa = mesaRepository.findById(id).orElse(null);
        if (mesa == null) {
            ApiResponse<MesaResponse> response = new ApiResponse<>(404, "Mesa no encontrada", null);
            return ResponseEntity.status(404).body(response);
        }
        ApiResponse<MesaResponse> response = new ApiResponse<>(200, "Mesa obtenida exitosamente",
                MesaResponse.fromEntity(mesa));
        return ResponseEntity.ok(response);
    }

    public ResponseEntity<ApiResponse<MesaResponse>> crearMesa(MesaDto mesa) {
        Mesa nuevaMesa = mesa.toEntity();
        mesaRepository.save(nuevaMesa);
        ApiResponse<MesaResponse> response = new ApiResponse<>(201, "Mesa creada exitosamente",
                MesaResponse.fromEntity(nuevaMesa));
        return ResponseEntity.status(201).body(response);
    }

    public ResponseEntity<ApiResponse<MesaResponse>> actualizarMesa(Long id, MesaDto mesaDto) {
        Mesa mesaExistente = mesaRepository.findById(id).orElse(null);
        if (mesaExistente == null) {
            ApiResponse<MesaResponse> response = new ApiResponse<>(404, "Mesa no encontrada", null);
            return ResponseEntity.status(404).body(response);
        }
        mesaExistente.setNumero(mesaDto.getNumeroMesa());
        mesaExistente.setCapacidad(mesaDto.getCapacidad());
        mesaRepository.save(mesaExistente);
        ApiResponse<MesaResponse> response = new ApiResponse<>(200, "Mesa actualizada exitosamente",
                MesaResponse.fromEntity(mesaExistente));
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
