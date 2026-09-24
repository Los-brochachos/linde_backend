package com.linde.linde_backend.mapper.cisterna;

import org.springframework.stereotype.Component;
import com.linde.linde_backend.dto.cisterna.FallaRequest;
import com.linde.linde_backend.dto.cisterna.FallaResponse;
import com.linde.linde_backend.entities.cisterna.Falla;
import com.linde.linde_backend.entities.cisterna.Cisterna;
import com.linde.linde_backend.entities.trabajador.Conductor;


@Component 
public class FallaMapper {
    public Falla toEntity(FallaRequest dto){
        if(dto==null)return null; 
        Cisterna cisterna = Cisterna.builder().idCisterna(dto.idCisterna()).build();
        Conductor conductor = Conductor.builder().idTrabajador(dto.idTrabajador()).build();
        return Falla.builder()
        .descripcion(dto.descripcion())
        .fechaHora(dto.fechaHora())
        .estado(dto.estado())
        .cisterna(cisterna)
        .conductor(conductor)
        .build();
    }
    public FallaResponse toDto(Falla entity){
        return new FallaResponse(
            entity.getIdFalla(),
            entity.getDescripcion(), 
            entity.getFechaHora(),
            entity.getEstado(),
            entity.getCisterna() != null ? entity.getCisterna().getIdCisterna() : null,
            entity.getConductor() != null ? entity.getConductor().getIdTrabajador() : null
        );
    }
}
