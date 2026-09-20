package com.linde.linde_backend.services;

import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.linde.linde_backend.entities.usuario.Usuario;
import com.linde.linde_backend.repositories.UsuarioRepository;
import com.linde.linde_backend.utils.AuthRequest;
import com.linde.linde_backend.utils.AuthResponse;
import com.linde.linde_backend.utils.EstadoUsuario;
import com.linde.linde_backend.utils.RefreshTokenRequest;
import com.linde.linde_backend.utils.RegisterRequest;
import com.linde.linde_backend.utils.RegisterResponse;
import com.linde.linde_backend.utils.RolesEnum;

import lombok.RequiredArgsConstructor;

@Service 
@RequiredArgsConstructor 
public class AuthService {

  private final TokenService tokenService;
  private final UsuarioRepository userRepository;
  private final PasswordEncoder passwordEncoder;
  private final JwtService jwtService;
  private final AuthenticationManager authenticationManager;

  public RegisterResponse register(RegisterRequest request) {
    if (userRepository.findByCorreo(request.correo()).isPresent()) {
      return new RegisterResponse(request.correo(), "ADVERTENCIA", "El usuario ya esta registrado.");
    }
    var user = Usuario.builder()
        .correo(request.correo())
        .contraseña(passwordEncoder.encode(request.contraseña()))
        .rol(RolesEnum.USER)
        .estado(EstadoUsuario.ACTIVO)
        .build();
    userRepository.save(user).getCorreo();
    return new RegisterResponse(request.correo(), "INFORMACIÓN", "Nuevo usuario registrado.");
  }

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