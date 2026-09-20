package com.linde.linde_backend.services;

import java.time.LocalDateTime;
import java.util.List;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import com.linde.linde_backend.entities.usuario.Token;
import com.linde.linde_backend.entities.usuario.Usuario;
import com.linde.linde_backend.entities.usuario.Token.TokenType;
import com.linde.linde_backend.repositories.TokenRepository;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;

@Service 
@RequiredArgsConstructor 
public class TokenService {
  private final TokenRepository repository;
  @Value ("${application.jwt.token-expiration-after-days}")
  private Integer tokenExpirationAfterDays;
  @Value ("${application.jwt.refresh-token-expiration-after-days}")
  private Integer refreshTokenExpirationAfterDays;

  public List<Token> getTokensByUser(Usuario usuario) throws Exception{
    return repository.findByUser(usuario).orElseThrow(()->new Exception("User not found"));
  }

  @Transactional 
  public void saveTokens(Usuario usuario, String jwtToken, String refreshToken) {
    try {
      
    Token jwtTokenObj = Token.builder()
        .user(usuario)
        .token(jwtToken)
        .expirationDate(LocalDateTime.now().plusDays(tokenExpirationAfterDays))
        .type(TokenType.TOKEN)
        .build();
    Token refreshTokenObj = Token.builder()
        .user(usuario)
        .token(jwtToken)
        .expirationDate(LocalDateTime.now().plusDays(refreshTokenExpirationAfterDays))
        .type(TokenType.REFRESH_TOKEN)
        .build();
    repository.save(jwtTokenObj);
    repository.save(refreshTokenObj);
    } catch (Exception e) {
      throw new RuntimeException("Error saving tokens");
    }
  }
}