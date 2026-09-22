package com.linde.linde_backend.mapper.pedido;

import org.springframework.stereotype.Component;

import com.linde.linde_backend.dto.pedido.ProductoRequest;
import com.linde.linde_backend.dto.pedido.ProductoResponse;
import com.linde.linde_backend.entities.pedido.Producto;
import com.linde.linde_backend.utils.Estado;

@Component
public class ProductoMapper {

    public Producto toEntity(ProductoRequest request) {

        return Producto.builder()
                .nombre(request.nombre())
                .tipoGas(request.tipoGas())
                .unidadMedida(request.unidadMedida())
                .precioUnitario(request.precioUnitario())
                .estado(Estado.ACTIVO)
                .build();
    }

    public ProductoResponse toResponse(Producto producto) {

        return new ProductoResponse(
                producto.getIdProducto(),
                producto.getNombre(),
                producto.getTipoGas(),
                producto.getUnidadMedida(),
                producto.getPrecioUnitario(),
                producto.getEstado()
        );
    }
}