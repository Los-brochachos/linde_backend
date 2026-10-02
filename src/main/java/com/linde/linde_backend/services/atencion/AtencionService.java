package com.linde.linde_backend.services.atencion;

import java.time.LocalDateTime;
import java.util.List;
import java.util.NoSuchElementException;

import org.springframework.stereotype.Service;

import com.linde.linde_backend.dto.atencion.atencion.AtencionRequest;
import com.linde.linde_backend.dto.atencion.atencion.AtencionResponse;
import com.linde.linde_backend.dto.atencion.atencion.CancelarAtencionRequest;
import com.linde.linde_backend.entities.atencion.Atencion;
import com.linde.linde_backend.entities.cisterna.Cisterna;
import com.linde.linde_backend.entities.pedido.Pedido;
import com.linde.linde_backend.entities.trabajador.Conductor;
import com.linde.linde_backend.entities.trabajador.Programador;
import com.linde.linde_backend.mappers.atencion.AtencionMapper;
import com.linde.linde_backend.repositories.atencion.AtencionRepository;
import com.linde.linde_backend.repositories.cisterna.CisternaRepository;
import com.linde.linde_backend.repositories.pedido.PedidoRepository;
import com.linde.linde_backend.repositories.trabajador.ConductorRepository;
import com.linde.linde_backend.repositories.trabajador.ProgramadorRepository;
import com.linde.linde_backend.utils.Estado;
import com.linde.linde_backend.utils.atencion.EstadoAtencion;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class AtencionService {

        private final AtencionRepository repository;
        private final PedidoRepository pedidoRepository;
        private final ConductorRepository conductorRepository;
        private final CisternaRepository cisternaRepository;
        private final ProgramadorRepository programadorRepository;
        private final AtencionMapper mapper;

        public List<AtencionResponse> listar() {

                return repository.findAll()
                        .stream()
                        .map(mapper::toDto)
                        .toList();
        }

        public AtencionResponse buscarPorId(Integer id) {

                Atencion atencion = repository.findById(id)
                        .orElseThrow(() ->
                                new NoSuchElementException(
                                        "La atención no existe"));

                return mapper.toDto(atencion);
        }

        public List<AtencionResponse> listarPorEstado(
                EstadoAtencion estado) {

                return repository.findByEstado(estado)
                        .stream()
                        .map(mapper::toDto)
                        .toList();
        }

        public List<AtencionResponse> listarPorPedido(
                Integer idPedido) {

                if (!pedidoRepository.existsById(idPedido)) {
                throw new NoSuchElementException(
                        "El pedido no existe");
                }

                return repository.findByPedidoIdPedido(idPedido)
                        .stream()
                        .map(mapper::toDto)
                        .toList();
        }

        @Transactional
        public AtencionResponse crear(
                AtencionRequest request,
                String correo) {

                Programador programador = programadorRepository
                        .findByTrabajadorUsuarioCorreo(correo)
                        .orElseThrow(() ->
                                new NoSuchElementException("Programador no encontrado"));

                Pedido pedido = pedidoRepository
                        .findById(request.idPedido())
                        .orElseThrow(() ->
                                new NoSuchElementException("El pedido no existe"));

                Conductor conductor = conductorRepository
                        .findById(request.idConductor())
                        .orElseThrow(() ->
                                new NoSuchElementException( "El conductor no existe"));

                Cisterna cisterna = cisternaRepository
                        .findById(request.idCisterna())
                        .orElseThrow(() ->
                                new NoSuchElementException("La cisterna no existe"));

                if (conductor.getTrabajador().getEstado()!= Estado.ACTIVO) {
                        throw new IllegalArgumentException("El conductor no está activo");
                }

                if (cisterna.getEstado()!= Estado.ACTIVO) {
                        throw new IllegalArgumentException("La cisterna no está activa");
                }

                if (request.fechaFinProgramada().isBefore(request.fechaInicioProgramada())) {
                        throw new IllegalArgumentException(
                        "La fecha de fin programada no puede ser anterior "
                        + "a la fecha de inicio programada");
                }

                Atencion atencion = mapper.toEntity(request);

                atencion.setPedido(pedido);
                atencion.setConductor(conductor);
                atencion.setCisterna(cisterna);
                atencion.setProgramador(programador);
                atencion.setEstado(EstadoAtencion.PROGRAMADA);

                Atencion guardada = repository.save(atencion);

                return mapper.toDto(guardada);
        }

        @Transactional
        public AtencionResponse iniciar(Integer id) {

                Atencion atencion = repository.findById(id)
                        .orElseThrow(() -> new NoSuchElementException("La atención no existe"));

                if (atencion.getEstado() != EstadoAtencion.PROGRAMADA) {
                        throw new IllegalArgumentException("Solo se puede iniciar una atención PROGRAMADA");
                }

                atencion.setEstado(EstadoAtencion.EN_CURSO);
                atencion.setFechaInicio(LocalDateTime.now());

                Atencion actualizada = repository.save(atencion);

                return mapper.toDto(actualizada);
        }

        @Transactional
        public AtencionResponse finalizar(Integer id) {

        Atencion atencion = repository.findById(id)
                .orElseThrow(() ->
                        new NoSuchElementException(
                                "La atención no existe"));

        if (atencion.getEstado()
                != EstadoAtencion.EN_CURSO) {

            throw new IllegalArgumentException(
                    "Solo se puede finalizar una atención EN_CURSO");
        }

        atencion.setEstado(EstadoAtencion.FINALIZADA);
        atencion.setFechaFin(LocalDateTime.now());

        Atencion actualizada = repository.save(atencion);

        return mapper.toDto(actualizada);
    }

        @Transactional
        public AtencionResponse cancelar(Integer id,CancelarAtencionRequest request) {

                Atencion atencion = repository.findById(id).orElseThrow(() ->
                                new NoSuchElementException("La atención no existe"));

                if (atencion.getEstado()
                        != EstadoAtencion.PROGRAMADA) {

                throw new IllegalArgumentException(
                        "Solo se puede cancelar una atención PROGRAMADA");
                }

                atencion.setEstado(EstadoAtencion.CANCELADA);
                atencion.setObservaciones(request.observaciones());

                Atencion actualizada = repository.save(atencion);

                return mapper.toDto(actualizada);
        }
}