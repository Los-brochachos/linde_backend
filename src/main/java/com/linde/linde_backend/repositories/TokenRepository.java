package com.linde.linde_backend.repositories;
/*esto es una prueba de maria! */
import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.linde.linde_backend.entities.usuario.Token;
import com.linde.linde_backend.entities.usuario.Usuario;

public interface TokenRepository extends JpaRepository<Token,Integer> {

    Optional<Token> findByToken(String token);

    Optional<List<Token>> findByUser(Usuario usuario);

}
