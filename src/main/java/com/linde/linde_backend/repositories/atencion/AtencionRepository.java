package com.linde.linde_backend.repositories.atencion;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.linde.linde_backend.entities.atencion.Atencion;
import com.linde.linde_backend.utils.atencion.EstadoAtencion;

public interface AtencionRepository extends JpaRepository<Atencion, Integer> {

    List<Atencion> findByEstado(EstadoAtencion estado);

    List<Atencion> findByPedidoIdPedido(Integer idPedido);

    Optional<Atencion> findByIdAtencionAndEstado(
            Integer idAtencion,
            EstadoAtencion estado
    );

    List<Atencion> findByConductor_IdTrabajador(Integer idTrabajador);

    List<Atencion> findByCisterna_IdCisterna(Integer idCisterna);

    List<Atencion> findByProgramador_IdTrabajador(Integer idTrabajador);
}