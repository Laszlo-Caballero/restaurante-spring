package com.restaurante.restaurante.usuarios;

import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import com.restaurante.restaurante.auth.entity.Usuario;
import com.restaurante.restaurante.utils.ApiResponse;

@Service
public class UsuarioService {
    public ResponseEntity<ApiResponse<Usuario>> obtenerPerfil() {

        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

        Usuario usuario = (Usuario) authentication.getPrincipal();

        return ResponseEntity.ok(new ApiResponse<>(200, "Perfil obtenido con éxito", usuario));
    }
}
