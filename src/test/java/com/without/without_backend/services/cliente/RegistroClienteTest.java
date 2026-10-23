package com.without.without_backend.services.cliente;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;
import java.util.Optional;
import org.junit.jupiter.api.Test;
import org.springframework.security.crypto.password.PasswordEncoder;
import com.without.without_backend.dto.cliente.ClienteCreateRequest;
import com.without.without_backend.entities.cliente.Cliente;
import com.without.without_backend.entities.usuario.Usuario;
import com.without.without_backend.mappers.cliente.ClienteMapper;
import com.without.without_backend.repositories.cliente.ClienteRepository;
import com.without.without_backend.repositories.usuario.UsuarioRepository;
import com.without.without_backend.utils.RolesEnum;
import com.without.without_backend.utils.Estado;

class RegistroClienteTest {
    private final ClienteRepository clientes = mock(ClienteRepository.class);
    private final UsuarioRepository usuarios = mock(UsuarioRepository.class);
    private final PasswordEncoder encoder = mock(PasswordEncoder.class);
    private final ClienteService service = new ClienteService(clientes, usuarios, new ClienteMapper(), encoder);
    private final ClienteCreateRequest request = new ClienteCreateRequest("20123456789", "Empresa de prueba", "Dirección", "999999999", "cliente@ejemplo.com", "Prueba1234*");
    @Test void registroPublicoCreaSoloClienteYEncriptaClave() {
        when(encoder.encode(request.contraseña())).thenReturn("clave-encriptada");
        when(usuarios.save(any(Usuario.class))).thenAnswer(i -> {
            Usuario u = i.getArgument(0);
            assertEquals(RolesEnum.CLIENTE, u.getRol()); assertEquals(Estado.ACTIVO, u.getEstado());
            assertEquals("clave-encriptada", u.getContraseña()); u.setIdUsuario(7); return u;
        });
        when(clientes.save(any(Cliente.class))).thenAnswer(i -> { Cliente c = i.getArgument(0); c.setIdCliente(9); return c; });
        var resultado = service.crearCliente(request);
        assertEquals(9, resultado.cliente().idCliente());
        assertEquals(RolesEnum.CLIENTE, resultado.cliente().rol());
        verify(encoder).encode(request.contraseña());
    }
    @Test void correoDuplicadoSeRechazaSinGuardar() {
        when(usuarios.findByCorreo(request.correo())).thenReturn(Optional.of(Usuario.builder().build()));
        assertEquals("El correo ya está registrado", assertThrows(IllegalArgumentException.class, () -> service.crearCliente(request)).getMessage());
        verify(usuarios, never()).save(any()); verify(clientes, never()).save(any());
    }
}
