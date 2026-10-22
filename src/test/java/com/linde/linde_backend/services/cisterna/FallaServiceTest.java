package com.linde.linde_backend.services.cisterna;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

import java.time.LocalDateTime;
import java.util.NoSuchElementException;
import java.util.Optional;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import com.linde.linde_backend.dto.cisterna.falla.CambiarEstadoFallaRequest;
import com.linde.linde_backend.dto.cisterna.falla.FallaRequest;
import com.linde.linde_backend.entities.usuario.Usuario;
import com.linde.linde_backend.entities.cisterna.Cisterna;
import com.linde.linde_backend.entities.cisterna.Falla;
import com.linde.linde_backend.entities.trabajador.Tecnico;
import com.linde.linde_backend.mappers.cisterna.FallaMapper;
import com.linde.linde_backend.repositories.cisterna.CisternaRepository;
import com.linde.linde_backend.repositories.cisterna.FallaRepository;
import com.linde.linde_backend.repositories.trabajador.TecnicoRepository;
import com.linde.linde_backend.repositories.usuario.UsuarioRepository;
import com.linde.linde_backend.utils.cisterna.EstadoFalla;

@ExtendWith(MockitoExtension.class)
@DisplayName("FallaService: transiciones de mantenimiento")
class FallaServiceTest {

    @Mock private FallaRepository repository;
    @Mock private CisternaRepository cisternaRepository;
    @Mock private TecnicoRepository tecnicoRepository;
    @Mock private UsuarioRepository usuarioRepository;
    private FallaService service;

    @BeforeEach
    void prepararServicio() {
        service = new FallaService(repository, cisternaRepository, tecnicoRepository,
            usuarioRepository, new FallaMapper());
    }

    @Test
    @DisplayName("F01 - Normal: una falla pendiente pasa a reparacion")
    void pendienteAvanzaAReparacion() {
        comprobarTransicionValida(EstadoFalla.PENDIENTE, EstadoFalla.EN_REPARACION);
    }

    @Test
    @DisplayName("F02 - Normal: una falla en reparacion pasa a resuelta")
    void reparacionAvanzaAResuelta() {
        comprobarTransicionValida(EstadoFalla.EN_REPARACION, EstadoFalla.RESUELTA);
    }

    @Test
    @DisplayName("F03 - Alternativo: no permite saltar de pendiente a resuelta")
    void rechazaSaltoDeEstado() {
        comprobarTransicionRechazada(EstadoFalla.PENDIENTE, EstadoFalla.RESUELTA);
    }

    @Test
    @DisplayName("F04 - Alternativo: no permite retroceder de reparacion a pendiente")
    void rechazaRetrocesoDeEstado() {
        comprobarTransicionRechazada(EstadoFalla.EN_REPARACION, EstadoFalla.PENDIENTE);
    }

    @Test
    @DisplayName("F05 - Limite: solicitar el mismo estado no produce una transicion")
    void rechazaElMismoEstado() {
        comprobarTransicionRechazada(EstadoFalla.PENDIENTE, EstadoFalla.PENDIENTE);
    }

    @Test
    @DisplayName("F06 - Limite: una falla resuelta no puede reabrirse")
    void rechazaSalidaDelEstadoFinal() {
        comprobarTransicionRechazada(EstadoFalla.RESUELTA, EstadoFalla.EN_REPARACION);
    }

    @Test
    @DisplayName("F07 - Alternativo: falla inexistente no se guarda")
    void cambiarEstadoDeFallaInexistenteFalla() {
        when(repository.findById(999)).thenReturn(Optional.empty());

        var error = assertThrows(NoSuchElementException.class,
            () -> service.cambiarEstado(new CambiarEstadoFallaRequest(999, EstadoFalla.EN_REPARACION)));

        assertEquals("La falla no existe", error.getMessage());
        verify(repository, never()).save(any());
    }

    @Test
    @DisplayName("F08 - Normal: crear asigna tecnico, cisterna y estado pendiente")
    void crearFallaAsignaLasRelacionesCorrectas() {
        Usuario usuario = Usuario.builder().idUsuario(2).build();
        Tecnico tecnico = Tecnico.builder().idTrabajador(7).build();
        Cisterna cisterna = Cisterna.builder().idCisterna(3).build();
        when(usuarioRepository.findByCorreo("tecnico@linde.example")).thenReturn(Optional.of(usuario));
        when(tecnicoRepository.findByTrabajadorUsuario(usuario)).thenReturn(Optional.of(tecnico));
        when(cisternaRepository.findById(3)).thenReturn(Optional.of(cisterna));
        when(repository.save(any(Falla.class))).thenAnswer(invocation -> {
            Falla guardada = invocation.getArgument(0);
            assertSame(tecnico, guardada.getTecnico());
            assertSame(cisterna, guardada.getCisterna());
            guardada.setIdFalla(20);
            return guardada;
        });

        var response = service.crear(solicitudCreacion(), "tecnico@linde.example");

        assertAll(
            () -> assertEquals(20, response.idFalla()),
            () -> assertEquals(EstadoFalla.PENDIENTE, response.estado()),
            () -> assertEquals(3, response.idCisterna()),
            () -> assertEquals(7, response.idTrabajador()),
            () -> assertEquals(solicitudCreacion().descripcion(), response.descripcion()),
            () -> assertEquals(solicitudCreacion().fechaHora(), response.fechaHora())
        );
        verify(repository).save(any(Falla.class));
    }

    @Test
    @DisplayName("F09 - Alternativo: usuario inexistente impide crear la falla")
    void crearSinUsuarioNoGuarda() {
        when(usuarioRepository.findByCorreo("tecnico@linde.example")).thenReturn(Optional.empty());

        var error = assertThrows(NoSuchElementException.class,
            () -> service.crear(solicitudCreacion(), "tecnico@linde.example"));

        assertEquals("Usuario no encontrado", error.getMessage());
        verifyNoInteractions(tecnicoRepository, cisternaRepository, repository);
    }

    @Test
    @DisplayName("F10 - Alternativo: usuario sin tecnico asociado impide crear la falla")
    void crearSinTecnicoNoGuarda() {
        Usuario usuario = Usuario.builder().idUsuario(2).build();
        when(usuarioRepository.findByCorreo("tecnico@linde.example")).thenReturn(Optional.of(usuario));
        when(tecnicoRepository.findByTrabajadorUsuario(usuario)).thenReturn(Optional.empty());

        var error = assertThrows(NoSuchElementException.class,
            () -> service.crear(solicitudCreacion(), "tecnico@linde.example"));

        assertEquals("Técnico no encontrado", error.getMessage());
        verifyNoInteractions(cisternaRepository, repository);
    }

    @Test
    @DisplayName("F11 - Alternativo: cisterna inexistente impide crear la falla")
    void crearSinCisternaNoGuarda() {
        Usuario usuario = Usuario.builder().idUsuario(2).build();
        when(usuarioRepository.findByCorreo("tecnico@linde.example")).thenReturn(Optional.of(usuario));
        when(tecnicoRepository.findByTrabajadorUsuario(usuario))
            .thenReturn(Optional.of(Tecnico.builder().idTrabajador(7).build()));
        when(cisternaRepository.findById(3)).thenReturn(Optional.empty());

        var error = assertThrows(NoSuchElementException.class,
            () -> service.crear(solicitudCreacion(), "tecnico@linde.example"));

        assertEquals("La cisterna no existe", error.getMessage());
        verify(repository, never()).save(any());
    }

    private FallaRequest solicitudCreacion() {
        return new FallaRequest("Fuga en valvula", LocalDateTime.of(2026, 10, 1, 9, 0), 3);
    }

    private void comprobarTransicionValida(EstadoFalla actual, EstadoFalla nuevo) {
        Falla falla = falla(actual);
        when(repository.findById(20)).thenReturn(Optional.of(falla));
        when(repository.save(falla)).thenReturn(falla);

        var response = service.cambiarEstado(new CambiarEstadoFallaRequest(20, nuevo));

        assertAll(
            () -> assertEquals(nuevo, falla.getEstado()),
            () -> assertEquals(nuevo, response.estado()),
            () -> assertEquals(20, response.idFalla()),
            () -> assertEquals("Fuga en valvula", response.descripcion()),
            () -> assertEquals(falla.getFechaHora(), response.fechaHora()),
            () -> assertEquals(3, response.idCisterna()),
            () -> assertEquals(7, response.idTrabajador())
        );
        verify(repository).save(falla);
    }

    private void comprobarTransicionRechazada(EstadoFalla actual, EstadoFalla nuevo) {
        Falla falla = falla(actual);
        when(repository.findById(20)).thenReturn(Optional.of(falla));

        var error = assertThrows(IllegalArgumentException.class,
            () -> service.cambiarEstado(new CambiarEstadoFallaRequest(20, nuevo)));

        assertEquals("El estado de la falla debe avanzar de forma secuencial", error.getMessage());
        assertEquals(actual, falla.getEstado(), "Una solicitud rechazada no debe modificar la falla");
        verify(repository, never()).save(any());
    }

    private Falla falla(EstadoFalla estado) {
        return Falla.builder().idFalla(20).descripcion("Fuga en valvula")
            .fechaHora(LocalDateTime.of(2026, 10, 1, 9, 0)).estado(estado)
            .cisterna(Cisterna.builder().idCisterna(3).build())
            .tecnico(Tecnico.builder().idTrabajador(7).build()).build();
    }
}
