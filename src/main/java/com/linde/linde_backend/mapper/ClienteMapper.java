package com.linde.linde_backend.mapper;

import org.springframework.stereotype.Component;

import com.linde.linde_backend.dto.ClienteRequeest;
import com.linde.linde_backend.dto.ClienteResponse;
import com.linde.linde_backend.entities.Cliente.cliente;

@Component 
public class ClienteMapper {
    public cliente toEntity (ClienteRequeest dto){
        return cliente.builder()
        .ruc(dto.ruc())
        .rsocial(dto.rsocial())
        .direccion(dto.direccion())
        .telefono(dto.telefono())
        .correo(dto.correo())
        .build();
    }
    public ClienteResponse toDto(cliente entitty){
        return new ClienteResponse(
            entitty.getId(),
            entitty.getRuc(),
            entitty.getRsocial(),
            entitty.getDireccion(),
            entitty.getTelefono(),
            entitty.getCorreo());
    }
}
