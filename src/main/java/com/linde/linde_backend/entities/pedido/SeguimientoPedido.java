package com.linde.linde_backend.entities.pedido;

import java.time.LocalDateTime;

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
@Table (name = "seguimientopedido")
@NoArgsConstructor 
@AllArgsConstructor 
public class SeguimientoPedido {
    @Id 
    @GeneratedValue (strategy = GenerationType.IDENTITY)
    private Integer idSeguimiento;
    @Column (nullable = false)
    private LocalDateTime fechaHora;
    @Column (nullable = false, length = 50)
    private String estado;
    @Column (length = 250)
    private String observacion;
    @ManyToOne (fetch = FetchType.LAZY)
    @JoinColumn (name = "idPedido", nullable = false)
    private Pedido pedido;
}
