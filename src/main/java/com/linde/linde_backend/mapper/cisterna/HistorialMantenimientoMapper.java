package com.linde.linde_backend.mapper.cisterna;

import org.springframework.stereotype.Component;

import com.linde.linde_backend.dto.cisterna.HistorialMantenimientoRequest;
import com.linde.linde_backend.dto.cisterna.HistorialMantenimientoResponse;
import com.linde.linde_backend.entities.cisterna.Cisterna;
import com.linde.linde_backend.entities.cisterna.Falla;
import com.linde.linde_backend.entities.cisterna.HistorialMantenimiento;
import com.linde.linde_backend.entities.trabajador.Tecnico;

@Component 
public class HistorialMantenimientoMapper {
    public HistorialMantenimiento toEntity(HistorialMantenimientoRequest dto){
        if(dto==null)return null; 
            Cisterna cisterna = Cisterna.builder().idCisterna(dto.idCisterna()).build();
            Falla falla = Falla.builder().idFalla(dto.idFalla()).build();
            Tecnico tecnico = Tecnico.builder().idTrabajador(dto.idTrabajador()).build();
            return HistorialMantenimiento.builder()
            .fecha(dto.fecha())
            .descripcion(dto.descripcion())
            .resultado(dto.resultado())
            .observaciones(dto.observaciones())
            .costo(dto.costo())
            .cisterna(cisterna)
            .falla(falla)
            .tecnico(tecnico)
            .build();
        }
    public HistorialMantenimientoResponse toDto(HistorialMantenimiento entity){
        return new HistorialMantenimientoResponse(
            entity.getIdHistorial(),
            entity.getFecha(), 
            entity.getDescripcion(),
            entity.getResultado(),
            entity.getObservaciones(),
            entity.getCosto(),
            entity.getCisterna() != null ? entity.getCisterna().getIdCisterna() : null,
            entity.getFalla() != null ? entity.getFalla().getIdFalla() : null,
            entity.getTecnico() != null ? entity.getTecnico().getIdTrabajador() : null
            );
    }
}
