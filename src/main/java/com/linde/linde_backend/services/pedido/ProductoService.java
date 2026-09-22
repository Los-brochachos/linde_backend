package com.linde.linde_backend.services.pedido;

import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.linde.linde_backend.dto.pedido.ProductoRequest;
import com.linde.linde_backend.dto.pedido.ProductoResponse;
import com.linde.linde_backend.entities.pedido.Producto;
import com.linde.linde_backend.mapper.pedido.ProductoMapper;
import com.linde.linde_backend.repositories.pedido.ProductoRepository;
import com.linde.linde_backend.utils.Estado;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class ProductoService {

    private final ProductoRepository productoRepository;
    private final ProductoMapper productoMapper;

    public List<ProductoResponse> listar() {

        return productoRepository.findAll()
                .stream()
                .map(productoMapper::toResponse)
                .toList();
    }

    public ProductoResponse buscarPorId(Integer id) {

        Producto producto = productoRepository.findById(id)
                .orElseThrow(() -> new RuntimeException(
                        "Producto no encontrado"
                ));

        return productoMapper.toResponse(producto);
    }

    @Transactional
    public ProductoResponse insertar(ProductoRequest request) {

        Producto producto = productoMapper.toEntity(request);

        producto = productoRepository.save(producto);

        return productoMapper.toResponse(producto);
    }

    @Transactional
    public ProductoResponse actualizar(
            Integer id,
            ProductoRequest request) {

        Producto producto = productoRepository.findById(id)
                .orElseThrow(() -> new RuntimeException(
                        "Producto no encontrado"
                ));

        producto.setNombre(request.nombre());
        producto.setTipoGas(request.tipoGas());
        producto.setUnidadMedida(request.unidadMedida());
        producto.setPrecioUnitario(request.precioUnitario());

        producto = productoRepository.save(producto);

        return productoMapper.toResponse(producto);
    }

    @Transactional
    public void desactivar(Integer id) {

        Producto producto = productoRepository.findById(id)
                .orElseThrow(() -> new RuntimeException(
                        "Producto no encontrado"
                ));

        if (producto.getEstado() == Estado.INACTIVO) {
            throw new RuntimeException(
                    "El producto ya está inactivo"
            );
        }

        producto.setEstado(Estado.INACTIVO);

        productoRepository.save(producto);
    }
}