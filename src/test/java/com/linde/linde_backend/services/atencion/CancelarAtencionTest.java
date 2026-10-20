package com.linde.linde_backend.services.atencion;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;
import java.util.List;
import java.util.Optional;
import org.junit.jupiter.api.Test;
import com.linde.linde_backend.dto.atencion.atencion.CancelarAtencionRequest;
import com.linde.linde_backend.entities.atencion.Atencion;
import com.linde.linde_backend.entities.pedido.Pedido;
import com.linde.linde_backend.mappers.atencion.AtencionMapper;
import com.linde.linde_backend.repositories.atencion.AtencionRepository;
import com.linde.linde_backend.utils.atencion.EstadoAtencion;

class CancelarAtencionTest {
    private final AtencionRepository repository = mock(AtencionRepository.class);
    private final AtencionService service = new AtencionService(repository, null, null, null, null, new AtencionMapper());
    private final CancelarAtencionRequest request = new CancelarAtencionRequest("Solicitud del cliente");
    @Test void cancelaYGuardaMotivoSinEliminarPedido() {
        var atencion = Atencion.builder().idAtencion(8).pedido(Pedido.builder().idPedido(11).build()).estado(EstadoAtencion.PROGRAMADA).build();
        when(repository.findById(8)).thenReturn(Optional.of(atencion));
        when(repository.save(any(Atencion.class))).thenAnswer(i -> i.getArgument(0));
        var resultado = service.cancelar(8, request);
        assertEquals(EstadoAtencion.CANCELADA, resultado.estado());
        assertEquals(11, resultado.idPedido());
        assertEquals(request.observaciones(), resultado.observaciones());
        verify(repository).save(atencion); verify(repository, never()).delete(any());
    }
    @Test void noCancelaAtencionesIniciadasFinalizadasOCanceladas() {
        for (var estado : List.of(EstadoAtencion.EN_CURSO, EstadoAtencion.FINALIZADA, EstadoAtencion.CANCELADA)) {
            when(repository.findById(8)).thenReturn(Optional.of(Atencion.builder().idAtencion(8).estado(estado).build()));
            assertThrows(IllegalArgumentException.class, () -> service.cancelar(8, request));
        }
        verify(repository, never()).save(any());
    }
    @Test void inexistenteNoSeCreaAlCancelar() {
        assertThrows(java.util.NoSuchElementException.class, () -> service.cancelar(99, request));
        verify(repository, never()).save(any());
    }
}
