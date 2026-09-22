package com.linde.linde_backend.services.trabajador;

import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.linde.linde_backend.dto.trabajador.ConductorRequest;
import com.linde.linde_backend.dto.trabajador.ConductorResponse;
import com.linde.linde_backend.entities.trabajador.Conductor;
import com.linde.linde_backend.entities.trabajador.Trabajador;
import com.linde.linde_backend.mapper.trabajador.ConductorMapper;
import com.linde.linde_backend.repositories.trabajador.ConductorRepository;
import com.linde.linde_backend.repositories.trabajador.TrabajadorRepository;
import com.linde.linde_backend.utils.EstadoUsuario;
import com.linde.linde_backend.utils.RolesEnum;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class ConductorService {

    private final ConductorRepository conductorRepository;
    private final TrabajadorRepository trabajadorRepository;
    private final ConductorMapper conductorMapper;

    public List<ConductorResponse> listar() {

        return conductorRepository.findAll()
                .stream()
                .map(conductorMapper::toResponse)
                .toList();
    }

    public ConductorResponse buscarPorId(Integer idTrabajador) {

        Conductor conductor = conductorRepository.findById(idTrabajador)
                .orElseThrow(() -> new RuntimeException(
                        "Conductor no encontrado"
                ));

        return conductorMapper.toResponse(conductor);
    }

    @Transactional
    public ConductorResponse insertar(Integer idTrabajador, ConductorRequest request) {

        Trabajador trabajador = trabajadorRepository.findById(idTrabajador)
                .orElseThrow(() -> new RuntimeException(
                        "Trabajador no encontrado"
                ));

        if (trabajador.getUsuario().getRol() != RolesEnum.CONDUCTOR) {
            throw new RuntimeException(
                    "El trabajador no tiene el rol de CONDUCTOR"
            );
        }

        if (conductorRepository.existsById(idTrabajador)) {
            throw new RuntimeException(
                    "El trabajador ya está registrado como conductor"
            );
        }

        Conductor conductor = conductorMapper.toEntity(
                request,
                trabajador
        );

        conductor = conductorRepository.save(conductor);

        return conductorMapper.toResponse(conductor);
    }

    @Transactional
    public ConductorResponse actualizar(Integer idTrabajador,ConductorRequest request) {

        Conductor conductor = conductorRepository.findById(idTrabajador)
                .orElseThrow(() -> new RuntimeException(
                        "Conductor no encontrado"
                ));

        conductor.setLicenciaConducir(
                request.licenciaConducir()
        );

        conductor.setCategoriaLicencia(
                request.categoriaLicencia()
        );

        conductor.setFechaVencimientoLicencia(
                request.fechaVencimientoLicencia()
        );

        conductor = conductorRepository.save(conductor);

        return conductorMapper.toResponse(conductor);
    }


}