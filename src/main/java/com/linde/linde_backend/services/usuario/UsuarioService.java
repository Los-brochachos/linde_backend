package com.linde.linde_backend.services.usuario;

import java.util.List;

import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.linde.linde_backend.dto.usuario.UsuarioResponse;
import com.linde.linde_backend.dto.usuario.UsuarioUpdateRequest;
import com.linde.linde_backend.entities.usuario.Usuario;
import com.linde.linde_backend.mapper.usuario.UsuarioMapper;
import com.linde.linde_backend.repositories.usuario.UsuarioRepository;
import com.linde.linde_backend.utils.Estado;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class UsuarioService implements UserDetailsService {

    private final UsuarioRepository repository;
    private final UsuarioMapper mapper;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {

        return repository.findByCorreo(username)
                .orElseThrow(() -> new UsernameNotFoundException("User not found"));
    }

    public List<UsuarioResponse> findAll() {

        return repository.findAll()
                .stream()
                .map(mapper::toResponse)
                .toList();
    }

    public UsuarioResponse findById(Integer id) {

        Usuario usuario = repository.findById(id)
                .orElseThrow(() -> new RuntimeException("Usuario no encontrado"));

        return mapper.toResponse(usuario);
    }

    @Transactional
    public UsuarioResponse actualizar(Integer id, UsuarioUpdateRequest request) {

        Usuario usuario = repository.findById(id)
                .orElseThrow(() -> new RuntimeException("Usuario no encontrado"));

        if (!usuario.getCorreo().equals(request.correo())
                && repository.findByCorreo(request.correo()).isPresent()) {

            throw new RuntimeException("El correo ya está registrado");
        }

        usuario.setCorreo(request.correo());
        usuario.setEstado(request.estado());

        usuario = repository.save(usuario);

        return mapper.toResponse(usuario);
    }

    @Transactional
    public void desactivar(Integer id) {

        Usuario usuario = repository.findById(id)
                .orElseThrow(() -> new RuntimeException("Usuario no encontrado"));

        usuario.setEstado(Estado.INACTIVO);

        repository.save(usuario);
    }

    public UsuarioResponse me(String correo) {

        Usuario usuario = repository.findByCorreo(correo).orElseThrow(() -> new RuntimeException("Usuario no encontrado"));
        return mapper.toResponse(usuario);

    }
}