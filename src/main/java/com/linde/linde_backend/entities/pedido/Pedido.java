package com.linde.linde_backend.entities.pedido;

import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDate;

@Entity
@Table(name = "pedido")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Pedido {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer idPedido;

    @Column(nullable = false)
    private LocalDate fechaRegistro;

    @Column(nullable = false, length = 50)
    private String estado;

    private LocalDate fechaEntregaEstimada;

    @Column(length = 20)
    private String prioridad;
}