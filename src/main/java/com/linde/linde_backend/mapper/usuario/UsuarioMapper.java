package com.linde.linde_backend.mapper.usuario;

import org.springframework.stereotype.Component;

import com.linde.linde_backend.dto.usuario.UsuarioResponse;
import com.linde.linde_backend.entities.usuario.Usuario;

@Component
public class UsuarioMapper {

    public UsuarioResponse toResponse(Usuario usuario) {

        return new UsuarioResponse(
                usuario.getIdUsuario(),
                usuario.getCorreo(),
                usuario.getEstado(),
                usuario.getRol()
        );
    }
}