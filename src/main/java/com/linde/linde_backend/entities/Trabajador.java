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
@Table (name = "trabajador")
@NoArgsConstructor 
@AllArgsConstructor 
public class Trabajador {
    @Id 
    @GeneratedValue (strategy = GenerationType.IDENTITY)
    private Integer idTrabajador;
    @Column (nullable = false, length = 100)
    private String nombres;
    @Column (nullable = false, length = 100)
    private String apellidos;
    @Column (nullable = false, length = 20)
    private String dni;
    @Column (length = 20)
    private String telefono;
    @Column (length = 150)    
    private String direccion;
    @Column (nullable = false)
    private LocalDate fechaIngreso;
    @Column (nullable = false, length = 20)
    private String estado;
    @ManyToOne (fetch = FetchType.LAZY)
    @JoinColumn (name = "idUsuario", nullable = false)
    private Usuario usuario;
    
    @OneToOne (mappedBy = "trabajador", fetch = FetchType.LAZY)
    private Conductor conductor;
    @OneToOne (mappedBy = "trabajador", fetch = FetchType.LAZY)
    private Tecnico tecnico;
    @OneToOne (mappedBy = "trabajador", fetch = FetchType.LAZY)
    private Programador programador;
}
