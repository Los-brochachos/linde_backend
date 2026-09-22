package com.linde.linde_backend.mapper.pedido;

import org.springframework.stereotype.Component;

import com.linde.linde_backend.dto.pedido.DetallePedidoRequest;
import com.linde.linde_backend.dto.pedido.DetallePedidoResponse;
import com.linde.linde_backend.entities.pedido.DetallePedido;
import com.linde.linde_backend.entities.pedido.Pedido;
import com.linde.linde_backend.entities.pedido.Producto;

@Component
public class DetallePedidoMapper {

    public DetallePedido toEntity(
            DetallePedidoRequest request,
            Pedido pedido,
            Producto producto) {

        return DetallePedido.builder()
                .cantidad(request.cantidad())
                .precioUnitario(producto.getPrecioUnitario())
                .pedido(pedido)
                .producto(producto)
                .build();
    }

    public DetallePedidoResponse toResponse(DetallePedido detalle) {

        return new DetallePedidoResponse(
                detalle.getIdDetalle(),
                detalle.getCantidad(),
                detalle.getPrecioUnitario(),
                detalle.getProducto().getIdProducto(),
                detalle.getProducto().getNombre()
        );
    }
}