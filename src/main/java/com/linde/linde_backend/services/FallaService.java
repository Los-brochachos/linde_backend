package com.linde.linde_backend.services;

import java.time.LocalDateTime;
import java.util.List;

import org.springframework.stereotype.Service;

import com.linde.linde_backend.entities.cisterna.Cisterna;
import com.linde.linde_backend.entities.cisterna.Falla;
import com.linde.linde_backend.entities.trabajador.Conductor;
import com.linde.linde_backend.repositories.CisternaRepository;
import com.linde.linde_backend.repositories.FallaRepository;
import com.linde.linde_backend.repositories.trabajador.ConductorRepository;

import jakarta.persistence.EntityNotFoundException;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class FallaService {

    private final FallaRepository repository;
    private final CisternaRepository cisternaRepository;
    private final ConductorRepository conductorRepository;

    public List<Falla> listar() {
        return repository.findAll();
    }

    public Falla obtener(Integer id) {
        return repository.findById(id)
            .orElseThrow(() -> new EntityNotFoundException("La falla con id " + id + " no existe."));
    }

    public List<Falla> listarPorCisterna(Integer idCisterna) {
        return repository.findByCisterna_IdCisterna(idCisterna);
    }

    @Transactional
    public Falla reportar(Integer idCisterna, Integer idConductor, String descripcion) {
        Cisterna cisterna = cisternaRepository.findById(idCisterna)
            .orElseThrow(() -> new EntityNotFoundException("La cisterna " + idCisterna + " no existe."));

        Conductor conductor = conductorRepository.findById(idConductor)
            .orElseThrow(() -> new EntityNotFoundException("El conductor " + idConductor + " no existe."));

        Falla falla = Falla.builder()
            .descripcion(descripcion)
            .fechaHora(LocalDateTime.now())
            .estado("PENDIENTE")
            .cisterna(cisterna)
            .conductor(conductor)
            .build();

        return repository.save(falla);
    }

    @Transactional
    public Falla actualizarEstado(Integer id, String estado) {
        Falla falla = obtener(id);
        falla.setEstado(estado);
        return repository.save(falla);
    }

    @Transactional
    public void eliminar(Integer id) {
        Falla falla = obtener(id);
        repository.delete(falla);
    }
}