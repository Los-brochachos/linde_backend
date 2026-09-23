package com.linde.linde_backend.dto.usuario;

import com.linde.linde_backend.utils.Estado;
import com.linde.linde_backend.utils.RolesEnum;

public record UsuarioResponse(
    Integer idUsuario,
    String correo,
    Estado estado,
    RolesEnum rol
) {}