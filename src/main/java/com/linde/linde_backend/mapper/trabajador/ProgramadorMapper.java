package com.linde.linde_backend.mapper.trabajador;

import org.springframework.stereotype.Component;

import com.linde.linde_backend.dto.trabajador.ProgramadorRequest;
import com.linde.linde_backend.dto.trabajador.ProgramadorResponse;
import com.linde.linde_backend.entities.trabajador.Programador;
import com.linde.linde_backend.entities.trabajador.Trabajador;

@Component
public class ProgramadorMapper {

    public Programador toEntity(
            ProgramadorRequest request,
            Trabajador trabajador) {

        return Programador.builder()
                .trabajador(trabajador)
                .area(request.area())
                .turno(request.turno())
                .build();
    }

    public ProgramadorResponse toResponse(
            Programador programador) {

        Trabajador trabajador = programador.getTrabajador();

        return new ProgramadorResponse(
                trabajador.getIdTrabajador(),
                trabajador.getNombres(),
                trabajador.getApellidos(),
                trabajador.getDni(),
                programador.getArea(),
                programador.getTurno()
        );
    }
}