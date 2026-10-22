package com.linde.linde_backend.utils.auth;

import com.linde.linde_backend.dto.cliente.ClienteResponse;

public record RegisterResponse(
    ClienteResponse cliente,
    String status,
    String message
) {

}
