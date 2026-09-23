package com.linde.linde_backend.services.pedido;

import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.linde.linde_backend.dto.pedido.DetallePedidoRequest;
import com.linde.linde_backend.dto.pedido.DetallePedidoResponse;
import com.linde.linde_backend.entities.pedido.DetallePedido;
import com.linde.linde_backend.entities.pedido.Pedido;
import com.linde.linde_backend.entities.pedido.Producto;
import com.linde.linde_backend.mapper.pedido.DetallePedidoMapper;
import com.linde.linde_backend.repositories.pedido.DetallePedidoRepository;
import com.linde.linde_backend.repositories.pedido.PedidoRepository;
import com.linde.linde_backend.repositories.pedido.ProductoRepository;
import com.linde.linde_backend.utils.Estado;
import com.linde.linde_backend.utils.EstadoPedido;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class DetallePedidoService {

    private final DetallePedidoRepository detallePedidoRepository;
    private final PedidoRepository pedidoRepository;
    private final ProductoRepository productoRepository;
    private final DetallePedidoMapper detallePedidoMapper;

    @Transactional
    public DetallePedidoResponse crear(
            Integer idPedido,
            DetallePedidoRequest request) {

        Pedido pedido = pedidoRepository.findById(idPedido)
                .orElseThrow(() ->
                        new RuntimeException("Pedido no encontrado"));

        Producto producto = productoRepository.findById(request.idProducto())
                .orElseThrow(() ->
                        new RuntimeException("Producto no encontrado"));

        if (producto.getEstado() != Estado.ACTIVO) {
            throw new RuntimeException("El producto no está activo");
        }

        DetallePedido detalle = detallePedidoMapper.toEntity(
                request,
                pedido,
                producto
        );

        DetallePedido guardado = detallePedidoRepository.save(detalle);

        return detallePedidoMapper.toResponse(guardado);
    }

    @Transactional(readOnly = true)
    public List<DetallePedidoResponse> listarPorPedido(Integer idPedido) {

        if (!pedidoRepository.existsById(idPedido)) {
            throw new RuntimeException("Pedido no encontrado");
        }

        return detallePedidoRepository
                .findByPedidoIdPedido(idPedido)
                .stream()
                .map(detallePedidoMapper::toResponse)
                .toList();
    }

    @Transactional
    public DetallePedidoResponse actualizar(Integer idPedido,Integer idDetalle,DetallePedidoRequest request) {

        Pedido pedido = pedidoRepository.findById(idPedido)
                .orElseThrow(() ->
                        new RuntimeException("Pedido no encontrado"));

        if (pedido.getEstado() != EstadoPedido.RECIBIDO) {
            throw new RuntimeException(
                    "Solo se pueden editar los detalles de un pedido en estado RECIBIDO");
        }

        DetallePedido detalle = detallePedidoRepository.findById(idDetalle)
                .orElseThrow(() ->
                        new RuntimeException("Detalle de pedido no encontrado"));

        if (!detalle.getPedido().getIdPedido().equals(idPedido)) {
            throw new RuntimeException(
                    "El detalle no pertenece al pedido indicado");
        }

        Producto producto = productoRepository.findById(request.idProducto())
                .orElseThrow(() ->
                        new RuntimeException("Producto no encontrado"));

        if (producto.getEstado() != Estado.ACTIVO) {
            throw new RuntimeException("El producto no está activo");
        }

        detalle.setCantidad(request.cantidad());
        detalle.setProducto(producto);
        detalle.setPrecioUnitario(producto.getPrecioUnitario());

        DetallePedido actualizado =
                detallePedidoRepository.save(detalle);

        return detallePedidoMapper.toResponse(actualizado);
    }
}