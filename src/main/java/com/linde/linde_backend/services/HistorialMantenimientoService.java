package com.linde.linde_backend.services;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
import org.springframework.stereotype.Service;
import jakarta.transaction.Transactional;
import com.linde.linde_backend.entities.cisterna.HistorialMantenimiento;
import com.linde.linde_backend.entities.cisterna.Cisterna;
import com.linde.linde_backend.entities.cisterna.Falla;
import com.linde.linde_backend.entities.trabajador.Tecnico;
import com.linde.linde_backend.repositories.HistorialMantenimientoRepository;
import com.linde.linde_backend.repositories.CisternaRepository;
import com.linde.linde_backend.repositories.FallaRepository;
import com.linde.linde_backend.repositories.TecnicoRepository; 
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class HistorialMantenimientoService {

    private final HistorialMantenimientoRepository repository;
    private final CisternaRepository cisternaRepository;
    private final TecnicoRepository tecnicoRepository;
    private final FallaRepository fallaRepository;

    public List<HistorialMantenimiento> listar() {
        return repository.findAll();
    }

    public List<HistorialMantenimiento> buscarPorCisterna(Integer idCisterna) {
        return repository.findByCisterna_IdCisterna(idCisterna);
    }

    // 🌟 Lógica del buscador por día sugerida
    public List<HistorialMantenimiento> buscarPorFecha(LocalDate fechaUsuario) {
        LocalDateTime inicioDia = fechaUsuario.atStartOfDay(); 
        LocalDateTime finDia = fechaUsuario.atTime(23, 59, 59, 999999999); 
        return repository.findByFechaBetween(inicioDia, finDia);
    }

    @Transactional
    public HistorialMantenimiento crear(LocalDateTime fecha, String descripcion, String resultado, String observaciones, BigDecimal costo, Integer idCisterna, Integer idTecnico, Integer idFalla) {
            
        Cisterna cisterna = cisternaRepository.findById(idCisterna)
            .orElseThrow(() -> new jakarta.persistence.EntityNotFoundException("Cisterna no registrada"));

        Tecnico tecnico = tecnicoRepository.findById(idTecnico)
            .orElseThrow(() -> new jakarta.persistence.EntityNotFoundException("Técnico no registrado"));

        HistorialMantenimiento nuevoHistorial= HistorialMantenimiento.builder()
        .fecha(fecha)
        .descripcion(descripcion)
        .resultado(resultado)
        .observaciones(observaciones)
        .costo(costo)
        .cisterna(idCisterna)
        .falla(idFalla)
        .tecnico(idTecnico)
        .build();
        return repository.save(nuevoHistorial);
    }
}
