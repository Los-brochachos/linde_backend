package com.linde.linde_backend.entities.cisterna;

import java.math.BigDecimal;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
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
@Table (name = "cisterna")
@NoArgsConstructor 
@AllArgsConstructor 
public class Cisterna {
    @Id 
    @GeneratedValue (strategy = GenerationType.IDENTITY)
    private Integer idCisterna;
    @Column (nullable = false, length = 20)
    private String placa;
    @Column (nullable = false, length = 100, unique = true)
    private String nombre;
    @Column (nullable = false, precision = 10, scale = 2)
    private BigDecimal capacidad;
    @Column (nullable = false, length = 20)
    private String estado;

}
