package com.linde.linde_backend.services.pedido;

import java.util.List;
import java.util.NoSuchElementException;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.linde.linde_backend.dto.pedido.seguimientopedido.SeguimientoPedidoResponse;
import com.linde.linde_backend.mappers.pedido.SeguimientoPedidoMapper;
import com.linde.linde_backend.repositories.pedido.PedidoRepository;
import com.linde.linde_backend.repositories.pedido.SeguimientoPedidoRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class SeguimientoPedidoService {

    private final SeguimientoPedidoRepository seguimientoPedidoRepository;
    private final PedidoRepository pedidoRepository;
    private final SeguimientoPedidoMapper seguimientoPedidoMapper;

    @Transactional(readOnly = true)
    public List<SeguimientoPedidoResponse> listarPorPedido(
            Integer idPedido) {

        if (!pedidoRepository.existsById(idPedido)) {
            throw new NoSuchElementException(
                    "Pedido no encontrado");
        }

        return seguimientoPedidoRepository
                .findByPedidoIdPedidoOrderByFechaHoraAsc(idPedido)
                .stream()
                .map(seguimientoPedidoMapper::toResponse)
                .toList();
    }
}