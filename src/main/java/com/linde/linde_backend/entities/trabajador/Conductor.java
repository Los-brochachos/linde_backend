package com.linde.linde_backend.entities.trabajador;

import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDate;

@Entity
@Table(name = "conductor")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Conductor extends Trabajador {

    @Column(nullable = false, length = 30)
    private String licenciaConducir;

    @Column(nullable = false, length = 30)
    private String categoriaLicencia;

    private LocalDate fechaVencimientoLicencia;
}