package com.restaurante.restaurante.auth.dto;

import com.restaurante.restaurante.auth.entity.Usuario;
import com.restaurante.restaurante.auth.enums.RoleEnum;

import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class UsuarioDto {
    @NotNull(message = "El ID del usuario no puede ser nulo en la actualización")
    private String nombre;

    @NotNull(message = "El nombre de usuario no puede ser nulo en la actualización")
    private String username;

    @NotNull(message = "La contraseña no puede ser nula en la actualización")
    private String password;

    @NotNull
    private RoleEnum role;

    public Usuario toEntity() {
        Usuario usuario = new Usuario();
        usuario.setNombre(this.nombre);
        usuario.setUsername(this.username);
        usuario.setRole(this.role);
        return usuario;
    }
}
