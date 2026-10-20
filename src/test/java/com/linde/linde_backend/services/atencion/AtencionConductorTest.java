package com.linde.linde_backend.services.atencion;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;
import java.util.List;
import java.util.Optional;
import java.util.NoSuchElementException;
import org.junit.jupiter.api.Test;
import com.linde.linde_backend.entities.atencion.Atencion;
import com.linde.linde_backend.mappers.atencion.AtencionMapper;
import com.linde.linde_backend.repositories.atencion.AtencionRepository;
import com.linde.linde_backend.utils.atencion.EstadoAtencion;

class AtencionConductorTest {
    private final AtencionRepository repository = mock(AtencionRepository.class);
    private final AtencionService service = new AtencionService(repository, null, null, null, null, new AtencionMapper());
    private final String correo = "conductor@ejemplo.com";
    private Atencion preparar(EstadoAtencion estado) {
        var a = Atencion.builder().idAtencion(8).estado(estado).build();
        when(repository.findByIdAtencionAndConductor_Trabajador_Usuario_Correo(8, correo)).thenReturn(Optional.of(a));
        when(repository.save(any(Atencion.class))).thenAnswer(i -> i.getArgument(0));
        return a;
    }
    @Test void listaUnicamenteLasAsignadasAlCorreo() {
        when(repository.findByConductor_Trabajador_Usuario_Correo(correo)).thenReturn(List.of());
        assertTrue(service.listarAsignadas(correo).isEmpty());
        verify(repository).findByConductor_Trabajador_Usuario_Correo(correo);
        verify(repository, never()).findAll();
    }
    @Test void noPuedeIniciarAtencionAjena() {
        assertThrows(NoSuchElementException.class, () -> service.iniciar(8, correo));
        verify(repository, never()).save(any());
    }
    @Test void noPuedeFinalizarAtencionAjena() {
        assertThrows(NoSuchElementException.class, () -> service.finalizar(8, correo));
        verify(repository, never()).save(any());
    }
    @Test void iniciarRegistraEstadoYHora() {
        preparar(EstadoAtencion.PROGRAMADA);
        var resultado = service.iniciar(8, correo);
        assertEquals(EstadoAtencion.EN_CURSO, resultado.estado());
        assertNotNull(resultado.fechaInicio());
        assertNull(resultado.fechaFin());
    }
    @Test void finalizarRegistraEstadoYConservaInicio() {
        var a = preparar(EstadoAtencion.EN_CURSO);
        var inicio = java.time.LocalDateTime.now().minusMinutes(10); a.setFechaInicio(inicio);
        var resultado = service.finalizar(8, correo);
        assertEquals(EstadoAtencion.FINALIZADA, resultado.estado());
        assertEquals(inicio, resultado.fechaInicio());
        assertTrue(resultado.fechaFin().isAfter(inicio));
    }
    @Test void noFinalizaSinIniciar() {
        preparar(EstadoAtencion.PROGRAMADA);
        assertThrows(IllegalArgumentException.class, () -> service.finalizar(8, correo));
        verify(repository, never()).save(any());
    }
    @Test void noIniciaDosVeces() {
        preparar(EstadoAtencion.EN_CURSO);
        assertThrows(IllegalArgumentException.class, () -> service.iniciar(8, correo));
        verify(repository, never()).save(any());
    }
    @Test void noFinalizaDosVeces() {
        preparar(EstadoAtencion.FINALIZADA);
        assertThrows(IllegalArgumentException.class, () -> service.finalizar(8, correo));
        verify(repository, never()).save(any());
    }
}
