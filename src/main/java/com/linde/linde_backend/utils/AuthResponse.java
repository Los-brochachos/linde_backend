package com.linde.linde_backend.utils;

import com.fasterxml.jackson.annotation.JsonProperty;


public record AuthResponse(
    @JsonProperty("access_token") String accessToken,
    @JsonProperty("refresh_token") String refreshToken
){
    
}