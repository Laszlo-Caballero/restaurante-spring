package com.restaurante.restaurante.usuarios;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.restaurante.restaurante.auth.dto.UsuarioDto;
import com.restaurante.restaurante.auth.entity.Usuario;
import com.restaurante.restaurante.auth.repository.UsuarioRepository;
import com.restaurante.restaurante.auth.response.UsuarioRaw;
import com.restaurante.restaurante.auth.response.UsuarioResponse;
import com.restaurante.restaurante.utils.ApiResponse;

@Service
public class UsuarioService {

    @Autowired
    private UsuarioRepository usuarioRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    public ResponseEntity<ApiResponse<UsuarioResponse>> obtenerPerfil() {

        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

        Usuario usuario = (Usuario) authentication.getPrincipal();

        UsuarioResponse usuarioResponse = UsuarioResponse.fromEntity(usuario);

        return ResponseEntity.ok(new ApiResponse<>(200, "Perfil obtenido con éxito", usuarioResponse));
    }

    public ResponseEntity<ApiResponse<List<UsuarioResponse>>> listarUsuarios() {
        Sort sortById = Sort.by("usuarioId").ascending();

        List<Usuario> usuarios = usuarioRepository.findAll(sortById);
        List<UsuarioResponse> usuarioResponses = UsuarioResponse.toResponses(usuarios);
        return ResponseEntity.ok(new ApiResponse<>(200, "Lista de usuarios obtenida con éxito", usuarioResponses));
    }

    public ResponseEntity<?> eliminarUsuario(Long id) {
        var findUser = usuarioRepository.findById(id).orElse(null);
        if (findUser == null) {
            return ResponseEntity.status(404).body(new ApiResponse<>(404, "Usuario no encontrado", null));
        }

        findUser.setEstado(false);
        usuarioRepository.save(findUser);
        return ResponseEntity.ok(new ApiResponse<>(200, "Usuario eliminado (estado inactivo) con éxito", null));
    }

    public ResponseEntity<?> actualizarUsuario(UsuarioDto usuarioDto, Long id) {
        var findUser = usuarioRepository.findById(id).orElse(null);
        if (findUser == null) {
            return ResponseEntity.status(404).body(new ApiResponse<>(404, "Usuario no encontrado", null));
        }

        if (usuarioRepository.existsByUsername(usuarioDto.getUsername())) {
            return ResponseEntity.status(400)
                    .body(new ApiResponse<>(400, "El nombre de usuario ya existe", null));
        }

        findUser.setNombre(usuarioDto.getNombre());
        findUser.setUsername(usuarioDto.getUsername());
        findUser.setPassword(passwordEncoder.encode(usuarioDto.getPassword()));

        findUser.setRole(usuarioDto.getRole());

        usuarioRepository.save(findUser);
        return ResponseEntity.ok(new ApiResponse<>(200, "Usuario actualizado con éxito", null));
    }

    public ResponseEntity<?> getUsuarioById(Long id) {
        var findUser = usuarioRepository.findById(id).orElse(null);
        if (findUser == null) {
            return ResponseEntity.status(404).body(new ApiResponse<>(404, "Usuario no encontrado", null));
        }

        UsuarioRaw usuarioResponse = UsuarioRaw.fromEntity(findUser);
        return ResponseEntity.ok(new ApiResponse<>(200, "Usuario obtenido con éxito", usuarioResponse));
    }

}
