package com.linde.linde_backend.services;
import java.time.LocalDateTime;
import java.util.List;
import org.springframework.stereotype.Service;
import lombok.RequiredArgsConstructor;
import com.linde.linde_backend.entities.cisterna.Falla;
import com.linde.linde_backend.repositories.FallaRepository;
import com.linde.linde_backend.repositories.CisternaRepository;
import com.linde.linde_backend.repositories.ConductorRepository;
import com.linde.linde_backend.entities.cisterna.Cisterna;
import com.linde.linde_backend.entities.trabajador.Conductor;
import jakarta.transaction.Transactional;

@Service 
@RequiredArgsConstructor 
public class FallaService {
    private final FallaRepository repository;
    private final CisternaRepository cisternaRepository;
    private final ConductorRepository conductorRepository;

    public List <Falla> listar(){
        return repository.findAll();
    }
    
    public final List<Falla> findByCisterna_IdCisterna (Integer id){
        return repository.findByCisterna_IdCisterna(id);
    }

    @Transactional 
    public Falla create(String descripcion, LocalDateTime fechaHora, Integer idCisterna, Integer idConductor){
        Cisterna cisterna= cisternaRepository.findById(idCisterna).orElseThrow(() -> new jakarta.persistence.EntityNotFoundException("La cisterna no existe."));
        Conductor conductor = conductorRepository.findById(idConductor)
            .orElseThrow(() -> new jakarta.persistence.EntityNotFoundException("El conductor no existe."));
        
        Falla nuevaFalla= Falla.builder()
        .descripcion(descripcion)
        .fechaHora(fechaHora)
        .estado("PENDIENTE")
        .cisterna(cisterna)
        .conductor(conductor)
        .build();
        return repository.save(nuevaFalla);
    } 

    @Transactional
    public Falla cambiarEstado(Integer idFalla){
        return repository.findById(idFalla).map(
            falla -> {
            falla.setEstado("SOLUCIONADO");
            return repository.save(falla);
            })
            .orElseThrow(() -> new jakarta.persistence.EntityNotFoundException("La falla con ID " + idFalla + " no existe."));
    
    }

}