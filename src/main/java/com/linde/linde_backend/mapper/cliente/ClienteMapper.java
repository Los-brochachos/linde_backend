package com.linde.linde_backend.mapper.cliente;

import org.springframework.stereotype.Component;

import com.linde.linde_backend.dto.cliente.ClienteCreateRequest;
import com.linde.linde_backend.dto.cliente.ClienteResponse;
import com.linde.linde_backend.entities.cliente.Cliente;
import com.linde.linde_backend.entities.usuario.Usuario;

@Component
public class ClienteMapper {

    public Cliente toEntity(ClienteCreateRequest dto, Usuario usuario) {

        return Cliente.builder()
                .usuario(usuario)
                .ruc(dto.ruc())
                .razonSocial(dto.razonSocial())
                .direccion(dto.direccion())
                .telefono(dto.telefono())
                .build();
    }

    public ClienteResponse toDto(Cliente entity) {

        return new ClienteResponse(
                entity.getIdCliente(),
                entity.getRuc(),
                entity.getRazonSocial(),
                entity.getDireccion(),
                entity.getTelefono(),
                entity.getUsuario().getCorreo(),
                entity.getUsuario().getRol(),
                entity.getFechaCreacion(),
                entity.getFechaActualizacion()
        );
    }
}