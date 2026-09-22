package com.linde.linde_backend.entities.pedido;

import java.math.BigDecimal;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity 
@Getter 
@Setter 
@Builder 
@Table (name = "detalle_pedido")
@NoArgsConstructor 
@AllArgsConstructor 
public class DetallePedido {
    @Id 
    @GeneratedValue (strategy = GenerationType.IDENTITY)
    private Integer idDetalle;

    @Column (nullable = false, precision = 10, scale = 2)
    private BigDecimal cantidad;

    @Column (nullable = false, precision = 10, scale = 2)
    private BigDecimal precioUnitario;

    @ManyToOne (fetch = FetchType.LAZY)
    @JoinColumn (name="idPedido", nullable = false)
    private Pedido pedido;
    
    @ManyToOne (fetch = FetchType.LAZY)
    @JoinColumn (name = "idProducto", nullable = false)
    private Producto producto;
}
