package com.without.without_backend.services.usuario;

import java.nio.charset.StandardCharsets;
import java.util.NoSuchElementException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import com.without.without_backend.dto.usuario.CambiarClaveRequest;
import com.without.without_backend.repositories.usuario.UsuarioRepository;
import lombok.RequiredArgsConstructor;

@Service @RequiredArgsConstructor
public class SeguridadCuentaService {
    private final UsuarioRepository usuarios;
    private final PasswordEncoder encoder;
    @Transactional
    public void cambiarClave(String correo, CambiarClaveRequest request) {
        var usuario = usuarios.findByCorreo(correo).orElseThrow(() -> new NoSuchElementException("Usuario no encontrado"));
        if (!encoder.matches(request.actual(), usuario.getContraseña())) throw new IllegalArgumentException("La contraseña actual no es correcta");
        if (request.nueva().getBytes(StandardCharsets.UTF_8).length > 72) throw new IllegalArgumentException("La nueva contraseña no puede superar 72 bytes");
        if (encoder.matches(request.nueva(), usuario.getContraseña())) throw new IllegalArgumentException("La nueva contraseña debe ser diferente de la actual");
        usuario.setContraseña(encoder.encode(request.nueva()));
        usuarios.save(usuario);
    }
}
