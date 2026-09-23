package com.linde.linde_backend.services.cisterna;

import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Service;

import com.linde.linde_backend.dto.cisterna.CisternaRequest;
import com.linde.linde_backend.dto.cisterna.CisternaResponse;
import com.linde.linde_backend.dto.cisterna.EditarCisternaRequest;
import com.linde.linde_backend.dto.cisterna.EliminarCisternaRequest;
import com.linde.linde_backend.entities.cisterna.Cisterna;
import com.linde.linde_backend.mapper.cisterna.CisternaMapper;
import com.linde.linde_backend.repositories.cisterna.CisternaRepository;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;

@Service 
@RequiredArgsConstructor 
public class CisternaService {
    
    private final CisternaRepository repository;
    private final CisternaMapper mapper;

    public List<CisternaResponse> listar(){
        return repository.findAll().stream()
            .map(mapper::toDto)
            .toList();
    }    

    public Optional<CisternaResponse> findByPlaca(String placa){
        return repository.findByPlaca(placa)
            .map(mapper::toDto);
    }

    public Optional<CisternaResponse> findByNombre(String nombre){
        return repository.findByNombre(nombre)
            .map(mapper::toDto);
    }

    @Transactional 
    public CisternaResponse crear(CisternaRequest request){
        Cisterna cisterna = mapper.toEntity(request);
        return mapper.toDto(repository.save(cisterna));
    }
    
    @Transactional 
    public CisternaResponse eliminar(EliminarCisternaRequest request) { 
        return repository.findByNombre(request.nombre()) 
            .map(cisterna -> {
                cisterna.setEstado("INACTIVO"); 
                Cisterna cisternaGuardada = repository.save(cisterna);
                return mapper.toDto(cisternaGuardada);
            })
            .orElseThrow(() -> new jakarta.persistence.EntityNotFoundException("La cisterna '" + request.nombre() + "' no existe."));
    }

    @Transactional 
public CisternaResponse editar(EditarCisternaRequest request) {
    return repository.findByNombre(request.nombreBuscar())
        .map(cisterna -> {
            if (request.placa() != null) cisterna.setPlaca(request.placa());
            if (request.nombre() != null) cisterna.setNombre(request.nombre()); 
            if (request.capacidad() != null) cisterna.setCapacidad(request.capacidad());
            
            Cisterna cisternaGuardada = repository.save(cisterna);
            return mapper.toDto(cisternaGuardada);
        })
        .orElseThrow(() -> new jakarta.persistence.EntityNotFoundException(
            "La cisterna '" + request.nombreBuscar() + "' no existe."
        ));
}
}
