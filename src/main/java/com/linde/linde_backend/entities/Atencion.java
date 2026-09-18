package com.linde.linde_backend.entities;

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
@Table (name="atencion")
@NoArgsConstructor 
@AllArgsConstructor 
public class Atencion {
    @Id 
    @GeneratedValue(strategy=GenerationType.IDENTITY)
    private Integer idAtencion;
    @Column (nullable = false)
    private LocalDateTime fechaInicioProgramada;
    @Column
    private LocalDateTime fechaFinProgramada;
    @Column
    private LocalDateTime fechaInicio;
    @Column
    private LocalDateTime fechaFin;
    @Column (nullable = false, length = 50)
    private String estado; 
    @Column (length = 250)
    private String observaciones;
    @ManyToOne(fetch=FetchType.LAZY)
    @JoinColumn (name ="idPedido",nullable = false)
    private Pedido pedido;
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn (name="idConductor", nullable = false)
    private Conductor conductor;
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn (name = "idCisterna", nullable = false)
    private Cisterna cisterna;
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn (name = "idProgramador", nullable = false)
    private Programador programador;
}
