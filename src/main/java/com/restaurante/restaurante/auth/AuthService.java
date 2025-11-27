package com.restaurante.restaurante.auth;

import java.sql.Timestamp;
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
import com.restaurante.restaurante.auth.enums.RoleEnum;
import com.restaurante.restaurante.auth.repository.UsuarioRepository;
import com.restaurante.restaurante.auth.response.LoginResponse;
import com.restaurante.restaurante.jwt.JwtService;
import com.restaurante.restaurante.utils.ApiResponse;

import jakarta.annotation.PostConstruct;
import lombok.extern.slf4j.Slf4j;

@Service
@Slf4j
public class AuthService {

    @Autowired
    private UsuarioRepository usuarioRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    private JwtService jwtService;

    @PostConstruct
    private void initAdminUser() {
        if (usuarioRepository.count() == 0) {
            Usuario admin = new Usuario();
            admin.setUsername("admin");
            admin.setPassword(passwordEncoder.encode("admin"));
            admin.setNombre("Administrador");
            admin.setEstado(true);
            admin.setRole(RoleEnum.ADMIN);
            usuarioRepository.save(admin);
            log.info("Admin user created with username 'admin' and password 'admin'");
        }
        log.info("Admin user already exists");
    }

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

            user.setLastLogin(new Timestamp(System.currentTimeMillis()));

            usuarioRepository.save(user);

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
                    .body(new ApiResponse<>(400, "El nombre de usuario ya existe", null));
        }

        Usuario newUsuario = usuarioDto.toEntity();
        newUsuario.setPassword(passwordEncoder.encode(usuarioDto.getPassword()));

        usuarioRepository.save(newUsuario);

        return ResponseEntity.status(201)
                .body(new ApiResponse<>(201, "User registered successfully", newUsuario));
    }

}
