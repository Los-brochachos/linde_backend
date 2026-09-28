package com.linde.linde_backend.services.pedido;

import java.util.List;
import java.util.NoSuchElementException;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.linde.linde_backend.dto.pedido.detallepedido.AgregarCantidadDetalleRequest;
import com.linde.linde_backend.dto.pedido.detallepedido.DetallePedidoRequest;
import com.linde.linde_backend.dto.pedido.detallepedido.DetallePedidoResponse;
import com.linde.linde_backend.entities.pedido.DetallePedido;
import com.linde.linde_backend.entities.pedido.Pedido;
import com.linde.linde_backend.entities.pedido.Producto;
import com.linde.linde_backend.mappers.pedido.DetallePedidoMapper;
import com.linde.linde_backend.repositories.pedido.DetallePedidoRepository;
import com.linde.linde_backend.repositories.pedido.PedidoRepository;
import com.linde.linde_backend.repositories.pedido.ProductoRepository;
import com.linde.linde_backend.utils.Estado;
import com.linde.linde_backend.utils.pedido.EstadoPedido;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class DetallePedidoService {

    private final DetallePedidoRepository detallePedidoRepository;
    private final PedidoRepository pedidoRepository;
    private final ProductoRepository productoRepository;
    private final DetallePedidoMapper detallePedidoMapper;

    @Transactional(readOnly = true)
    public List<DetallePedidoResponse> listarActivosPorPedido(
            Integer idPedido) {

        if (!pedidoRepository.existsById(idPedido)) {
            throw new NoSuchElementException("Pedido no encontrado");
        }

        return detallePedidoRepository
                .findByPedidoIdPedidoAndEstado(
                        idPedido,
                        Estado.ACTIVO)
                .stream()
                .map(detallePedidoMapper::toResponse)
                .toList();
    }

    @Transactional(readOnly = true)
    public List<DetallePedidoResponse> listarTodosPorPedido(
            Integer idPedido) {

        if (!pedidoRepository.existsById(idPedido)) {
            throw new NoSuchElementException("Pedido no encontrado");
        }

        return detallePedidoRepository
                .findByPedidoIdPedido(idPedido)
                .stream()
                .map(detallePedidoMapper::toResponse)
                .toList();
    }

    @Transactional
    public DetallePedidoResponse agregarDetalle(
            Integer idPedido,
            DetallePedidoRequest request) {

        Pedido pedido = pedidoRepository.findById(idPedido)
                .orElseThrow(() -> new NoSuchElementException("Pedido no encontrado"));

        validarPedidoEditable(pedido);

        Producto producto = productoRepository
                .findById(request.idProducto())
                .orElseThrow(() -> new NoSuchElementException("Producto no encontrado"));

        if (producto.getEstado() != Estado.ACTIVO) {
            throw new IllegalArgumentException("El producto no está activo");
        }

        boolean productoYaExiste = detallePedidoRepository.existsByPedidoIdPedidoAndProductoIdProductoAndEstado(idPedido,request.idProducto(),Estado.ACTIVO);

        if (productoYaExiste) {
            throw new IllegalArgumentException("El producto ya existe en el pedido. Puede agregar más cantidad");
        }

        DetallePedido detalle = detallePedidoMapper.toEntity(
                request,
                pedido,
                producto);

        DetallePedido guardado =
                detallePedidoRepository.save(detalle);

        return detallePedidoMapper.toResponse(guardado);
    }

    @Transactional
    public DetallePedidoResponse agregarCantidad(
            Integer idPedido,
            Integer idDetalle,
            AgregarCantidadDetalleRequest request) {

        Pedido pedido = pedidoRepository.findById(idPedido)
                .orElseThrow(() ->new NoSuchElementException("Pedido no encontrado"));

        validarPedidoEditable(pedido);

        DetallePedido detalle = obtenerDetalle(
                idPedido,
                idDetalle);

        validarDetalleActivo(detalle);

        detalle.setCantidad(detalle.getCantidad().add(request.cantidad()));

        DetallePedido actualizado = detallePedidoRepository.save(detalle);

        return detallePedidoMapper.toResponse(actualizado);
    }

    @Transactional
    public DetallePedidoResponse cancelar(
            Integer idPedido,
            Integer idDetalle) {

        Pedido pedido = pedidoRepository.findById(idPedido)
                .orElseThrow(() -> new NoSuchElementException("Pedido no encontrado"));

        validarPedidoEditable(pedido);

        DetallePedido detalle = obtenerDetalle(
                idPedido,
                idDetalle);

        validarDetalleActivo(detalle);

        detalle.setEstado(Estado.INACTIVO);

        DetallePedido actualizado =
                detallePedidoRepository.save(detalle);

        return detallePedidoMapper.toResponse(actualizado);
    }

    private void validarPedidoEditable(Pedido pedido) {

        if (pedido.getEstado() != EstadoPedido.RECIBIDO) {
            throw new IllegalArgumentException("Solo se pueden modificar los detalles de un pedido en estado RECIBIDO");
        }
    }

    private DetallePedido obtenerDetalle(
            Integer idPedido,
            Integer idDetalle) {

        DetallePedido detalle = detallePedidoRepository
                .findById(idDetalle)
                .orElseThrow(() ->
                        new NoSuchElementException("Detalle de pedido no encontrado"));

        if (!detalle.getPedido().getIdPedido().equals(idPedido)) {
            throw new IllegalArgumentException("El detalle no pertenece al pedido indicado");
        }

        return detalle;
    }

    private void validarDetalleActivo(
            DetallePedido detalle) {

        if (detalle.getEstado() != Estado.ACTIVO) {
            throw new IllegalArgumentException("El detalle está inactivo");
        }
    }
}