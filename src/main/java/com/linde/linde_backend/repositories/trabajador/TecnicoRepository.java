package com.linde.linde_backend.repositories.trabajador;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.linde.linde_backend.entities.trabajador.Tecnico;
import com.linde.linde_backend.entities.usuario.Usuario;
import com.linde.linde_backend.utils.Estado;

public interface TecnicoRepository extends JpaRepository<Tecnico, Integer> {

    List<Tecnico> findByTrabajadorEstado(Estado estado);

    Optional<Tecnico> findByIdTrabajadorAndTrabajadorEstado(
            Integer idTrabajador,
            Estado estado
    );

    Optional<Tecnico> findByUsuario(Usuario usuario);

    
}

