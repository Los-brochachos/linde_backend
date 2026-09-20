package com.linde.linde_backend.controllers;

import java.util.List;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.linde.linde_backend.entities.Usuario.Usuario;
import com.linde.linde_backend.services.UsuarioService;

import lombok.RequiredArgsConstructor;

@RestController 
@RequestMapping("api/v1/usuarios")
@RequiredArgsConstructor 
public class UsuarioRestController {


    private final UsuarioService service;


    @GetMapping 
    public List<Usuario> findAll(){
        return service.findAll();
    }
}
