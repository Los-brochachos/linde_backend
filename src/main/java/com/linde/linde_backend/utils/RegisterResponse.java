package com.linde.linde_backend.utils;

import com.linde.linde_backend.dto.cliente.ClienteResponse;

public record RegisterResponse(
    ClienteResponse cliente,
    String status,
    String message
) {

}
