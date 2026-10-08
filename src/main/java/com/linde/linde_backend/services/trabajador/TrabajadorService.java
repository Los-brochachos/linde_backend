package com.linde.linde_backend.services.trabajador;

import java.util.List;
import java.util.NoSuchElementException;

import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.linde.linde_backend.dto.trabajador.TrabajadorCreateRequest;
import com.linde.linde_backend.dto.trabajador.TrabajadorResponse;
import com.linde.linde_backend.entities.trabajador.Trabajador;
import com.linde.linde_backend.entities.usuario.Usuario;
import com.linde.linde_backend.mappers.trabajador.TrabajadorMapper;
import com.linde.linde_backend.repositories.trabajador.TrabajadorRepository;
import com.linde.linde_backend.repositories.usuario.UsuarioRepository;
import com.linde.linde_backend.utils.Estado;
import com.linde.linde_backend.utils.RolesEnum;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class TrabajadorService {

    private final TrabajadorRepository trabajadorRepository;
    private final TrabajadorMapper trabajadorMapper;
    private final UsuarioRepository usuarioRepository;
    private final PasswordEncoder passwordEncoder;

    public List<TrabajadorResponse> listarTodos() {

        return trabajadorRepository
                .findAll()
                .stream()
                .map(trabajadorMapper::toResponse)
                .toList();
    }

    public List<TrabajadorResponse> listarActivos() {

        return trabajadorRepository
                .findByEstado(Estado.ACTIVO)
                .stream()
                .map(trabajadorMapper::toResponse)
                .toList();
    }

    public TrabajadorResponse buscarPorId(Integer id) {

        Trabajador trabajador = trabajadorRepository
                .findById(id)
                .orElseThrow(() ->
                        new NoSuchElementException(
                                "Trabajador no encontrado"
                        ));

        return trabajadorMapper.toResponse(trabajador);
    }


    @Transactional 
    public TrabajadorResponse crear(TrabajadorCreateRequest request) {

    if (request.rol() != RolesEnum.ANALISTA
            && request.rol() != RolesEnum.ADMIN) {
        throw new IllegalArgumentException(
                "Solo se permite registrar ADMIN o ANALISTA"
        );
    }

    usuarioRepository.findByCorreo(request.correo())
                .orElseThrow(()-> new IllegalArgumentException("El correo ya está registrado"));
        
    

    trabajadorRepository.findByDni(request.dni()).orElseThrow(()->new IllegalArgumentException(
                "El DNI ya está registrado"
        ));
    

    Usuario usuario = Usuario.builder()
            .correo(request.correo())
            .contraseña(passwordEncoder.encode(request.contraseña()))
            .rol(request.rol())
            .estado(Estado.ACTIVO)
            .build();

    usuarioRepository.save(usuario);

    Trabajador trabajador = Trabajador.builder()
            .nombres(request.nombres())
            .apellidos(request.apellidos())
            .dni(request.dni())
            .telefono(request.telefono())
            .direccion(request.direccion())
            .fechaIngreso(request.fechaIngreso())
            .estado(Estado.ACTIVO)
            .usuario(usuario)
            .build();

    trabajador = trabajadorRepository.save(trabajador);

    return trabajadorMapper.toResponse(trabajador);
}
    public TrabajadorResponse buscarActivoPorId(Integer id) {

        Trabajador trabajador = trabajadorRepository
                .findByIdTrabajadorAndEstado(
                        id,
                        Estado.ACTIVO
                )
                .orElseThrow(() ->
                        new NoSuchElementException(
                                "Trabajador no encontrado"
                        ));

        return trabajadorMapper.toResponse(trabajador);
    }

    public TrabajadorResponse buscarPorDni(String dni) {

        Trabajador trabajador = trabajadorRepository
                .findByDni(dni)
                .orElseThrow(() ->
                        new NoSuchElementException(
                                "Trabajador no encontrado"
                        ));

        return trabajadorMapper.toResponse(trabajador);
    }

    public TrabajadorResponse buscarActivoPorDni(String dni) {

        Trabajador trabajador = trabajadorRepository
                .findByDniAndEstado(
                        dni,
                        Estado.ACTIVO
                )
                .orElseThrow(() ->
                        new NoSuchElementException(
                                "Trabajador no encontrado"
                        ));

        return trabajadorMapper.toResponse(trabajador);
    }
}