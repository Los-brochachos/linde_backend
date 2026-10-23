package com.without.without_backend.services.pedido;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.Set;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.without.without_backend.dto.pedido.pedido.PedidoRequest;
import com.without.without_backend.dto.pedido.pedido.PedidoResponse;
import com.without.without_backend.dto.pedido.seguimientopedido.CambioEstadoPedidoRequest;
import com.without.without_backend.dto.pedido.seguimientopedido.CancelarPedidoRequest;
import com.without.without_backend.dto.pedido.detallepedido.DetallePedidoRequest;
import com.without.without_backend.entities.cliente.Cliente;
import com.without.without_backend.entities.pedido.DetallePedido;
import com.without.without_backend.entities.pedido.Pedido;
import com.without.without_backend.entities.pedido.Producto;
import com.without.without_backend.entities.pedido.SeguimientoPedido;
import com.without.without_backend.entities.usuario.Usuario;
import com.without.without_backend.mappers.pedido.DetallePedidoMapper;
import com.without.without_backend.mappers.pedido.PedidoMapper;
import com.without.without_backend.repositories.cliente.ClienteRepository;
import com.without.without_backend.repositories.pedido.DetallePedidoRepository;
import com.without.without_backend.repositories.pedido.PedidoRepository;
import com.without.without_backend.repositories.pedido.ProductoRepository;
import com.without.without_backend.repositories.pedido.SeguimientoPedidoRepository;
import com.without.without_backend.repositories.usuario.UsuarioRepository;
import com.without.without_backend.utils.Estado;
import com.without.without_backend.utils.pedido.EstadoPedido;

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
        private final com.without.without_backend.repositories.atencion.AtencionRepository atenciones;

        @Transactional(readOnly = true)
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

                detalle.setEstado(Estado.ACTIVO);
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

                if (pedido.getEstado() != EstadoPedido.RECIBIDO
                        && pedido.getEstado() != EstadoPedido.EN_PREPARACION) {
                        throw new IllegalArgumentException("Solo se puede cancelar un pedido RECIBIDO o EN_PREPARACION");
                }

                cancelarAtencionesPendientes(pedido, request.observacion());
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

                if (pedido.getEstado() != EstadoPedido.RECIBIDO
                        && pedido.getEstado() != EstadoPedido.EN_PREPARACION) {
                        throw new IllegalArgumentException("Solo se puede cancelar un pedido RECIBIDO o EN_PREPARACION");
                }

                cancelarAtencionesPendientes(pedido, request.observacion());
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
        private void cancelarAtencionesPendientes(Pedido pedido, String observacion) {
                var asociadas = atenciones.findByPedidoIdPedido(pedido.getIdPedido());
                if (asociadas.stream().anyMatch(a -> a.getEstado()
                        == com.without.without_backend.utils.atencion.EstadoAtencion.EN_CURSO)) {
                        throw new IllegalArgumentException("No se puede cancelar un pedido con una atención EN_CURSO");
                }
                for (var atencion : asociadas) {
                        if (atencion.getEstado() == com.without.without_backend.utils.atencion.EstadoAtencion.PROGRAMADA) {
                                atencion.setEstado(com.without.without_backend.utils.atencion.EstadoAtencion.CANCELADA);
                                atencion.setObservaciones(observacion == null || observacion.isBlank()
                                        ? "Cancelada por cancelación del pedido" : observacion.strip());
                                atenciones.save(atencion);
                        }
                }
        }
}
