package com.linde.linde_backend.utils.auth;

import jakarta.validation.constraints.NotBlank;

public record RefreshTokenRequest(

    @NotBlank(message = "El refresh token es obligatorio")
    String refreshToken

) {
}