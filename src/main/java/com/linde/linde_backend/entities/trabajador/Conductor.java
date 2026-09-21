package com.linde.linde_backend.entities.trabajador;

import java.time.LocalDate;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.MapsId;
import jakarta.persistence.OneToOne;
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
@Table (name = "conductor")
@NoArgsConstructor 
@AllArgsConstructor 
public class Conductor {
    @Id 
    private Integer idTrabajador;
    @OneToOne (fetch = FetchType.LAZY)
    @MapsId 
    @JoinColumn (name="idTrabajador")
    private Trabajador trabajador; 

    @Column (nullable = false, length = 30)
    private String licenciaConducir;
    @Column (nullable = false, length = 30)
    private String categoriaLicencia;
    @Column (nullable = false)
    private LocalDate fechaVencimientoLicencia;
}
