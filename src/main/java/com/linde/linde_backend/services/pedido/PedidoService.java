package com.linde.linde_backend.services.pedido;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.linde.linde_backend.dto.pedido.CambioEstadoPedidoRequest;
import com.linde.linde_backend.dto.pedido.PedidoRequest;
import com.linde.linde_backend.dto.pedido.PedidoResponse;
import com.linde.linde_backend.dto.pedido.SeguimientoPedidoResponse;
import com.linde.linde_backend.entities.cliente.Cliente;
import com.linde.linde_backend.entities.pedido.Pedido;
import com.linde.linde_backend.entities.pedido.SeguimientoPedido;
import com.linde.linde_backend.mapper.pedido.PedidoMapper;
import com.linde.linde_backend.mapper.pedido.SeguimientoPedidoMapper;
import com.linde.linde_backend.repositories.cliente.ClienteRepository;
import com.linde.linde_backend.repositories.pedido.PedidoRepository;
import com.linde.linde_backend.repositories.pedido.SeguimientoPedidoRepository;
import com.linde.linde_backend.utils.EstadoPedido;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class PedidoService {

    private final PedidoRepository pedidoRepository;
    private final ClienteRepository clienteRepository;
    private final SeguimientoPedidoRepository seguimientoRepository;
    private final PedidoMapper pedidoMapper;
    private final SeguimientoPedidoMapper seguimientoMapper;

    public List<PedidoResponse> listar() {

        return pedidoRepository.findAll()
                .stream()
                .map(pedidoMapper::toResponse)
                .toList();
    }

    public List<SeguimientoPedidoResponse> listarSeguimiento(Integer idPedido) {

        pedidoRepository.findById(idPedido)
                .orElseThrow(() -> new RuntimeException(
                        "Pedido no encontrado"
                ));

        return seguimientoRepository
                .findByPedidoIdPedidoOrderByFechaHoraAsc(idPedido)
                .stream()
                .map(seguimientoMapper::toResponse)
                .toList();
    }

    public PedidoResponse buscarPorId(Integer id) {

        Pedido pedido = pedidoRepository.findById(id)
                .orElseThrow(() -> new RuntimeException(
                        "Pedido no encontrado"
                ));

        return pedidoMapper.toResponse(pedido);
    }

    @Transactional
    public PedidoResponse insertar(PedidoRequest request) {

        Cliente cliente = clienteRepository.findById(request.idCliente())
                .orElseThrow(() -> new RuntimeException(
                        "Cliente no encontrado"
                ));

        Pedido pedido = pedidoMapper.toEntity(
                request,
                cliente
        );

        pedido.setFechaRegistro(LocalDate.now());

        pedido = pedidoRepository.save(pedido);

        return pedidoMapper.toResponse(pedido);
    }

    @Transactional
    public PedidoResponse cancelar(Integer id) {

        Pedido pedido = pedidoRepository.findById(id)
                .orElseThrow(() -> new RuntimeException(
                        "Pedido no encontrado"
                ));

        if (pedido.getEstado() == EstadoPedido.ENTREGADO) {
            throw new RuntimeException(
                    "No se puede cancelar un pedido entregado"
            );
        }

        if (pedido.getEstado() == EstadoPedido.CANCELADO) {
            throw new RuntimeException(
                    "El pedido ya está cancelado"
            );
        }

        pedido.setEstado(EstadoPedido.CANCELADO);

        SeguimientoPedido seguimiento = SeguimientoPedido.builder()
                .fechaHora(LocalDateTime.now())
                .estado(EstadoPedido.CANCELADO)
                .observacion("Pedido cancelado")
                .pedido(pedido)
                .build();

        seguimientoRepository.save(seguimiento);
        pedidoRepository.save(pedido);

        return pedidoMapper.toResponse(pedido);
    }

    @Transactional
    public PedidoResponse cambiarEstado(
            Integer id,
            CambioEstadoPedidoRequest request) {

        Pedido pedido = pedidoRepository.findById(id)
                .orElseThrow(() -> new RuntimeException(
                        "Pedido no encontrado"
                ));

        if (pedido.getEstado() == EstadoPedido.ENTREGADO) {
            throw new RuntimeException(
                    "Un pedido entregado no puede cambiar de estado"
            );
        }

        if (pedido.getEstado() == EstadoPedido.CANCELADO) {
            throw new RuntimeException(
                    "Un pedido cancelado no puede cambiar de estado"
            );
        }

        if (request.estado() == EstadoPedido.CANCELADO) {
            throw new RuntimeException(
                    "Para cancelar el pedido utilice el endpoint de cancelación"
            );
        }

        if (pedido.getEstado() == request.estado()) {
            throw new RuntimeException(
                    "El pedido ya se encuentra en ese estado"
            );
        }

        pedido.setEstado(request.estado());

        SeguimientoPedido seguimiento = SeguimientoPedido.builder()
                .fechaHora(LocalDateTime.now())
                .estado(request.estado())
                .observacion(request.observacion())
                .pedido(pedido)
                .build();

        seguimientoRepository.save(seguimiento);
        pedidoRepository.save(pedido);

        return pedidoMapper.toResponse(pedido);
    }
}