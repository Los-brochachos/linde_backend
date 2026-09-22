package com.linde.linde_backend.mapper.cliente;

import org.springframework.stereotype.Component;

import com.linde.linde_backend.dto.cliente.ClienteRequest;
import com.linde.linde_backend.dto.cliente.ClienteResponse;
import com.linde.linde_backend.entities.cliente.Cliente;

@Component 
public class ClienteMapper {
    public Cliente toEntity (ClienteRequest dto){
        return Cliente.builder()
        .ruc(dto.ruc())
        .razonSocial(dto.rsocial())
        .direccion(dto.direccion())
        .telefono(dto.telefono())
        .correo(dto.correo())
        .build();
    }
    public ClienteResponse toDto(Cliente entitty){
        return new ClienteResponse(
            entitty.getIdCliente(),
            entitty.getRuc(),
            entitty.getRazonSocial(),
            entitty.getDireccion(),
            entitty.getTelefono(),
            entitty.getCorreo());
    }
}
