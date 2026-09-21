package com.linde.linde_backend.entities.trabajador;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "programador")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Programador extends Trabajador {

    @Column(nullable = false, length = 100)
    private String area;

    @Column(length = 30)
    private String turno;
}