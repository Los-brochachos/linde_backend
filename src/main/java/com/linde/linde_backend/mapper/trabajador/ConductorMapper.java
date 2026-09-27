package com.linde.linde_backend.mapper.trabajador;

import org.springframework.stereotype.Component;

import com.linde.linde_backend.dto.trabajador.conductor.ConductorCreateRequest;
import com.linde.linde_backend.dto.trabajador.conductor.ConductorResponse;
import com.linde.linde_backend.entities.trabajador.Conductor;
import com.linde.linde_backend.entities.trabajador.Trabajador;

@Component
public class ConductorMapper {

    public Conductor toEntity(
            ConductorCreateRequest request,
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
                trabajador.getTelefono(),
                trabajador.getDireccion(),
                trabajador.getFechaIngreso(),
                trabajador.getEstado(),
                trabajador.getUsuario().getCorreo(),
                trabajador.getUsuario().getRol(),
                conductor.getLicenciaConducir(),
                conductor.getCategoriaLicencia(),
                conductor.getFechaVencimientoLicencia(),
                trabajador.getFechaCreacion(),
                trabajador.getFechaActualizacion()
        );
    }
}

