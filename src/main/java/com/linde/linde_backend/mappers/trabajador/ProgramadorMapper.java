package com.linde.linde_backend.mappers.trabajador;

import org.springframework.stereotype.Component;

import com.linde.linde_backend.dto.trabajador.programador.ProgramadorCreateRequest;
import com.linde.linde_backend.dto.trabajador.programador.ProgramadorResponse;
import com.linde.linde_backend.entities.trabajador.Programador;
import com.linde.linde_backend.entities.trabajador.Trabajador;

@Component
public class ProgramadorMapper {

    public Programador toEntity(
            ProgramadorCreateRequest request,
            Trabajador trabajador) {

        return Programador.builder()
                .trabajador(trabajador)
                .turno(request.turno())
                .nivelIngles(request.nivelIngles())
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
                trabajador.getTelefono(),
                trabajador.getDireccion(),
                trabajador.getFechaIngreso(),
                trabajador.getEstado(),
                trabajador.getUsuario().getCorreo(),
                trabajador.getUsuario().getRol(),
                programador.getNivelIngles(),
                programador.getTurno(),
                trabajador.getFechaCreacion(),
                trabajador.getFechaActualizacion()
        );
    }
}
