package com.linde.linde_backend.entities.Cisterna;

import java.math.BigDecimal;
import java.time.LocalDateTime;

import com.linde.linde_backend.entities.trabajador.Tecnico;

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
@Table (name = "historial_mantenimiento")
@NoArgsConstructor 
@AllArgsConstructor 
public class HistorialMantenimiento {
    @Id 
    @GeneratedValue (strategy = GenerationType.IDENTITY)
    private Integer idHistorial;
    @Column (nullable = false)
    private LocalDateTime fecha;
    @Column (nullable = false, length = 250)
    private String descripcion;
    @Column (length = 250)
    private String resultado;
    @Column (length = 250)
    private String observaciones;
    @Column (precision = 10, scale = 2)
    private BigDecimal costo;
    @ManyToOne (fetch=FetchType.LAZY)
    @JoinColumn (name = "idCisterna", nullable = false)
    private Cisterna cisterna;
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn (name = "idFalla")
    private Falla falla;
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn (name = "idTecnico", nullable = false)
    private Tecnico tecnico;
}
