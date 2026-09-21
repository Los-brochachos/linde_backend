package com.linde.linde_backend.controllers.usuario;

import java.util.List;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.linde.linde_backend.entities.usuario.Usuario;
import com.linde.linde_backend.services.UsuarioService;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;


@RestController 
@RequestMapping("api/v1/usuarios")
@RequiredArgsConstructor 
public class UsuarioRestController {


    private final UsuarioService service;


    @GetMapping 
    public List<Usuario> findAll(){
        return service.findAll();
    }

    @GetMapping ("/{id}")
    public Usuario findById(Integer id){
        return service.findById(id);
    }

    @PostMapping("path")
    public String postMethodName(@RequestBody String entity) {
        //TODO: process POST request
        
        return entity;
    }
    
}
