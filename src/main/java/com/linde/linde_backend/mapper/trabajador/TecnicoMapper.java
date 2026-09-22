package com.linde.linde_backend.mapper.trabajador;

import org.springframework.stereotype.Component;

import com.linde.linde_backend.dto.trabajador.TecnicoRequest;
import com.linde.linde_backend.dto.trabajador.TecnicoResponse;
import com.linde.linde_backend.entities.trabajador.Tecnico;
import com.linde.linde_backend.entities.trabajador.Trabajador;

@Component
public class TecnicoMapper {

    public Tecnico toEntity(
            TecnicoRequest request,
            Trabajador trabajador) {

        return Tecnico.builder()
                .trabajador(trabajador)
                .especialidad(request.especialidad())
                .nivelTecnico(request.nivelTecnico())
                .certificacion(request.certificacion())
                .build();
    }

    public TecnicoResponse toResponse(
            Tecnico tecnico) {

        Trabajador trabajador = tecnico.getTrabajador();

        return new TecnicoResponse(
                trabajador.getIdTrabajador(),
                trabajador.getNombres(),
                trabajador.getApellidos(),
                trabajador.getDni(),
                tecnico.getEspecialidad(),
                tecnico.getNivelTecnico(),
                tecnico.getCertificacion()
        );
    }
}