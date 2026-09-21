package com.linde.linde_backend.entities.trabajador;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "tecnico")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Tecnico extends Trabajador {

    @Column(nullable = false, length = 100)
    private String especialidad;

    @Column(length = 50)
    private String nivelTecnico;

    @Column(length = 100)
    private String certificacion;
}