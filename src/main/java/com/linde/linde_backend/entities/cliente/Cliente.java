package com.linde.linde_backend.entities.cliente;

import com.linde.linde_backend.entities.usuario.Usuario;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
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
@Table (name = "cliente")
@AllArgsConstructor 
<<<<<<< HEAD:src/main/java/com/linde/linde_backend/entities/Cliente/cliente.java
@Builder 

public class cliente {
=======
@NoArgsConstructor 
public class Cliente {
>>>>>>> main:src/main/java/com/linde/linde_backend/entities/cliente/Cliente.java
    @Id 
    @GeneratedValue (strategy = GenerationType.IDENTITY)
    private Integer idCliente;
    @Column (nullable = false, length = 20)
    private String ruc;
    @Column (nullable = false, length = 100)
    private String razonSocial;
    @Column (length = 150)
    private String direccion;
    @Column (length = 20)
    private String telefono;
    @Column (length = 100)
    private String correo;
    @OneToOne (fetch = FetchType.LAZY)
    @JoinColumn (name = "idUsuario", nullable = false)
    private Usuario usuario;
}
