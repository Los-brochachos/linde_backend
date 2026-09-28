package com.linde.linde_backend.mappers.trabajador;

import org.springframework.stereotype.Component;

import com.linde.linde_backend.dto.trabajador.TrabajadorResponse;
import com.linde.linde_backend.entities.trabajador.Trabajador;

@Component
public class TrabajadorMapper {

    public TrabajadorResponse toResponse(Trabajador trabajador) {

        return new TrabajadorResponse(
                trabajador.getIdTrabajador(),
                trabajador.getNombres(),
                trabajador.getApellidos(),
                trabajador.getDni(),
                trabajador.getTelefono(),
                trabajador.getDireccion(),
                trabajador.getFechaIngreso(),
                trabajador.getEstado(),
                trabajador.getUsuario().getCorreo(),
                trabajador.getUsuario().getRol()
        );
    }
}