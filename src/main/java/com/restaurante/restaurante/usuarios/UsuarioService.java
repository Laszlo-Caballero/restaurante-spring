package com.restaurante.restaurante.usuarios;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import com.restaurante.restaurante.auth.entity.Usuario;
import com.restaurante.restaurante.auth.repository.UsuarioRepository;
import com.restaurante.restaurante.auth.response.UsuarioResponse;
import com.restaurante.restaurante.utils.ApiResponse;

@Service
public class UsuarioService {

    @Autowired
    private UsuarioRepository usuarioRepository;

    public ResponseEntity<ApiResponse<UsuarioResponse>> obtenerPerfil() {

        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

        Usuario usuario = (Usuario) authentication.getPrincipal();

        UsuarioResponse usuarioResponse = UsuarioResponse.fromEntity(usuario);

        return ResponseEntity.ok(new ApiResponse<>(200, "Perfil obtenido con éxito", usuarioResponse));
    }

    public ResponseEntity<ApiResponse<List<UsuarioResponse>>> listarUsuarios() {
        List<Usuario> usuarios = usuarioRepository.findAll();
        List<UsuarioResponse> usuarioResponses = UsuarioResponse.toResponses(usuarios);
        return ResponseEntity.ok(new ApiResponse<>(200, "Lista de usuarios obtenida con éxito", usuarioResponses));
    }
}
