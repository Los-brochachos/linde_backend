package com.linde.linde_backend.entities.Cliente;

import com.linde.linde_backend.entities.Usuario;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
@Entity 
@Table (name = "Cliente")
@NoArgsConstructor 
@Getter 
@Setter 
@AllArgsConstructor 


public class cliente {
    @Id 
    @GeneratedValue (strategy = GenerationType.IDENTITY)
    private Integer id ;

    @Column (nullable = false, length = 20)
    private String ruc;
    @Column (nullable = false, length = 100)
    private  String rsocial;
    @Column (nullable = false , length = 150)
    private String direccion;
    @Column (nullable = false, length = 20)
    private String telefono;
    @Column (nullable = false, length = 100)
    private String correo ;
    
    @ManyToOne 
    @JoinColumn (name = "idUsuario",nullable = false)
    private Usuario usuario;
}
