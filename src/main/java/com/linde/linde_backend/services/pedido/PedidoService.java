package com.linde.linde_backend.services.pedido;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.Set;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.linde.linde_backend.dto.pedido.pedido.PedidoRequest;
import com.linde.linde_backend.dto.pedido.pedido.PedidoResponse;
import com.linde.linde_backend.dto.pedido.seguimientopedido.CambioEstadoPedidoRequest;
import com.linde.linde_backend.dto.pedido.seguimientopedido.CancelarPedidoRequest;
import com.linde.linde_backend.dto.pedido.detallepedido.DetallePedidoRequest;
import com.linde.linde_backend.entities.cliente.Cliente;
import com.linde.linde_backend.entities.pedido.DetallePedido;
import com.linde.linde_backend.entities.pedido.Pedido;
import com.linde.linde_backend.entities.pedido.Producto;
import com.linde.linde_backend.entities.pedido.SeguimientoPedido;
import com.linde.linde_backend.entities.usuario.Usuario;
import com.linde.linde_backend.mappers.pedido.DetallePedidoMapper;
import com.linde.linde_backend.mappers.pedido.PedidoMapper;
import com.linde.linde_backend.repositories.cliente.ClienteRepository;
import com.linde.linde_backend.repositories.pedido.DetallePedidoRepository;
import com.linde.linde_backend.repositories.pedido.PedidoRepository;
import com.linde.linde_backend.repositories.pedido.ProductoRepository;
import com.linde.linde_backend.repositories.pedido.SeguimientoPedidoRepository;
import com.linde.linde_backend.repositories.usuario.UsuarioRepository;
import com.linde.linde_backend.utils.Estado;
import com.linde.linde_backend.utils.pedido.EstadoPedido;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class PedidoService {

        private final PedidoRepository pedidoRepository;
        private final ClienteRepository clienteRepository;
        private final ProductoRepository productoRepository;
        private final DetallePedidoRepository detallePedidoRepository;
        private final SeguimientoPedidoRepository seguimientoRepository;
        private final UsuarioRepository usuarioRepository;
        private final PedidoMapper pedidoMapper;
        private final DetallePedidoMapper detallePedidoMapper;

        public List<PedidoResponse> listar() {
                return pedidoRepository.findAll()
                        .stream()
                        .map(pedidoMapper::toResponse)
                        .toList();
        }

        public PedidoResponse buscarPorId(Integer id) {

                Pedido pedido = pedidoRepository.findById(id)
                        .orElseThrow(() ->
                                new NoSuchElementException("Pedido no encontrado"));

                return pedidoMapper.toResponse(pedido);
        }

        @Transactional
        public PedidoResponse insertar(PedidoRequest request, String correo) {

                // 1. Buscar Usuario
                Usuario usuario = usuarioRepository.findByCorreo(correo)
                        .orElseThrow(() ->
                                new NoSuchElementException("Usuario no encontrado"));

                // 2. Buscamos cliente 
                Cliente cliente = clienteRepository.findByUsuario(usuario)
                        .orElseThrow(() ->
                                new NoSuchElementException("Cliente no encontrado"));

                // 3. Verificar que no haya productos repetidos
                Set<Integer> productosAgregados = new HashSet<>();

                        for (DetallePedidoRequest detalleRequest : request.detalles()) {

                        if (!productosAgregados.add(detalleRequest.idProducto())) {
                                throw new IllegalArgumentException(
                                        "Un producto no puede repetirse dentro del mismo pedido");
                        }
                }

                // 4. Crear pedido
                Pedido pedido = pedidoMapper.toEntity(request, cliente);

                pedido.setFechaRegistro(LocalDate.now());
                pedido.setEstado(EstadoPedido.RECIBIDO);

                pedido = pedidoRepository.save(pedido);

                // 5. Crear detalles
                for (DetallePedidoRequest detalleRequest : request.detalles()) {

                Producto producto = productoRepository
                        .findById(detalleRequest.idProducto())
                        .orElseThrow(() ->new NoSuchElementException("Producto no encontrado: "+ detalleRequest.idProducto()));

                if (producto.getEstado() != Estado.ACTIVO) {
                        throw new IllegalArgumentException("El producto " + producto.getNombre()+ " no está activo");
                }

                DetallePedido detalle = detallePedidoMapper.toEntity(
                        detalleRequest,
                        pedido,
                        producto
                );

                detallePedidoRepository.save(detalle);
                }

                // 6. Crear seguimiento inicial
                SeguimientoPedido seguimiento = SeguimientoPedido.builder()
                        .fechaHora(LocalDateTime.now())
                        .estado(EstadoPedido.RECIBIDO)
                        .observacion("Pedido registrado")
                        .pedido(pedido)
                        .build();

                seguimientoRepository.save(seguimiento);

                return pedidoMapper.toResponse(pedido);
        }

        public List<PedidoResponse> listarMisPedidos(String correo) {

                Usuario usuario = usuarioRepository.findByCorreo(correo)
                        .orElseThrow(() ->
                                new NoSuchElementException("Usuario no encontrado"));

                Cliente cliente = clienteRepository.findByUsuario(usuario)
                        .orElseThrow(() ->
                                new NoSuchElementException("Cliente no encontrado"));

                return pedidoRepository
                        .findByClienteIdCliente(cliente.getIdCliente())
                        .stream()
                        .map(pedidoMapper::toResponse)
                        .toList();
        }

        public PedidoResponse buscarMiPedido(
                Integer idPedido,
                String correo) {

                Usuario usuario = usuarioRepository.findByCorreo(correo)
                        .orElseThrow(() ->
                                new NoSuchElementException("Usuario no encontrado"));

                Cliente cliente = clienteRepository.findByUsuario(usuario)
                        .orElseThrow(() ->
                                new NoSuchElementException("Cliente no encontrado"));

                Pedido pedido = pedidoRepository
                        .findByIdPedidoAndClienteIdCliente(
                                idPedido,
                                cliente.getIdCliente()
                        )
                        .orElseThrow(() ->
                                new NoSuchElementException("Pedido no encontrado"));

                return pedidoMapper.toResponse(pedido);
        }

        @Transactional
        public PedidoResponse cambiarEstado(
                Integer id,
                CambioEstadoPedidoRequest request) {

                Pedido pedido = pedidoRepository.findById(id)
                        .orElseThrow(() ->new NoSuchElementException("Pedido no encontrado"));

                if (pedido.getEstado() == EstadoPedido.ENTREGADO) {
                        throw new IllegalArgumentException("Un pedido entregado no puede cambiar de estado");
                }

                if (pedido.getEstado() == EstadoPedido.CANCELADO) {
                        throw new IllegalArgumentException("Un pedido cancelado no puede cambiar de estado");
                }

                if (request.estado() == EstadoPedido.CANCELADO) {
                        throw new IllegalArgumentException("Para cancelar el pedido utilice el endpoint de cancelación");
                }

                if (pedido.getEstado() == request.estado()) {
                        throw new IllegalArgumentException("El pedido ya se encuentra en ese estado");
                }

                if(!request.estado().esSiguienteA(pedido.getEstado())){
                        throw new IllegalArgumentException("El pedido debe avanzar al siguiente estado"
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

                return pedidoMapper.toResponse(pedido);
        }

        @Transactional
        public PedidoResponse cancelarMiPedido(
                Integer id,
                CancelarPedidoRequest request,
                String correo) {

                Usuario usuario = usuarioRepository.findByCorreo(correo)
                        .orElseThrow(() ->
                                new NoSuchElementException("Usuario no encontrado"));

                Cliente cliente = clienteRepository.findByUsuario(usuario)
                        .orElseThrow(() ->
                                new NoSuchElementException("Cliente no encontrado"));

                Pedido pedido = pedidoRepository
                        .findByIdPedidoAndClienteIdCliente(
                                id,
                                cliente.getIdCliente()
                        )
                        .orElseThrow(() ->
                                new NoSuchElementException("Pedido no encontrado"));

                if (pedido.getEstado() == EstadoPedido.ENTREGADO) {
                        throw new IllegalArgumentException(
                                "No se puede cancelar un pedido entregado");
                }

                if (pedido.getEstado() == EstadoPedido.CANCELADO) {
                        throw new IllegalArgumentException(
                                "El pedido ya está cancelado");
                }

                pedido.setEstado(EstadoPedido.CANCELADO);

                SeguimientoPedido seguimiento = SeguimientoPedido.builder()
                        .fechaHora(LocalDateTime.now())
                        .estado(EstadoPedido.CANCELADO)
                        .observacion(request.observacion())
                        .pedido(pedido)
                        .build();

                seguimientoRepository.save(seguimiento);

                return pedidoMapper.toResponse(pedido);
        }


        @Transactional
        public PedidoResponse cancelar(Integer id,CancelarPedidoRequest request) {

                Pedido pedido = pedidoRepository.findById(id)
                        .orElseThrow(() ->new NoSuchElementException("Pedido no encontrado"));

                if (pedido.getEstado() == EstadoPedido.ENTREGADO) {
                throw new IllegalArgumentException("No se puede cancelar un pedido entregado");
                }

                if (pedido.getEstado() == EstadoPedido.CANCELADO) {
                throw new IllegalArgumentException(
                        "El pedido ya está cancelado");
                }

                pedido.setEstado(EstadoPedido.CANCELADO);

                SeguimientoPedido seguimiento = SeguimientoPedido.builder()
                        .fechaHora(LocalDateTime.now())
                        .estado(EstadoPedido.CANCELADO)
                        .observacion(request.observacion())
                        .pedido(pedido)
                        .build();

                seguimientoRepository.save(seguimiento);

                return pedidoMapper.toResponse(pedido);
        }
}
