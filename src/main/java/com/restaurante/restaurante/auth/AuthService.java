package com.restaurante.restaurante.auth;

import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.restaurante.restaurante.auth.dto.LoginDto;
import com.restaurante.restaurante.auth.dto.UsuarioDto;
import com.restaurante.restaurante.auth.entity.Usuario;
import com.restaurante.restaurante.auth.repository.UsuarioRepository;
import com.restaurante.restaurante.auth.response.LoginResponse;
import com.restaurante.restaurante.jwt.JwtService;
import com.restaurante.restaurante.utils.ApiResponse;

@Service
public class AuthService {

    @Autowired
    private UsuarioRepository usuarioRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    private JwtService jwtService;

    public ResponseEntity<ApiResponse<LoginResponse>> login(LoginDto login) {
        try {
            authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(login.getUsername(), login.getPassword()));

            var user = usuarioRepository.findByUsername(login.getUsername()).orElseThrow();

            if (!user.getEstado()) {
                return ResponseEntity.status(403)
                        .body(new ApiResponse<>(403, "La cuenta está inactiva", null));
            }

            Map<String, Object> claims = new HashMap<>();
            claims.put("username", user.getUsername());
            claims.put("userId", user.getUsuarioId());

            String token = jwtService.generateToken(claims, user);

            LoginResponse loginResponse = new LoginResponse(user, token);

            return ResponseEntity.ok(new ApiResponse<>(200, "Login successful", loginResponse));
        } catch (Exception e) {
            return ResponseEntity.status(401)
                    .body(new ApiResponse<>(401, "Usuario o contraseña inválidos", null));
        }
    }

    public ResponseEntity<ApiResponse<Usuario>> register(UsuarioDto usuarioDto) {

        if (usuarioRepository.existsByUsername(usuarioDto.getUsername())) {
            return ResponseEntity.status(400)
                    .body(new ApiResponse<>(400, "Username already exists", null));
        }

        Usuario newUsuario = usuarioDto.toEntity();
        newUsuario.setPassword(passwordEncoder.encode(usuarioDto.getPassword()));

        usuarioRepository.save(newUsuario);

        return ResponseEntity.status(201)
                .body(new ApiResponse<>(201, "User registered successfully", newUsuario));
    }

}
