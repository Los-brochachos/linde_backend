package com.linde.linde_backend.entities.trabajador;

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
@Builder 
@Getter 
@Setter 
@Table (name="tecnico")
@NoArgsConstructor 
@AllArgsConstructor 
public class Tecnico {
    @Id 
    private Integer idTrabajador;
    @OneToOne (fetch= FetchType.LAZY)
    @MapsId 
    @JoinColumn (name = "idTrabajador")
    private Trabajador trabajador;
    @Column (nullable = false, length = 100)
    private String especialidad;
    @Column (nullable = false, length = 50)
    private String nivelTecnico;
    @Column (nullable = false, length = 100)
    private String certificacion;
}
