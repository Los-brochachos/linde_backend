package com.linde.linde_backend.dto.cisterna;

import jakarta.validation.constraints.NotNull;

public record EliminarFallaRequest(
    @NotNull Integer idFalla
) {
}
