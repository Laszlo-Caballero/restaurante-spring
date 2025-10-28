package com.restaurante.restaurante.auth.response;

import java.util.List;

import com.restaurante.restaurante.auth.entity.Usuario;
import com.restaurante.restaurante.auth.enums.RoleEnum;

import lombok.Data;

@Data
public class UsuarioRaw {
    private Long usuarioId;
    private String nombre;
    private String username;
    private RoleEnum role;

    public static UsuarioRaw fromEntity(Usuario usuario) {
        var usuarioRaw = new UsuarioRaw();
        usuarioRaw.setUsuarioId(usuario.getUsuarioId());
        usuarioRaw.setNombre(usuario.getNombre());
        usuarioRaw.setUsername(usuario.getUsername());
        usuarioRaw.setRole(usuario.getRole());
        return usuarioRaw;
    }

    public static List<UsuarioRaw> toResponse(List<Usuario> usuarios) {
        return usuarios.stream().map(UsuarioRaw::fromEntity).toList();
    }

}
