package com.linde.linde_backend.services;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Service;

import com.linde.linde_backend.entities.cisterna.Cisterna;
import com.linde.linde_backend.repositories.CisternaRepository;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;

@Service 
@RequiredArgsConstructor 
public class CisternaService {
    private final CisternaRepository repository;
    public List <Cisterna> listar(){
        return repository.findAll();
    }    

    public Optional <Cisterna> findByPlaca(String placa){
        return repository.findByPlaca(placa);
    }

    public Optional <Cisterna> findByNombre(String nombre){
        return repository.findByNombre(nombre);
    }

    @Transactional 
    public Cisterna crear(String placa, String nombre, BigDecimal capacidad){
        return repository.findByPlaca(placa).orElseGet(
            ()->repository.save(Cisterna.builder()
        .placa(placa)
        .nombre(nombre)
        .capacidad(capacidad)
        .estado("ACTIVO")
        .build())
        );
    }

    @Transactional 
    public Cisterna eliminar(String nombre) {
    return repository.findByNombre(nombre)
        .map(cisterna -> {
            cisterna.setEstado("INACTIVO");
            return repository.save(cisterna);
        })
        .orElseThrow(() -> new jakarta.persistence.EntityNotFoundException("La cisterna " + nombre + " no existe."));
    }

    @Transactional 
    public Cisterna editar(String placa, String nombre, BigDecimal capacidad){
        return repository.findByNombre(nombre)
        .map(cisterna -> {
            cisterna.setPlaca(placa);
            cisterna.setNombre(nombre);
            cisterna.setCapacidad(capacidad);
            return repository.save(cisterna);
        })
        .orElseThrow(()-> new jakarta.persistence.EntityNotFoundException("La cisterna con placa " + placa + " no existe."));
    }
}
