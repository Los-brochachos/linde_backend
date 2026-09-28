package com.linde.linde_backend.services.cliente;

import java.util.List;
import java.util.NoSuchElementException;

import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.linde.linde_backend.dto.cliente.ClienteCreateRequest;
import com.linde.linde_backend.dto.cliente.ClienteResponse;
import com.linde.linde_backend.dto.cliente.ClienteUpdateRequest;
import com.linde.linde_backend.entities.cliente.Cliente;
import com.linde.linde_backend.entities.usuario.Usuario;
import com.linde.linde_backend.mappers.cliente.ClienteMapper;
import com.linde.linde_backend.repositories.cliente.ClienteRepository;
import com.linde.linde_backend.repositories.usuario.UsuarioRepository;
import com.linde.linde_backend.utils.Estado;
import com.linde.linde_backend.utils.RolesEnum;
import com.linde.linde_backend.utils.auth.RegisterResponse;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class ClienteService {

    private final ClienteRepository clienteRepository;
    private final UsuarioRepository usuarioRepository;
    private final ClienteMapper clienteMapper;
    private final PasswordEncoder passwordEncoder;

    public List<ClienteResponse> listarClientesTodos() {
        return clienteRepository.findAll()
                .stream()
                .map(clienteMapper::toDto)
                .toList();
    }

    public List<ClienteResponse> listarActivos() {
        return clienteRepository.findByUsuarioEstado(Estado.ACTIVO)
                .stream()
                .map(clienteMapper::toDto)
                .toList();
    }

    public ClienteResponse buscarPorId(Integer id) {

        Cliente cliente = clienteRepository.findById(id)
                .orElseThrow(() ->
                        new NoSuchElementException("Cliente no encontrado"));

        return clienteMapper.toDto(cliente);
    }

    public ClienteResponse buscarActivoPorId(Integer id) {

        Cliente cliente = clienteRepository
                .findByIdClienteAndUsuarioEstado(
                        id,
                        Estado.ACTIVO
                ).orElseThrow(() ->new NoSuchElementException("Cliente no encontrado"));

        return clienteMapper.toDto(cliente);
    }

    @Transactional
    public RegisterResponse crearCliente(ClienteCreateRequest request) {

        // 1. Verificar que el correo no esté registrado
        if (usuarioRepository.findByCorreo(request.correo()).isPresent()) {
            throw new RuntimeException(
                    "El correo ya está registrado");
        }

        // 2. Crear Usuario
        Usuario usuario = Usuario.builder()
                .correo(request.correo())
                .contraseña(
                        passwordEncoder.encode(request.contraseña())
                )
                .estado(Estado.ACTIVO)
                .rol(RolesEnum.CLIENTE)
                .build();

        usuario = usuarioRepository.save(usuario);

        // 3. Crear Cliente asociado al Usuario
        Cliente cliente = clienteMapper.toEntity(
                request,
                usuario
        );

        cliente = clienteRepository.save(cliente);


        // 4. Devolver respuesta
        return new RegisterResponse(clienteMapper.toDto(cliente), "INFORMACIÓN", "Nuevo usuario registrado.");
    }

    @Transactional
    public ClienteResponse actualizarCliente(
            Integer id,
            ClienteUpdateRequest request) {

        // 1. Buscar Cliente
        Cliente cliente = clienteRepository.findById(id)
                        .orElseThrow(() -> new NoSuchElementException("Cliente no encontrado"));
        
        Usuario usuario = cliente.getUsuario();

        // 2. Actualizar RUC
        if (request.ruc() != null) {
            cliente.setRuc(request.ruc());
        }

        // 3. Actualizar Razón Social
        if (request.razonSocial() != null) {
            cliente.setRazonSocial(request.razonSocial());
        }

        // 4. Actualizar Dirección
        if (request.direccion() != null) {
            cliente.setDireccion(request.direccion());
        }

        // 5. Actualizar Teléfono
        if (request.telefono() != null) {
            cliente.setTelefono(request.telefono());
        }

        // 6. Actualizar Correo
        if (request.correo() != null
                && !usuario.getCorreo().equals(request.correo())) {

            if (usuarioRepository
                    .findByCorreo(request.correo())
                    .isPresent()) {

                throw new IllegalArgumentException(
                        "El correo ya está registrado");
            }

            usuario.setCorreo(request.correo());
        }

        // 7. Actualizar contraseña solo si fue enviada
        if (request.contraseña() != null
                && !request.contraseña().isBlank()) {

            usuario.setContraseña(
                    passwordEncoder.encode(
                            request.contraseña()
                    )
            );
        }

        // 8. Guardar cambios
        usuarioRepository.save(usuario);
        clienteRepository.save(cliente);

        // 9. Devolver respuesta
        return clienteMapper.toDto(cliente);
    }

    @Transactional
    public void eliminarCliente(Integer id) {

        Cliente cliente = clienteRepository.findById(id)
                .orElseThrow(() ->
                        new NoSuchElementException(
                                "Cliente no encontrado"));

        Usuario usuario = cliente.getUsuario();

        // Desactivación lógica
        usuario.setEstado(Estado.INACTIVO);
        usuarioRepository.save(usuario);
    }
}