package com.linde.linde_backend.services.cisterna;
import java.util.List;
import org.springframework.stereotype.Service;
import lombok.RequiredArgsConstructor;
import com.linde.linde_backend.entities.cisterna.Falla;
import com.linde.linde_backend.repositories.cisterna.FallaRepository;
import com.linde.linde_backend.repositories.cisterna.CisternaRepository;
import com.linde.linde_backend.repositories.trabajador.ConductorRepository;
import com.linde.linde_backend.dto.cisterna.EliminarFallaRequest;
import com.linde.linde_backend.dto.cisterna.FallaRequest;
import com.linde.linde_backend.dto.cisterna.FallaResponse;
import com.linde.linde_backend.entities.cisterna.Cisterna;
import com.linde.linde_backend.entities.trabajador.Conductor;
import com.linde.linde_backend.mapper.cisterna.FallaMapper;

import jakarta.transaction.Transactional;

@Service 
@RequiredArgsConstructor 
public class FallaService {
    private final FallaRepository repository;
    private final CisternaRepository cisternaRepository;
    private final ConductorRepository conductorRepository;
    private final FallaMapper mapper;

    public List <FallaResponse> listar(){
        return repository.findAll().stream().map(mapper::toDto).toList();
    }
    
    public List<FallaResponse> findByCisterna_IdCisterna (Integer id){
        return repository.findByCisterna_IdCisterna(id).stream().map(mapper::toDto).toList();
    }

    @Transactional 
    public FallaResponse create(FallaRequest request){
        Cisterna cisterna= cisternaRepository.findById(request.idCisterna()).orElseThrow(() -> new jakarta.persistence.EntityNotFoundException("La cisterna no existe."));
        Conductor conductor = conductorRepository.findById(request.idTrabajador())
            .orElseThrow(() -> new jakarta.persistence.EntityNotFoundException("El conductor no se encuentra registrado."));
                
        Falla falla=mapper.toEntity(request);
        
        falla.setCisterna(cisterna);
        falla.setConductor(conductor);
        return mapper.toDto(repository.save(falla));
    } 

    @Transactional
    public FallaResponse cambiarEstado(EliminarFallaRequest request ){
        return repository.findById(request.idFalla()).map(
            falla -> {
            falla.setEstado("SOLUCIONADO");
            Falla fallanueva= repository.save(falla);
            return mapper.toDto(fallanueva);
            })
            .orElseThrow(() -> new jakarta.persistence.EntityNotFoundException("Dato inválido"));
    
    }

}