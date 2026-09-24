package com.linde.linde_backend.services.cliente;

import java.util.List;

import org.springframework.stereotype.Service;

import com.linde.linde_backend.dto.cliente.ClienteRequest;
import com.linde.linde_backend.dto.cliente.ClienteResponse;
import com.linde.linde_backend.entities.cliente.Cliente;
import com.linde.linde_backend.entities.usuario.Usuario;
import com.linde.linde_backend.mapper.cliente.ClienteMapper;
import com.linde.linde_backend.repositories.cliente.ClienteRepository;
import com.linde.linde_backend.repositories.usuario.UsuarioRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class ClienteService {

    private final ClienteRepository clienteRepository;
    private final UsuarioRepository usuarioRepository;
    private final ClienteMapper clienteMapper;

    public List<Cliente> listarClientes() {
        return clienteRepository.findAll();
    }

    public Cliente buscarCliente(Integer id) {
        return clienteRepository.findById(id)
                .orElse(null);
    }

    public ClienteResponse crearCliente(ClienteRequest request) {

        Usuario usuario = usuarioRepository.findById(request.idUsuario())
                .orElseThrow(() ->
                        new RuntimeException("Usuario no encontrado"));

        Cliente cliente = clienteMapper.toEntity(
                request,
                usuario
        );

        Cliente guardado = clienteRepository.save(cliente);

        return clienteMapper.toDto(guardado);
    }

    public void eliminarCliente(Integer id) {
        clienteRepository.deleteById(id);
    }


    public ClienteResponse actualizarCliente(
            Integer id,
            ClienteRequest request) {

        Cliente cliente = clienteRepository.findById(id)
                .orElseThrow(() ->
                        new RuntimeException("Cliente no encontrado"));

        Usuario usuario = usuarioRepository.findById(request.idUsuario())
                .orElseThrow(() ->
                        new RuntimeException("Usuario no encontrado"));

        cliente.setUsuario(usuario);
        cliente.setRuc(request.ruc());
        cliente.setRazonSocial(request.rsocial());
        cliente.setDireccion(request.direccion());
        cliente.setTelefono(request.telefono());
        cliente.setCorreo(request.correo());

        Cliente actualizado = clienteRepository.save(cliente);

        return clienteMapper.toDto(actualizado);
    }


}
