package com.linde.linde_backend.mapper.trabajador;

import org.springframework.stereotype.Component;

import com.linde.linde_backend.dto.trabajador.TrabajadorRequest;
import com.linde.linde_backend.dto.trabajador.TrabajadorResponse;
import com.linde.linde_backend.entities.trabajador.Trabajador;
import com.linde.linde_backend.entities.usuario.Usuario;

@Component
public class TrabajadorMapper {

    public Trabajador toEntity(TrabajadorRequest request, Usuario usuario) {

        return Trabajador.builder()
                .nombres(request.nombres())
                .apellidos(request.apellidos())
                .dni(request.dni())
                .telefono(request.telefono())
                .direccion(request.direccion())
                .fechaIngreso(request.fechaIngreso())
                .estado(request.estado())
                .usuario(usuario)
                .build();
    }

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
                trabajador.getUsuario().getIdUsuario(),
                trabajador.getUsuario().getCorreo(),
                trabajador.getUsuario().getRol()
        );
    }
}