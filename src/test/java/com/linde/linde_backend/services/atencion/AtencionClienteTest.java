package com.linde.linde_backend.services.atencion;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;
import java.util.List;
import org.junit.jupiter.api.Test;
import com.linde.linde_backend.entities.atencion.Atencion;
import com.linde.linde_backend.entities.pedido.Pedido;
import com.linde.linde_backend.mappers.atencion.AtencionMapper;
import com.linde.linde_backend.repositories.atencion.AtencionRepository;
import com.linde.linde_backend.utils.atencion.EstadoAtencion;

class AtencionClienteTest {
    @Test void consultaSoloLasAtencionesDelCorreoAutenticado() {
        var repository = mock(AtencionRepository.class);
        var atencion = Atencion.builder().idAtencion(7).estado(EstadoAtencion.PROGRAMADA)
            .pedido(Pedido.builder().idPedido(3).build()).build();
        when(repository.findByPedido_Cliente_Usuario_Correo("cliente@linde.example"))
            .thenReturn(List.of(atencion));
        var service = new AtencionService(repository, null, null, null, null, new AtencionMapper());
        var resultado = service.listarMisAtenciones("cliente@linde.example");
        assertEquals(1, resultado.size());
        assertEquals(7, resultado.getFirst().idAtencion());
        assertEquals(3, resultado.getFirst().idPedido());
        verify(repository).findByPedido_Cliente_Usuario_Correo("cliente@linde.example");
        verify(repository, never()).findAll();
    }

    @Test void clienteSinAtencionesRecibeListaVacia() {
        var repository = mock(AtencionRepository.class);
        when(repository.findByPedido_Cliente_Usuario_Correo("otro@linde.example")).thenReturn(List.of());
        var service = new AtencionService(repository, null, null, null, null, new AtencionMapper());
        assertTrue(service.listarMisAtenciones("otro@linde.example").isEmpty());
        verify(repository, never()).findAll();
    }
}
