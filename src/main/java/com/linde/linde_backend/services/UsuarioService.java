package com.linde.linde_backend.services;

import java.util.List;

import org.springframework.stereotype.Service;

import com.linde.linde_backend.entities.Usuario.Usuario;
import com.linde.linde_backend.repositories.UsuarioRepository;

import lombok.RequiredArgsConstructor;

@Service 
@RequiredArgsConstructor 
public class UsuarioService {

    private final UsuarioRepository repository;

    
    public List<Usuario> findAll(){
        return repository.findAll();
    }



}
