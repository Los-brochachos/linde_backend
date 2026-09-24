package com.linde.linde_backend.services.cisterna;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
import org.springframework.stereotype.Service;
import jakarta.transaction.Transactional;
import com.linde.linde_backend.entities.cisterna.HistorialMantenimiento;
import com.linde.linde_backend.dto.cisterna.HistorialMantenimientoRequest;
import com.linde.linde_backend.dto.cisterna.HistorialMantenimientoResponse;
import com.linde.linde_backend.entities.cisterna.Cisterna;
import com.linde.linde_backend.entities.cisterna.Falla;
import com.linde.linde_backend.entities.trabajador.Tecnico;
import com.linde.linde_backend.mapper.cisterna.HistorialMantenimientoMapper;
import com.linde.linde_backend.repositories.cisterna.HistorialMantenimientoRepository;
import com.linde.linde_backend.repositories.cisterna.CisternaRepository;
import com.linde.linde_backend.repositories.cisterna.FallaRepository;
import com.linde.linde_backend.repositories.trabajador.TecnicoRepository; // Asegúrate de tener este repo creado
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class HistorialMantenimientoService {

    private final HistorialMantenimientoRepository repository;
    private final CisternaRepository cisternaRepository;
    private final TecnicoRepository tecnicoRepository;
    private final FallaRepository fallaRepository;
    private final HistorialMantenimientoMapper mapper;

    public List<HistorialMantenimientoResponse> listar() {
        return repository.findAll().stream().map(mapper::toDto).toList();
    }

    public List<HistorialMantenimientoResponse> buscarPorCisterna(Integer idCisterna) {
        return repository.findByCisterna_IdCisterna(idCisterna).stream().map(mapper::toDto).toList();
    }

    public List<HistorialMantenimientoResponse> buscarNombre(String nombre) {
        return repository.findByCisterna_Nombre(nombre).stream().map(mapper::toDto).toList();
    }

    public List<HistorialMantenimientoResponse> buscarPorFecha(LocalDate fechaUsuario) {
        LocalDateTime inicioDia = fechaUsuario.atStartOfDay(); 
        LocalDateTime finDia = fechaUsuario.atTime(23, 59, 59, 999999999); 
        return repository.findByFechaBetween(inicioDia, finDia).stream().map(mapper::toDto).toList();
    }

    @Transactional
    public HistorialMantenimientoResponse crear(HistorialMantenimientoRequest request) {
            
        Cisterna cisterna = cisternaRepository.findById(request.idCisterna())
            .orElseThrow(() -> new jakarta.persistence.EntityNotFoundException("Cisterna no registrada"));

        Tecnico tecnico = tecnicoRepository.findById(request.idTrabajador())
            .orElseThrow(() -> new jakarta.persistence.EntityNotFoundException("Técnico no registrado"));

        Falla falla = null;
        if (request.idFalla() != null) {
            falla = fallaRepository.findById(request.idFalla())
                .orElseThrow(() -> new jakarta.persistence.EntityNotFoundException("Falla no registrada"));
        }

        HistorialMantenimiento historial= mapper.toEntity(request);
        historial.setCisterna(cisterna);
        historial.setTecnico(tecnico);
        historial.setFalla(falla);
        return mapper.toDto(repository.save(historial));
    }

}