package com.linde.linde_backend.services.trabajador;

import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.linde.linde_backend.dto.trabajador.TecnicoRequest;
import com.linde.linde_backend.dto.trabajador.TecnicoResponse;
import com.linde.linde_backend.entities.trabajador.Tecnico;
import com.linde.linde_backend.entities.trabajador.Trabajador;
import com.linde.linde_backend.mapper.trabajador.TecnicoMapper;
import com.linde.linde_backend.repositories.trabajador.TecnicoRepository;
import com.linde.linde_backend.repositories.trabajador.TrabajadorRepository;
import com.linde.linde_backend.utils.RolesEnum;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class TecnicoService {

    private final TecnicoRepository tecnicoRepository;
    private final TrabajadorRepository trabajadorRepository;
    private final TecnicoMapper tecnicoMapper;

    public List<TecnicoResponse> listar() {

        return tecnicoRepository.findAll()
                .stream()
                .map(tecnicoMapper::toResponse)
                .toList();
    }

    public TecnicoResponse buscarPorId(Integer idTrabajador) {

        Tecnico tecnico = tecnicoRepository.findById(idTrabajador)
                .orElseThrow(() -> new RuntimeException(
                        "Técnico no encontrado"
                ));

        return tecnicoMapper.toResponse(tecnico);
    }

    @Transactional
    public TecnicoResponse insertar(Integer idTrabajador, TecnicoRequest request) {

        Trabajador trabajador = trabajadorRepository.findById(idTrabajador)
                .orElseThrow(() -> new RuntimeException(
                        "Trabajador no encontrado"
                ));

        if (trabajador.getUsuario().getRol() != RolesEnum.TECNICO) {
            throw new RuntimeException(
                    "El trabajador no tiene el rol de TECNICO"
            );
        }

        if (tecnicoRepository.existsById(idTrabajador)) {
            throw new RuntimeException(
                    "El trabajador ya está registrado como técnico"
            );
        }

        Tecnico tecnico = tecnicoMapper.toEntity(
                request,
                trabajador
        );

        tecnico = tecnicoRepository.save(tecnico);

        return tecnicoMapper.toResponse(tecnico);
    }

    @Transactional
    public TecnicoResponse actualizar(Integer idTrabajador,TecnicoRequest request) {

        Tecnico tecnico = tecnicoRepository.findById(idTrabajador)
                .orElseThrow(() -> new RuntimeException(
                        "Técnico no encontrado"
                ));

        tecnico.setEspecialidad(
                request.especialidad()
        );

        tecnico.setNivelTecnico(
                request.nivelTecnico()
        );

        tecnico.setCertificacion(
                request.certificacion()
        );

        tecnico = tecnicoRepository.save(tecnico);

        return tecnicoMapper.toResponse(tecnico);
    }

}