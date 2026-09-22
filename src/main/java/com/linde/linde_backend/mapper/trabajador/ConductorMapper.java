package com.linde.linde_backend.mapper.trabajador;

import org.springframework.stereotype.Component;

import com.linde.linde_backend.dto.trabajador.ConductorRequest;
import com.linde.linde_backend.dto.trabajador.ConductorResponse;
import com.linde.linde_backend.entities.trabajador.Conductor;
import com.linde.linde_backend.entities.trabajador.Trabajador;

@Component
public class ConductorMapper {

    public Conductor toEntity(
            ConductorRequest request,
            Trabajador trabajador) {

        return Conductor.builder()
                .trabajador(trabajador)
                .licenciaConducir(request.licenciaConducir())
                .categoriaLicencia(request.categoriaLicencia())
                .fechaVencimientoLicencia(
                        request.fechaVencimientoLicencia()
                )
                .build();
    }

    public ConductorResponse toResponse(
            Conductor conductor) {

        Trabajador trabajador = conductor.getTrabajador();

        return new ConductorResponse(
                trabajador.getIdTrabajador(),
                trabajador.getNombres(),
                trabajador.getApellidos(),
                trabajador.getDni(),
                conductor.getLicenciaConducir(),
                conductor.getCategoriaLicencia(),
                conductor.getFechaVencimientoLicencia()
        );
    }
}