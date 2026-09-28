package com.linde.linde_backend.mappers.pedido;

import org.springframework.stereotype.Component;

import com.linde.linde_backend.dto.pedido.producto.ProductoCreateRequest;
import com.linde.linde_backend.dto.pedido.producto.ProductoResponse;
import com.linde.linde_backend.entities.pedido.Producto;

@Component
public class ProductoMapper {

    public Producto toEntity(ProductoCreateRequest request) {

        return Producto.builder()
                .nombre(request.nombre())
                .tipoGas(request.tipoGas())
                .unidadMedida(request.unidadMedida())
                .precioUnitario(request.precioUnitario())
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