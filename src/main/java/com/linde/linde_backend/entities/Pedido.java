package com.linde.linde_backend.entities;

import java.time.LocalDate;

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
@Table (name="pedido")
@NoArgsConstructor 
@AllArgsConstructor 
public class Pedido {
    @Id 
    @GeneratedValue (strategy= GenerationType.IDENTITY)
    private Integer idPedido;
    @Column (nullable = false)
    private LocalDate fechaRegistro;
    @Column (nullable = false, length = 50)
    private String estado;
    @Column
    private LocalDate fechaEntregaEstimada;
    @Column (length = 20)
    private String prioridad; 
    @ManyToOne (fetch= FetchType.LAZY)
    @JoinColumn (name = "idCliente", nullable = false)
    private Cliente cliente;
}
