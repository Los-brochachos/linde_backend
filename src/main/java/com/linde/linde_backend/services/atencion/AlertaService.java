package com.linde.linde_backend.services.atencion;

import java.time.LocalDateTime;
import java.util.List;
import java.util.NoSuchElementException;

import org.springframework.stereotype.Service;

import com.linde.linde_backend.dto.atencion.alerta.AlertaRequest;
import com.linde.linde_backend.dto.atencion.alerta.AlertaResponse;
import com.linde.linde_backend.entities.atencion.Alerta;
import com.linde.linde_backend.entities.atencion.Atencion;
import com.linde.linde_backend.entities.trabajador.Conductor;
import com.linde.linde_backend.mappers.atencion.AlertaMapper;
import com.linde.linde_backend.repositories.atencion.AlertaRepository;
import com.linde.linde_backend.repositories.atencion.AtencionRepository;
import com.linde.linde_backend.repositories.trabajador.ConductorRepository;
import com.linde.linde_backend.utils.atencion.EstadoAtencion;
import com.linde.linde_backend.utils.atencion.EstadoAlerta;
import com.linde.linde_backend.utils.atencion.TipoAlerta;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class AlertaService {

    private final AlertaRepository repository;
    private final AtencionRepository atencionRepository;
    private final ConductorRepository conductorRepository;
    private final AlertaMapper mapper;

    @Transactional
    public AlertaResponse crear(
            AlertaRequest request,
            String correo
    ) {

        Atencion atencion = atencionRepository.findById(request.idAtencion())
                .orElseThrow(() ->
                        new NoSuchElementException(
                                "La atención no existe"
                        ));

        Conductor conductor = conductorRepository
                .findByTrabajadorUsuarioCorreo(correo)
                .orElseThrow(() ->
                        new NoSuchElementException(
                                "Conductor no encontrado"
                        ));

        if (!atencion.getConductor()
                .getIdTrabajador()
                .equals(conductor.getIdTrabajador())) {

            throw new IllegalArgumentException(
                    "El conductor no está asignado a esta atención"
            );
        }

        if (atencion.getEstado() != EstadoAtencion.EN_CURSO) {
            throw new IllegalArgumentException(
                    "Solo se puede registrar una alerta durante una atención EN_CURSO"
            );
        }

        Alerta alerta = mapper.toEntity(request);

        alerta.setAtencion(atencion);
        alerta.setFechaHora(LocalDateTime.now());
        alerta.setEstado(EstadoAlerta.PENDIENTE);

        Alerta guardada = repository.save(alerta);

        return mapper.toDto(guardada);
    }

    @Transactional
    public List<AlertaResponse> listar() {

        return repository.findAll()
                .stream()
                .map(mapper::toDto)
                .toList();
    }

    @Transactional
    public AlertaResponse buscarPorId(Integer id) {

        Alerta alerta = repository.findById(id)
                .orElseThrow(() ->
                        new NoSuchElementException(
                                "La alerta no existe"
                        ));

        return mapper.toDto(alerta);
    }

    @Transactional
    public List<AlertaResponse> listarPorAtencion(
            Integer idAtencion
    ) {

        if (!atencionRepository.existsById(idAtencion)) {
            throw new NoSuchElementException(
                    "La atención no existe"
            );
        }

        return repository
                .findByAtencionIdAtencion(idAtencion)
                .stream()
                .map(mapper::toDto)
                .toList();
    }

    @Transactional
    public List<AlertaResponse> listarPorEstado(
            EstadoAlerta estado
    ) {

        return repository.findByEstado(estado)
                .stream()
                .map(mapper::toDto)
                .toList();
    }

    @Transactional
    public List<AlertaResponse> listarPorTipo(
            TipoAlerta tipo
    ) {

        return repository.findByTipo(tipo)
                .stream()
                .map(mapper::toDto)
                .toList();
    }

    @Transactional
    public AlertaResponse marcarRevisada(Integer id) {

        Alerta alerta = repository.findById(id)
                .orElseThrow(() ->
                        new NoSuchElementException(
                                "La alerta no existe"
                        ));

        if (alerta.getEstado() != EstadoAlerta.PENDIENTE) {
            throw new IllegalArgumentException(
                    "Solo se puede revisar una alerta PENDIENTE"
            );
        }

        alerta.setEstado(EstadoAlerta.REVISADA);

        return mapper.toDto(repository.save(alerta));
    }

    @Transactional
    public AlertaResponse marcarAtendida(Integer id) {

        Alerta alerta = repository.findById(id)
                .orElseThrow(() ->
                        new NoSuchElementException(
                                "La alerta no existe"
                        ));

        if (alerta.getEstado() != EstadoAlerta.REVISADA) {
            throw new IllegalArgumentException(
                    "Solo se puede atender una alerta REVISADA"
            );
        }

        alerta.setEstado(EstadoAlerta.ATENDIDA);

        return mapper.toDto(repository.save(alerta));
    }
}