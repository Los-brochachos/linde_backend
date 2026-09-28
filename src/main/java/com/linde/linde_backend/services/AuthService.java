package com.linde.linde_backend.services;

import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.stereotype.Service;

import com.linde.linde_backend.repositories.usuario.UsuarioRepository;
import com.linde.linde_backend.utils.auth.AuthRequest;
import com.linde.linde_backend.utils.auth.AuthResponse;
import com.linde.linde_backend.utils.auth.RefreshTokenRequest;

import lombok.RequiredArgsConstructor;

@Service 
@RequiredArgsConstructor 
public class AuthService {

  private final TokenService tokenService;
  private final UsuarioRepository userRepository;
  private final JwtService jwtService;
  private final AuthenticationManager authenticationManager;



  public AuthResponse authenticate(AuthRequest request) {
    authenticationManager.authenticate(
        new UsernamePasswordAuthenticationToken(request.correo(), request.contraseña()));
    var user = userRepository.findByCorreo(request.correo()).orElseThrow();
    var jwtToken = jwtService.generateToken(user);
    var refreshToken = jwtService.generateRefreshToken(user);
    tokenService.saveTokens(user,jwtToken,refreshToken);
    return new AuthResponse(jwtToken, refreshToken);
  }

  public AuthResponse refreshToken(RefreshTokenRequest request) {
    String userEmail = jwtService.extractUsername(request.refreshToken());
    if (userEmail != null) {
      var user = userRepository.findByCorreo(userEmail).orElseThrow();
      if (jwtService.isTokenValid(request.refreshToken(), user)) {
        var accessToken = jwtService.generateToken(user);
        return new AuthResponse(accessToken, request.refreshToken());
      }
    }
    throw new RuntimeException("INVALIDO Refresh Token");
  }
}