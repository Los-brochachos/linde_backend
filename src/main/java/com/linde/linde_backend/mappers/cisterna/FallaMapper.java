package com.linde.linde_backend.mappers.cisterna;

import org.springframework.stereotype.Component;

import com.linde.linde_backend.dto.cisterna.falla.FallaRequest;
import com.linde.linde_backend.dto.cisterna.falla.FallaResponse;
import com.linde.linde_backend.entities.cisterna.Cisterna;
import com.linde.linde_backend.entities.cisterna.Falla;

@Component
public class FallaMapper {

    public Falla toEntity(FallaRequest dto) {

        Cisterna cisterna = Cisterna.builder()
                .idCisterna(dto.idCisterna())
                .build();

        return Falla.builder()
                .descripcion(dto.descripcion())
                .fechaHora(dto.fechaHora())
                .cisterna(cisterna)
                .build();
    }

    public FallaResponse toDto(Falla entity) {

        if (entity == null) {
            return null;
        }

        return new FallaResponse(
                entity.getIdFalla(),
                entity.getDescripcion(),
                entity.getFechaHora(),
                entity.getEstado(),
                entity.getCisterna() != null
                        ? entity.getCisterna().getIdCisterna()
                        : null,
                entity.getTecnico() != null
                        ? entity.getTecnico().getIdTrabajador()
                        : null
        );
    }
}