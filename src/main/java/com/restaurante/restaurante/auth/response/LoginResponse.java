package com.restaurante.restaurante.auth.response;

import com.restaurante.restaurante.auth.entity.Usuario;

public class LoginResponse extends Usuario {
    private String token;

    public LoginResponse(Usuario usuario, String token) {
        super.setUsuarioId(usuario.getUsuarioId());
        super.setNombre(usuario.getNombre());
        super.setUsername(usuario.getUsername());
        super.setPassword(usuario.getPassword());
        super.setRole(usuario.getRole());
        this.token = token;
    }

    public String getToken() {
        return token;
    }
}
