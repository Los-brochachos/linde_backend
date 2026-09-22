package com.linde.linde_backend.dto.usuario;

import com.linde.linde_backend.utils.EstadoUsuario;
import com.linde.linde_backend.utils.RolesEnum;

public record UsuarioResponse(
    Integer idUsuario,
    String correo,
    EstadoUsuario estado,
    RolesEnum rol
) {}