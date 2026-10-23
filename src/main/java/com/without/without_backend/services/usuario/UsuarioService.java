package com.without.without_backend.services.usuario;

import java.util.List;
import java.util.NoSuchElementException;

import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.without.without_backend.dto.usuario.UsuarioResponse;
import com.without.without_backend.dto.usuario.UsuarioUpdateRequest;
import com.without.without_backend.entities.usuario.Usuario;
import com.without.without_backend.mappers.usuario.UsuarioMapper;
import com.without.without_backend.repositories.usuario.UsuarioRepository;
import com.without.without_backend.utils.Estado;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class UsuarioService implements UserDetailsService {

    private final UsuarioRepository repository;
    private final UsuarioMapper mapper;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {

        return repository.findByCorreo(username)
                .orElseThrow(() -> new UsernameNotFoundException("Usuario no encontrado"));
    }

    public List<UsuarioResponse> findAll() {

        return repository.findAll()
                .stream()
                .map(mapper::toResponse)
                .toList();
    }

    public UsuarioResponse findById(Integer id) {

        Usuario usuario = repository.findById(id)
                .orElseThrow(() -> new NoSuchElementException("Usuario no encontrado"));

        return mapper.toResponse(usuario);
    }

    @Transactional
    public UsuarioResponse actualizar(Integer id, UsuarioUpdateRequest request) {

        Usuario usuario = repository.findById(id)
                .orElseThrow(() -> new NoSuchElementException("Usuario no encontrado"));

        if (!usuario.getCorreo().equals(request.correo())
                && repository.findByCorreo(request.correo()).isPresent()) {

            throw new IllegalArgumentException("El correo ya está registrado");
        }

        usuario.setCorreo(request.correo());
        usuario = repository.save(usuario);

        return mapper.toResponse(usuario);
    }

    @Transactional
    public UsuarioResponse actualizarMe(
            String correoActual,
            UsuarioUpdateRequest request) {

        Usuario usuario = repository.findByCorreo(correoActual)
                .orElseThrow(() ->
                        new NoSuchElementException("Usuario no encontrado"));

        if (!usuario.getCorreo().equals(request.correo())
                && repository.findByCorreo(request.correo()).isPresent()) {

            throw new IllegalArgumentException("El correo ya está registrado");
        }

        usuario.setCorreo(request.correo());

        return mapper.toResponse(usuario);
    }

    @Transactional
    public void desactivar(Integer id) {

        Usuario usuario = repository.findById(id)
                .orElseThrow(() -> new NoSuchElementException("Usuario no encontrado"));

        usuario.setEstado(Estado.INACTIVO);

        repository.save(usuario);
    }

    public UsuarioResponse me(String correo) {

        Usuario usuario = repository.findByCorreo(correo).orElseThrow(() -> new NoSuchElementException("Usuario no encontrado"));
        return mapper.toResponse(usuario);

    }
}