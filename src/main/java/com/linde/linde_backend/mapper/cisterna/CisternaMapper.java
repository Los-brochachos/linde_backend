package com.linde.linde_backend.mapper.cisterna;

import org.springframework.stereotype.Component;

import com.linde.linde_backend.dto.cisterna.CisternaRequest;
import com.linde.linde_backend.dto.cisterna.CisternaResponse;
import com.linde.linde_backend.entities.cisterna.Cisterna;

@Component 
public class CisternaMapper {
    public Cisterna toEntity(CisternaRequest dto){
        return Cisterna.builder()
        .placa(dto.placa())
        .nombre(dto.nombre())
        .capacidad(dto.capacidad())
        .estado(dto.estado())
        .build();
    }
    public CisternaResponse toDto(Cisterna entity){
        return new CisternaResponse(
            entity.getIdCisterna(), 
            entity.getPlaca(),
            entity.getNombre(),
            entity.getCapacidad(),
            entity.getEstado());
    }
}
