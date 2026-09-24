package com.linde.linde_backend.mapper.cliente;

import org.springframework.stereotype.Component;

import com.linde.linde_backend.dto.cliente.ClienteRequest;
import com.linde.linde_backend.dto.cliente.ClienteResponse;
import com.linde.linde_backend.entities.cliente.Cliente;
import com.linde.linde_backend.entities.usuario.Usuario;

@Component
public class ClienteMapper {

    public Cliente toEntity(ClienteRequest dto, Usuario usuario) {

        return Cliente.builder()
                .usuario(usuario)
                .ruc(dto.ruc())
                .razonSocial(dto.rsocial())
                .direccion(dto.direccion())
                .telefono(dto.telefono())
                .correo(dto.correo())
                .build();
    }

    public ClienteResponse toDto(Cliente entity) {

        return new ClienteResponse(
                entity.getIdCliente(),
                entity.getRuc(),
                entity.getRazonSocial(),
                entity.getDireccion(),
                entity.getTelefono(),
                entity.getCorreo()
        );
    }
}
