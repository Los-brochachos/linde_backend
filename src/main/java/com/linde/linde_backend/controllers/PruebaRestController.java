package com.linde.linde_backend.controllers;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/prueba")
public class PruebaRestController {

    @GetMapping
    public String prueba() {
        return "Acceso autorizado";
    }
}