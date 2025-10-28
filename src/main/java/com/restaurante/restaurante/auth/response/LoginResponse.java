package com.restaurante.restaurante.auth.response;

import com.restaurante.restaurante.auth.entity.Usuario;

public class LoginResponse extends UsuarioRaw {
    private String token;

    public LoginResponse(Usuario usuario, String token) {
        super.setUsuarioId(usuario.getUsuarioId());
        super.setNombre(usuario.getNombre());
        super.setUsername(usuario.getUsername());
        super.setRole(usuario.getRole());
        this.token = token;
    }

    public String getToken() {
        return token;
    }
}
