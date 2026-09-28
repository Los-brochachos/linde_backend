package com.linde.linde_backend.services.trabajador;

import java.util.List;
import java.util.NoSuchElementException;

import org.springframework.stereotype.Service;

import com.linde.linde_backend.dto.trabajador.TrabajadorResponse;
import com.linde.linde_backend.entities.trabajador.Trabajador;
import com.linde.linde_backend.mappers.trabajador.TrabajadorMapper;
import com.linde.linde_backend.repositories.trabajador.TrabajadorRepository;
import com.linde.linde_backend.utils.Estado;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class TrabajadorService {

    private final TrabajadorRepository trabajadorRepository;
    private final TrabajadorMapper trabajadorMapper;

    public List<TrabajadorResponse> listarTodos() {

        return trabajadorRepository
                .findAll()
                .stream()
                .map(trabajadorMapper::toResponse)
                .toList();
    }

    public List<TrabajadorResponse> listarActivos() {

        return trabajadorRepository
                .findByEstado(Estado.ACTIVO)
                .stream()
                .map(trabajadorMapper::toResponse)
                .toList();
    }

    public TrabajadorResponse buscarPorId(Integer id) {

        Trabajador trabajador = trabajadorRepository
                .findById(id)
                .orElseThrow(() ->
                        new NoSuchElementException(
                                "Trabajador no encontrado"
                        ));

        return trabajadorMapper.toResponse(trabajador);
    }

    public TrabajadorResponse buscarActivoPorId(Integer id) {

        Trabajador trabajador = trabajadorRepository
                .findByIdTrabajadorAndEstado(
                        id,
                        Estado.ACTIVO
                )
                .orElseThrow(() ->
                        new NoSuchElementException(
                                "Trabajador no encontrado"
                        ));

        return trabajadorMapper.toResponse(trabajador);
    }

    public TrabajadorResponse buscarPorDni(String dni) {

        Trabajador trabajador = trabajadorRepository
                .findByDni(dni)
                .orElseThrow(() ->
                        new NoSuchElementException(
                                "Trabajador no encontrado"
                        ));

        return trabajadorMapper.toResponse(trabajador);
    }

    public TrabajadorResponse buscarActivoPorDni(String dni) {

        Trabajador trabajador = trabajadorRepository
                .findByDniAndEstado(
                        dni,
                        Estado.ACTIVO
                )
                .orElseThrow(() ->
                        new NoSuchElementException(
                                "Trabajador no encontrado"
                        ));

        return trabajadorMapper.toResponse(trabajador);
    }
}