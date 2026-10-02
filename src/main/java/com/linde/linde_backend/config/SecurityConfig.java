package com.linde.linde_backend.config;

import java.util.Arrays;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;

import com.linde.linde_backend.components.JwtAuthFilter;

import lombok.RequiredArgsConstructor;

@Configuration
@EnableWebSecurity 
@RequiredArgsConstructor 
 
public class SecurityConfig {
    private final JwtAuthFilter filter;

    @Bean
    AuthenticationManager authenticationManager(AuthenticationConfiguration configuration) throws Exception {
        return configuration.getAuthenticationManager();
    }

    @Bean
    SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http
            .csrf(csrf -> csrf.disable())
            .cors(Customizer.withDefaults())
            .authorizeHttpRequests(auth -> auth

                //ENDPOINTS PUBLICOS - AUTENTICACIÓN
                .requestMatchers(HttpMethod.POST, "/auth/login").permitAll()
                .requestMatchers(HttpMethod.POST, "/auth/refresh-token").permitAll()
                .requestMatchers("/prueba/**").permitAll()


                // ========================================================
                // USUARIOS
                // Consultar mi usuario
                .requestMatchers(HttpMethod.GET, "/api/v1/usuarios/me")
                    .authenticated()
                // Actualizar mi propio correo
                .requestMatchers(HttpMethod.PATCH, "/api/v1/usuarios/me")
                    .authenticated()
                // Listar usuarios
                .requestMatchers(HttpMethod.GET, "/api/v1/usuarios")
                    .hasAnyRole("ADMIN", "ANALISTA")
                // Consultar usuario por ID
                .requestMatchers(HttpMethod.GET, "/api/v1/usuarios/*")
                    .hasAnyRole("ADMIN", "ANALISTA")
                // Actualizar usuario por ID
                .requestMatchers(HttpMethod.PATCH, "/api/v1/usuarios/*")
                    .hasRole("ADMIN")
                // Desactivar usuario
                .requestMatchers(HttpMethod.DELETE, "/api/v1/usuarios/*")
                    .hasRole("ADMIN")
                // =========================================================
                
                
                
                // =========================================================
                // CONDUCTORES
                // Listar todos los conductores
                .requestMatchers(HttpMethod.GET, "/api/v1/conductores/me")
                    .hasRole("CONDUCTOR")

                .requestMatchers(HttpMethod.GET, "/api/v1/conductores")
                    .hasAnyRole("ADMIN", "ANALISTA")

                // Listar conductores activos
                .requestMatchers(HttpMethod.GET, "/api/v1/conductores/activos")
                    .hasAnyRole("ADMIN", "ANALISTA", "PROGRAMADOR")

                // Buscar conductor por ID
                .requestMatchers(HttpMethod.GET, "/api/v1/conductores/*")
                    .hasAnyRole("ADMIN", "ANALISTA")

                // Buscar conductor activo por ID
                .requestMatchers(HttpMethod.GET, "/api/v1/conductores/*/activo")
                    .hasAnyRole("ADMIN", "ANALISTA", "PROGRAMADOR")

                // Crear conductor
                .requestMatchers(HttpMethod.POST, "/api/v1/conductores")
                    .hasRole("ADMIN")

                // Actualizar conductor
                .requestMatchers(HttpMethod.PUT, "/api/v1/conductores/*")
                    .hasRole("ADMIN")

                // Desactivar conductor
                .requestMatchers(HttpMethod.DELETE, "/api/v1/conductores/*")
                    .hasRole("ADMIN")          
                // =========================================================
                

                // =========================================================
                // PROGRAMADORES
                // Obtener mis propios datos
                .requestMatchers(HttpMethod.GET, "/api/v1/programadores/me")
                    .hasRole("PROGRAMADOR")

                // Listar todos los programadores
                .requestMatchers(HttpMethod.GET, "/api/v1/programadores")
                    .hasAnyRole("ADMIN", "ANALISTA")

                // Listar programadores activos
                .requestMatchers(HttpMethod.GET, "/api/v1/programadores/activos")
                    .hasAnyRole("ADMIN", "ANALISTA")

                // Buscar programador por ID
                .requestMatchers(HttpMethod.GET, "/api/v1/programadores/*")
                    .hasAnyRole("ADMIN", "ANALISTA")

                // Buscar programador activo por ID
                .requestMatchers(HttpMethod.GET, "/api/v1/programadores/*/activo")
                    .hasAnyRole("ADMIN", "ANALISTA")

                // Crear programador
                .requestMatchers(HttpMethod.POST, "/api/v1/programadores")
                    .hasRole("ADMIN")

                // Actualizar programador
                .requestMatchers(HttpMethod.PUT, "/api/v1/programadores/*")
                    .hasRole("ADMIN")

                // Desactivar programador
                .requestMatchers(HttpMethod.DELETE, "/api/v1/programadores/*")
                    .hasRole("ADMIN")

                // =========================================================
                                

                // =========================================================
                // TÉCNICOS
                // Obtener mis propios datos
                .requestMatchers(HttpMethod.GET, "/api/v1/tecnicos/me")
                    .hasRole("TECNICO")

                // Listar todos los técnicos
                .requestMatchers(HttpMethod.GET, "/api/v1/tecnicos")
                    .hasAnyRole("ADMIN", "ANALISTA")

                // Listar técnicos activos
                .requestMatchers(HttpMethod.GET, "/api/v1/tecnicos/activos")
                    .hasAnyRole("ADMIN", "ANALISTA")

                // Buscar técnico por ID
                .requestMatchers(HttpMethod.GET, "/api/v1/tecnicos/*")
                    .hasAnyRole("ADMIN", "ANALISTA")

                // Buscar técnico activo por ID
                .requestMatchers(HttpMethod.GET, "/api/v1/tecnicos/*/activo")
                    .hasAnyRole("ADMIN", "ANALISTA")

                // Crear técnico
                .requestMatchers(HttpMethod.POST, "/api/v1/tecnicos")
                    .hasRole("ADMIN")

                // Actualizar técnico
                .requestMatchers(HttpMethod.PUT, "/api/v1/tecnicos/*")
                    .hasRole("ADMIN")

                // Desactivar técnico
                .requestMatchers(HttpMethod.DELETE, "/api/v1/tecnicos/*")
                    .hasRole("ADMIN")
                // =========================================================
            
                // =========================================================
                // TRABAJADORES

                .requestMatchers(HttpMethod.GET, "/api/v1/trabajadores")
                    .hasAnyRole("ADMIN", "ANALISTA")

                .requestMatchers(HttpMethod.GET, "/api/v1/trabajadores/activos")
                    .hasAnyRole("ADMIN", "ANALISTA")

                .requestMatchers(HttpMethod.GET, "/api/v1/trabajadores/dni/*/activo")
                    .hasAnyRole("ADMIN", "ANALISTA")

                .requestMatchers(HttpMethod.GET, "/api/v1/trabajadores/dni/*")
                    .hasAnyRole("ADMIN", "ANALISTA")

                .requestMatchers(HttpMethod.GET, "/api/v1/trabajadores/*/activo")
                    .hasAnyRole("ADMIN", "ANALISTA")

                .requestMatchers(HttpMethod.GET, "/api/v1/trabajadores/*")
                    .hasAnyRole("ADMIN", "ANALISTA")
                // =========================================================




                // =========================================================
                // CLIENTES
                // Registrar cliente
                .requestMatchers(HttpMethod.POST, "/api/v1/clientes/register")
                    .permitAll()

                // Obtener mis propios datos
                .requestMatchers(HttpMethod.GET, "/api/v1/clientes/me")
                    .hasRole("CLIENTE")

                // Actualizar mis propios datos
                .requestMatchers(HttpMethod.PATCH, "/api/v1/clientes/me")
                    .hasRole("CLIENTE")

                // Listar todos los clientes
                .requestMatchers(HttpMethod.GET, "/api/v1/clientes")
                    .hasAnyRole("ADMIN", "ANALISTA")

                // Listar clientes activos
                .requestMatchers(HttpMethod.GET, "/api/v1/clientes/activos")
                    .hasAnyRole("ADMIN", "ANALISTA")

                // Buscar cliente por ID
                .requestMatchers(HttpMethod.GET, "/api/v1/clientes/*")
                    .hasAnyRole("ADMIN", "ANALISTA")

                // Buscar cliente activo por ID
                .requestMatchers(HttpMethod.GET, "/api/v1/clientes/*/activo")
                    .hasAnyRole("ADMIN", "ANALISTA")

                // Actualizar cliente por ID
                .requestMatchers(HttpMethod.PUT, "/api/v1/clientes/*")
                    .hasRole("ADMIN")

                // Desactivar cliente
                .requestMatchers(HttpMethod.DELETE, "/api/v1/clientes/*")
                    .hasRole("ADMIN")
                // =========================================================



                // =========================================================
                // PEDIDOS 
                // CLIENTE: consultar sus propios pedidos
                .requestMatchers(HttpMethod.GET, "/api/v1/pedidos/mis-pedidos")
                    .hasRole("CLIENTE")

                .requestMatchers(HttpMethod.GET, "/api/v1/pedidos/mis-pedidos/*")
                    .hasRole("CLIENTE")

                // CLIENTE: registrar un pedido
                .requestMatchers(HttpMethod.POST, "/api/v1/pedidos")
                    .hasRole("CLIENTE")

                // ADMIN / ANALISTA / PROGRAMADOR: consultar todos los pedidos
                .requestMatchers(HttpMethod.GET, "/api/v1/pedidos")
                    .hasAnyRole("ADMIN", "ANALISTA", "PROGRAMADOR")

                // ADMIN / ANALISTA / PROGRAMADOR: consultar un pedido específico
                .requestMatchers(HttpMethod.GET, "/api/v1/pedidos/*")
                    .hasAnyRole("ADMIN", "ANALISTA", "PROGRAMADOR")

                // PROGRAMADOR: cambiar estado
                .requestMatchers(HttpMethod.PATCH, "/api/v1/pedidos/*/estado")
                    .hasRole("PROGRAMADOR")

                // ADMIN / PROGRAMADOR: cancelar pedido
                .requestMatchers(HttpMethod.PATCH, "/api/v1/pedidos/*/cancelar")
                    .hasAnyRole("ADMIN", "PROGRAMADOR")
                // =========================================================



                // =========================================================
                // DETALLE PEDIDO
                // CLIENTE: ver los detalles activos de su propio pedido
                .requestMatchers(HttpMethod.GET,"/api/v1/pedidos/*/detalles")
                    .hasRole("CLIENTE")

                // ADMIN / ANALISTA / PROGRAMADOR:
                // ver los detalles activos de cualquier pedido
                .requestMatchers(HttpMethod.GET,"/api/v1/pedidos/*/detalles/activos")
                    .hasAnyRole("ADMIN", "ANALISTA", "PROGRAMADOR")

                // ADMIN / ANALISTA / PROGRAMADOR:
                // ver todos los detalles, incluidos los inactivos
                .requestMatchers(HttpMethod.GET,"/api/v1/pedidos/*/detalles/todos")
                    .hasAnyRole("ADMIN", "ANALISTA", "PROGRAMADOR")

                // CLIENTE: agregar un detalle a su propio pedido
                .requestMatchers(HttpMethod.POST,"/api/v1/pedidos/*/detalles")
                    .hasRole("CLIENTE")

                // CLIENTE: aumentar cantidad de un detalle de su propio pedido
                .requestMatchers(HttpMethod.PATCH,"/api/v1/pedidos/*/detalles/*/cantidad")
                    .hasRole("CLIENTE")

                // CLIENTE: cancelar un detalle de su propio pedido
                .requestMatchers(HttpMethod.PATCH,"/api/v1/pedidos/*/detalles/*/cancelar")
                    .hasRole("CLIENTE")
                // =========================================================


                // =========================================================
                // PRODUCTO
                // ADMIN / ANALISTA:
                // listar todos los productos
                .requestMatchers(HttpMethod.GET,"/api/v1/productos")
                    .hasAnyRole("ADMIN", "ANALISTA")

                // ADMIN / ANALISTA / PROGRAMADOR / CLIENTE:
                // listar productos activos disponibles
                .requestMatchers(HttpMethod.GET,"/api/v1/productos/activos")
                    .hasAnyRole("ADMIN", "ANALISTA", "PROGRAMADOR", "CLIENTE")

                // ADMIN / ANALISTA:
                // buscar producto por ID
                .requestMatchers(HttpMethod.GET,"/api/v1/productos/*")
                    .hasAnyRole("ADMIN", "ANALISTA")

                // ADMIN / ANALISTA / PROGRAMADOR / CLIENTE:
                // buscar producto activo por ID
                .requestMatchers(HttpMethod.GET,"/api/v1/productos/*/activo")
                    .hasAnyRole("ADMIN", "ANALISTA", "PROGRAMADOR", "CLIENTE")

                // ADMIN:
                // crear producto
                .requestMatchers(HttpMethod.POST,"/api/v1/productos")
                    .hasRole("ADMIN")

                // ADMIN:
                // actualizar producto
                .requestMatchers(HttpMethod.PUT,"/api/v1/productos/*")
                    .hasRole("ADMIN")

                // ADMIN:
                // desactivar producto
                .requestMatchers(HttpMethod.DELETE,"/api/v1/productos/*")
                    .hasRole("ADMIN")
                // =========================================================

       

                // =========================================================
                // SEGUIMIENTO PEDIDO
                // CLIENTE: consultar seguimiento de su propio pedido
                .requestMatchers(HttpMethod.GET,"/api/v1/pedidos/*/seguimiento")
                    .hasRole("CLIENTE")

                // ADMIN / ANALISTA / PROGRAMADOR:
                // consultar seguimiento de cualquier pedido
                .requestMatchers(HttpMethod.GET,"/api/v1/pedidos/*/seguimiento/todos")
                    .hasAnyRole("ADMIN", "ANALISTA", "PROGRAMADOR")
                // =========================================================
                                
                .anyRequest()
                    .authenticated()
            )
            .sessionManagement(sess -> sess.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
            .addFilterBefore(filter, UsernamePasswordAuthenticationFilter.class);

        return http.build();
    }

    @Bean
    PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    UrlBasedCorsConfigurationSource corsConfigurationSource() {
        CorsConfiguration configuration = new CorsConfiguration();
        configuration.setAllowedOrigins(Arrays.asList("http://127.0.0.1:3000","http://localhost:4200","http://localhost:5173"));
        configuration.setAllowedMethods(Arrays.asList("GET", "POST", "PUT", "DELETE", "OPTIONS"));
        configuration.setAllowedHeaders(Arrays.asList("*"));
        configuration.setAllowCredentials(true);
        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
        source.registerCorsConfiguration("/**", configuration);
        return source;
    }
}