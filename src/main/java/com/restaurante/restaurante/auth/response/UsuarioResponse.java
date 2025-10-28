package com.restaurante.restaurante.auth.response;

import java.util.List;

import com.restaurante.restaurante.auth.entity.Usuario;
import com.restaurante.restaurante.auth.enums.RoleEnum;
import com.restaurante.restaurante.pedido.response.PedidoComidaRaw;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class UsuarioResponse {
    private Long usuarioId;
    private String nombre;
    private String username;
    private RoleEnum role;
    public List<PedidoComidaRaw> pedidos;

    public static UsuarioResponse fromEntity(Usuario usuario) {
        var usuarioResponse = new UsuarioResponse(
                usuario.getUsuarioId(),
                usuario.getNombre(),
                usuario.getUsername(),
                usuario.getRole(),
                PedidoComidaRaw.toResponse(usuario.getPedidos()));
        return usuarioResponse;
    }

    public static List<UsuarioResponse> toResponses(List<Usuario> usuarios) {
        return usuarios.stream().map(UsuarioResponse::fromEntity).toList();
    }
}
