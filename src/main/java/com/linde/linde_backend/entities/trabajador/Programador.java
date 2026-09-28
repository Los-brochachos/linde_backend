package com.linde.linde_backend.entities.trabajador;

import com.linde.linde_backend.utils.trabajador.NivelIngles;
import com.linde.linde_backend.utils.trabajador.Turno;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
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
@Table(name = "programador")
@Getter 
@Setter 
@Builder 
@NoArgsConstructor 
@AllArgsConstructor 
public class Programador {
    @Id
    private Integer idTrabajador;
    @OneToOne(fetch = FetchType.LAZY)
    @MapsId
    @JoinColumn(name = "idTrabajador")
    private Trabajador trabajador;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false, length = 20)
    private NivelIngles nivelIngles;

    @Enumerated (EnumType.STRING)
    @Column(nullable = false, length = 10)
    private Turno turno;
}
