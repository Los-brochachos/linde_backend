package com.linde.linde_backend.utils.auth;

/** Resultado interno: nunca se serializa directamente en un controlador. */
public record AuthTokens(String accessToken, String refreshToken) {}
