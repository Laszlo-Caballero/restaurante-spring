package com.restaurante.restaurante.auth.response;

import java.sql.Timestamp;
import java.util.List;

import com.restaurante.restaurante.auth.entity.Usuario;
import com.restaurante.restaurante.auth.enums.RoleEnum;
import com.restaurante.restaurante.pedido.response.PedidoComidaRaw;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Data
@Builder
public class UsuarioResponse {
    private Long usuarioId;
    private String nombre;
    private String username;
    private Boolean estado;
    private RoleEnum role;
    private Timestamp lastLogin;
    public List<PedidoComidaRaw> pedidos;

    public static UsuarioResponse fromEntity(Usuario usuario) {
        var usuarioResponse = UsuarioResponse.builder()
                .usuarioId(usuario.getUsuarioId())
                .nombre(usuario.getNombre())
                .username(usuario.getUsername())
                .estado(usuario.getEstado())
                .lastLogin(usuario.getLastLogin())
                .role(usuario.getRole())
                .pedidos(PedidoComidaRaw.toResponse(usuario.getPedidos()))
                .build();
        return usuarioResponse;
    }

    public static List<UsuarioResponse> toResponses(List<Usuario> usuarios) {
        return usuarios.stream().map(UsuarioResponse::fromEntity).toList();
    }
}
