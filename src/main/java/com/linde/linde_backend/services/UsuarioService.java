package com.linde.linde_backend.services;

import java.util.List;

import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.linde.linde_backend.entities.usuario.Usuario;
import com.linde.linde_backend.repositories.UsuarioRepository;

import lombok.RequiredArgsConstructor;

@Service 
@RequiredArgsConstructor 
public class UsuarioService implements UserDetailsService {

    private final UsuarioRepository repository;
    
    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        return repository.findByCorreo(username)
            .orElseThrow(() -> new UsernameNotFoundException("User not found"));
    }
    
    public List<Usuario> findAll(){
        return repository.findAll();
    }

    public Usuario findById(Integer id){
        return repository.findById(id).orElse(null);

    }



}
