package com.linde.linde_backend.entities.pedido;

import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;

@Entity
@Table(name = "seguimientopedido")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class SeguimientoPedido {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer idSeguimiento;

    @Column(nullable = false)
    private LocalDateTime fechaHora;

    @Column(nullable = false, length = 50)
    private String estado;

    @Column(length = 250)
    private String observacion;
}