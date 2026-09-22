package com.linde.linde_backend.services.trabajador;

import java.util.List;

import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.linde.linde_backend.dto.trabajador.TrabajadorRequest;
import com.linde.linde_backend.dto.trabajador.TrabajadorResponse;
import com.linde.linde_backend.entities.trabajador.Trabajador;
import com.linde.linde_backend.entities.usuario.Usuario;
import com.linde.linde_backend.mapper.trabajador.TrabajadorMapper;
import com.linde.linde_backend.repositories.trabajador.TrabajadorRepository;
import com.linde.linde_backend.repositories.usuario.UsuarioRepository;
import com.linde.linde_backend.utils.Estado;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class TrabajadorService {

    private final TrabajadorRepository trabajadorRepository;
    private final UsuarioRepository usuarioRepository;
    private final TrabajadorMapper trabajadorMapper;
    private final PasswordEncoder passwordEncoder;

    public List<TrabajadorResponse> listar() {
        return trabajadorRepository.findAll()
                .stream()
                .map(trabajadorMapper::toResponse)
                .toList();
    }

    public TrabajadorResponse buscarPorId(Integer id) {

        Trabajador trabajador = trabajadorRepository.findById(id)
                .orElseThrow(() -> new RuntimeException(
                        "Trabajador no encontrado"
                ));

        return trabajadorMapper.toResponse(trabajador);
    }

    @Transactional
    public TrabajadorResponse insertar(TrabajadorRequest request) {

        validarRol(request);

        if (usuarioRepository.findByCorreo(request.correo()).isPresent()) {
            throw new RuntimeException("El correo ya está registrado");
        }

        Usuario usuario = Usuario.builder()
                .correo(request.correo())
                .contraseña(passwordEncoder.encode(request.contraseña()))
                .estado(request.estado())
                .rol(request.rol())
                .build();

        usuario = usuarioRepository.save(usuario);

        Trabajador trabajador = trabajadorMapper.toEntity(
                request,
                usuario
        );

        trabajador = trabajadorRepository.save(trabajador);

        return trabajadorMapper.toResponse(trabajador);
    }

    @Transactional
    public TrabajadorResponse actualizar(Integer id,TrabajadorRequest request) {

        validarRol(request);

        Trabajador trabajador = trabajadorRepository.findById(id)
                .orElseThrow(() -> new RuntimeException(
                        "Trabajador no encontrado"
                ));

        Usuario usuario = trabajador.getUsuario();

        if (!usuario.getCorreo().equals(request.correo())) {

            if (usuarioRepository.findByCorreo(request.correo()).isPresent()) {
                throw new RuntimeException("El correo ya está registrado");
            }
        }

        trabajador.setNombres(request.nombres());
        trabajador.setApellidos(request.apellidos());
        trabajador.setDni(request.dni());
        trabajador.setTelefono(request.telefono());
        trabajador.setDireccion(request.direccion());
        trabajador.setFechaIngreso(request.fechaIngreso());
        trabajador.setEstado(request.estado());

        usuario.setCorreo(request.correo());
        usuario.setEstado(request.estado());
        usuario.setRol(request.rol());

        if (request.contraseña() != null
                && !request.contraseña().isBlank()) {

            usuario.setContraseña(
                    passwordEncoder.encode(request.contraseña())
            );
        }

        usuarioRepository.save(usuario);
        trabajadorRepository.save(trabajador);

        return trabajadorMapper.toResponse(trabajador);
    }

    @Transactional
    public void eliminar(Integer id) {

        Trabajador trabajador = trabajadorRepository.findById(id)
            .orElseThrow(() -> new RuntimeException(
                    "Trabajador no encontrado"
            ));

            Usuario usuario = trabajador.getUsuario();

            trabajador.setEstado(Estado.INACTIVO);
            usuario.setEstado(Estado.INACTIVO);

            trabajadorRepository.save(trabajador);
            usuarioRepository.save(usuario);
    }

    private void validarRol(TrabajadorRequest request) {

        switch (request.rol()) {

            case ADMIN:
            case CLIENTE:
                throw new RuntimeException(
                        "El rol seleccionado no corresponde a un trabajador"
                );

            case CONDUCTOR:
            case TECNICO:
            case PROGRAMADOR:
            case ANALISTA:
                break;
        }
    }
}