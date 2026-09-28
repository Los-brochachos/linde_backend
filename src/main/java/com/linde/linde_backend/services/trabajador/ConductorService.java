package com.linde.linde_backend.services.trabajador;

import java.util.List;
import java.util.NoSuchElementException;

import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.linde.linde_backend.dto.trabajador.conductor.ConductorCreateRequest;
import com.linde.linde_backend.dto.trabajador.conductor.ConductorResponse;
import com.linde.linde_backend.dto.trabajador.conductor.ConductorUpdateRequest;
import com.linde.linde_backend.entities.trabajador.Conductor;
import com.linde.linde_backend.entities.trabajador.Trabajador;
import com.linde.linde_backend.entities.usuario.Usuario;
import com.linde.linde_backend.mappers.trabajador.ConductorMapper;
import com.linde.linde_backend.repositories.trabajador.ConductorRepository;
import com.linde.linde_backend.repositories.trabajador.TrabajadorRepository;
import com.linde.linde_backend.repositories.usuario.UsuarioRepository;
import com.linde.linde_backend.utils.Estado;
import com.linde.linde_backend.utils.RolesEnum;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class ConductorService {

    private final ConductorRepository conductorRepository;
    private final TrabajadorRepository trabajadorRepository;
    private final UsuarioRepository usuarioRepository;
    private final ConductorMapper conductorMapper;
    private final PasswordEncoder passwordEncoder;


    // =========================
    // LISTAR TODOS
    // =========================

    public List<ConductorResponse> listarTodos() {

        return conductorRepository.findAll()
                .stream()
                .map(conductorMapper::toResponse)
                .toList();
    }


    // =========================
    // LISTAR ACTIVOS
    // =========================

    public List<ConductorResponse> listarActivos() {

        return conductorRepository
                .findByTrabajadorEstado(Estado.ACTIVO)
                .stream()
                .map(conductorMapper::toResponse)
                .toList();
    }


    // =========================
    // BUSCAR POR ID
    // =========================

    public ConductorResponse buscarPorId(Integer id) {

        Conductor conductor = conductorRepository.findById(id)
                .orElseThrow(() ->
                        new NoSuchElementException(
                                "Conductor no encontrado"
                        )
                );

        return conductorMapper.toResponse(conductor);
    }


    // =========================
    // BUSCAR ACTIVO POR ID
    // =========================

    public ConductorResponse buscarActivoPorId(Integer id) {

        Conductor conductor = conductorRepository
                .findByIdTrabajadorAndTrabajadorEstado(
                        id,
                        Estado.ACTIVO
                )
                .orElseThrow(() ->
                        new NoSuchElementException(
                                "Conductor no encontrado"
                        )
                );

        return conductorMapper.toResponse(conductor);
    }


    // =========================
    // CREAR CONDUCTOR
    // =========================

    @Transactional
    public ConductorResponse crearConductor(
            ConductorCreateRequest request) {

        // 1. Verificar correo
        if (usuarioRepository
                .findByCorreo(request.correo())
                .isPresent()) {

            throw new IllegalArgumentException(
                    "El correo ya está registrado"
            );
        }

        // 2. Verificar DNI
        if (trabajadorRepository
                .findByDni(request.dni())
                .isPresent()) {

            throw new IllegalArgumentException(
                    "El DNI ya está registrado"
            );
        }

        // 3. Verificar licencia
        if (conductorRepository
                .findByLicenciaConducir(
                        request.licenciaConducir()
                )
                .isPresent()) {

            throw new IllegalArgumentException(
                    "La licencia de conducir ya está registrada"
            );
        }


        // 4. Crear Usuario
        Usuario usuario = Usuario.builder()
                .correo(request.correo())
                .contraseña(
                        passwordEncoder.encode(
                                request.contraseña()
                        )
                )
                .estado(Estado.ACTIVO)
                .rol(RolesEnum.CONDUCTOR)
                .build();

        usuario = usuarioRepository.save(usuario);


        // 5. Crear Trabajador
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


        // 6. Crear Conductor
        Conductor conductor = conductorMapper.toEntity(
                request,
                trabajador
        );

        conductor = conductorRepository.save(conductor);


        // 7. Respuesta
        return conductorMapper.toResponse(conductor);
    }


    // =========================
    // ACTUALIZAR CONDUCTOR
    // =========================

    @Transactional
    public ConductorResponse actualizarConductor(
            Integer id,
            ConductorUpdateRequest request) {

        Conductor conductor = conductorRepository.findById(id)
                .orElseThrow(() ->
                        new NoSuchElementException(
                                "Conductor no encontrado"
                        )
                );

        Trabajador trabajador = conductor.getTrabajador();
        Usuario usuario = trabajador.getUsuario();


        // =========================
        // TRABAJADOR
        // =========================

        if (request.nombres() != null) {
            trabajador.setNombres(request.nombres());
        }

        if (request.apellidos() != null) {
            trabajador.setApellidos(request.apellidos());
        }

        if (request.dni() != null
                && !trabajador.getDni().equals(request.dni())) {

            if (trabajadorRepository
                    .findByDni(request.dni())
                    .isPresent()) {

                throw new IllegalArgumentException(
                        "El DNI ya está registrado"
                );
            }

            trabajador.setDni(request.dni());
        }

        if (request.telefono() != null) {
            trabajador.setTelefono(request.telefono());
        }

        if (request.direccion() != null) {
            trabajador.setDireccion(request.direccion());
        }

        if (request.fechaIngreso() != null) {
            trabajador.setFechaIngreso(request.fechaIngreso());
        }


        // =========================
        // USUARIO
        // =========================

        if (request.correo() != null
                && !usuario.getCorreo()
                        .equals(request.correo())) {

            if (usuarioRepository
                    .findByCorreo(request.correo())
                    .isPresent()) {

                throw new IllegalArgumentException(
                        "El correo ya está registrado"
                );
            }

            usuario.setCorreo(request.correo());
        }


        // =========================
        // CONDUCTOR
        // =========================

        if (request.licenciaConducir() != null
                && !conductor.getLicenciaConducir()
                        .equals(request.licenciaConducir())) {

            if (conductorRepository
                    .findByLicenciaConducir(
                            request.licenciaConducir()
                    )
                    .isPresent()) {

                throw new IllegalArgumentException(
                        "La licencia de conducir ya está registrada"
                );
            }

            conductor.setLicenciaConducir(
                    request.licenciaConducir()
            );
        }

        if (request.categoriaLicencia() != null) {
            conductor.setCategoriaLicencia(
                    request.categoriaLicencia()
            );
        }

        if (request.fechaVencimientoLicencia() != null) {
            conductor.setFechaVencimientoLicencia(
                    request.fechaVencimientoLicencia()
            );
        }


        // Guardar cambios
        usuarioRepository.save(usuario);
        trabajadorRepository.save(trabajador);
        conductorRepository.save(conductor);


        return conductorMapper.toResponse(conductor);
    }


    // =========================
    // DESACTIVAR CONDUCTOR
    // =========================

    @Transactional
    public void eliminarConductor(Integer id) {

        Conductor conductor = conductorRepository.findById(id)
                .orElseThrow(() ->
                        new NoSuchElementException(
                                "Conductor no encontrado"
                        )
                );

        Trabajador trabajador = conductor.getTrabajador();
        Usuario usuario = trabajador.getUsuario();

        trabajador.setEstado(Estado.INACTIVO);

        trabajadorRepository.save(trabajador);
        usuarioRepository.save(usuario);
    }
}
