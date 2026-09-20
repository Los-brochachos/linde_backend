package com.linde.linde_backend.entities.trabajador;

import com.linde.linde_backend.entities.usuario.Usuario;
import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDate;

@Entity
@Table(name = "trabajador")
@Inheritance(strategy = InheritanceType.JOINED)
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Trabajador {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer idTrabajador;

    @Column(nullable = false, length = 100)
    private String nombres;

    @Column(nullable = false, length = 100)
    private String apellidos;

    @Column(nullable = false, unique = true, length = 20)
    private String dni;

    @Column(length = 20)
    private String telefono;

    @Column(length = 150)
    private String direccion;

    private LocalDate fechaIngreso;

    @Column(nullable = false, length = 20)
    private String estado;

    @OneToOne(optional = false, fetch = FetchType.LAZY)
    @JoinColumn(
        name = "idUsuario",
        nullable = false,
        unique = true
    )
    private Usuario usuario;
}