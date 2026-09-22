package com.linde.linde_backend.services.trabajador;

import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.linde.linde_backend.dto.trabajador.ProgramadorRequest;
import com.linde.linde_backend.dto.trabajador.ProgramadorResponse;
import com.linde.linde_backend.entities.trabajador.Programador;
import com.linde.linde_backend.entities.trabajador.Trabajador;
import com.linde.linde_backend.mapper.trabajador.ProgramadorMapper;
import com.linde.linde_backend.repositories.trabajador.ProgramadorRepository;
import com.linde.linde_backend.repositories.trabajador.TrabajadorRepository;
import com.linde.linde_backend.utils.EstadoUsuario;
import com.linde.linde_backend.utils.RolesEnum;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class ProgramadorService {

    private final ProgramadorRepository programadorRepository;
    private final TrabajadorRepository trabajadorRepository;
    private final ProgramadorMapper programadorMapper;

    public List<ProgramadorResponse> listar() {

        return programadorRepository.findAll()
                .stream()
                .map(programadorMapper::toResponse)
                .toList();
    }

    public ProgramadorResponse buscarPorId(Integer idTrabajador) {

        Programador programador = programadorRepository.findById(idTrabajador)
                .orElseThrow(() -> new RuntimeException(
                        "Programador no encontrado"
                ));

        return programadorMapper.toResponse(programador);
    }

    @Transactional
    public ProgramadorResponse insertar(Integer idTrabajador,  ProgramadorRequest request) {

        Trabajador trabajador = trabajadorRepository.findById(idTrabajador)
                .orElseThrow(() -> new RuntimeException(
                        "Trabajador no encontrado"
                ));

        if (trabajador.getUsuario().getRol() != RolesEnum.PROGRAMADOR) {
            throw new RuntimeException(
                    "El trabajador no tiene el rol de PROGRAMADOR"
            );
        }

        if (programadorRepository.existsById(idTrabajador)) {
            throw new RuntimeException(
                    "El trabajador ya está registrado como programador"
            );
        }

        Programador programador = programadorMapper.toEntity(
                request,
                trabajador
        );

        programador = programadorRepository.save(programador);

        return programadorMapper.toResponse(programador);
    }

    @Transactional
    public ProgramadorResponse actualizar(Integer idTrabajador, ProgramadorRequest request) {

        Programador programador = programadorRepository.findById(idTrabajador)
                .orElseThrow(() -> new RuntimeException(
                        "Programador no encontrado"
                ));

        programador.setArea(request.area());

        programador.setTurno(request.turno());

        programador = programadorRepository.save(programador);

        return programadorMapper.toResponse(programador);
    }

    @Transactional
    public void desactivar(Integer idTrabajador) {

        Programador programador = programadorRepository.findById(idTrabajador)
                .orElseThrow(() -> new RuntimeException(
                        "Programador no encontrado"
                ));

        Trabajador trabajador = programador.getTrabajador();

        trabajador.setEstado("INACTIVO");
        trabajador.getUsuario().setEstado(EstadoUsuario.INACTIVO);

        trabajadorRepository.save(trabajador);
    }
}