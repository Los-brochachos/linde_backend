package com.linde.linde_backend.entities.Usuario;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity 
@Table(name = "Usuario")
@Getter 
@Setter 
@NoArgsConstructor 
@AllArgsConstructor
public class Usuario {

    @Id 
    @GeneratedValue (strategy = GenerationType.IDENTITY)
    private Integer idUsuario;
    

    @Column (nullable = false, length = 100)
    private String correo;

    @Column (nullable = false, length = 100)
    private String contraseña;

    @Column (nullable = false, length = 50)
    private String rol;

    @Column (nullable =false, length = 20)
    private String estado;

}
