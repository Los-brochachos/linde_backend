package com.linde.linde_backend.entities.usuario;

import java.util.Collection;
import java.util.List;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import com.linde.linde_backend.utils.Estado;
import com.linde.linde_backend.utils.RolesEnum;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;


@Entity 
@Table(name = "usuario")
@Getter 
@Setter 
@NoArgsConstructor 
@AllArgsConstructor
@Builder 
public class Usuario implements UserDetails{

    @Id 
    @GeneratedValue (strategy = GenerationType.IDENTITY)
    private Integer idUsuario;
    

    @Column (nullable = false, unique = true, length = 100)
    private String correo;

    @Column (nullable = false, length = 255)
    private String contraseña;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false, length = 20)
    private Estado estado;

    @Enumerated(EnumType.STRING)
    private RolesEnum rol;


    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return List.of(new SimpleGrantedAuthority("ROLE_" + rol.name()));
    }

    @Override
    public String getUsername() {
        return this.correo;
    }
    
    @Override
    public String getPassword() {
        return contraseña;
    }

}
