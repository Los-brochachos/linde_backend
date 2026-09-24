package com.linde.linde_backend.dto.cisterna;

import java.math.BigDecimal;

import jakarta.validation.constraints.Digits;
import jakarta.validation.constraints.Positive;

public record EditarCisternaRequest(
    String nombreBuscar, 
    String placa,       
    String nombre,      
    @Positive @Digits(integer = 5, fraction = 2, message = "Capacidad inválida") BigDecimal capacidad
) {

}
