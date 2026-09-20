package com.linde.linde_backend.entities.Atencion;

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
@Table (name = "alerta")
@NoArgsConstructor 
@AllArgsConstructor 
public class Alerta {
    @Id 
    @GeneratedValue (strategy = GenerationType.IDENTITY)
    private Integer idAlerta;
    @Column (nullable = false, length = 50)
    private String tipo;
    @Column (nullable = false, length = 250)
    private String mensaje;
    @Column (nullable = false)
    private LocalDateTime fechaHora;
    @Column (nullable = false, length = 50)
    private String estado;
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn (name="idAtencion",nullable = false)
    private Atencion atencion;
}
