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

                .requestMatchers("/auth/**").permitAll()


                //USUARIOS  
                .requestMatchers(HttpMethod.GET, "/api/v1/usuarios/me")
                    .authenticated()

                .requestMatchers(HttpMethod.GET, "/api/v1/usuarios")
                    .hasAnyRole("ADMIN", "ANALISTA")

                .requestMatchers(HttpMethod.GET, "/api/v1/usuarios/*")
                    .hasAnyRole("ADMIN", "ANALISTA")

                .requestMatchers(HttpMethod.PUT, "/api/v1/usuarios/*")
                    .hasAnyRole("ADMIN", "CLIENTE", "PROGRAMADOR", "CONDUCTOR", "TECNICO")

                .requestMatchers(HttpMethod.DELETE, "/api/v1/usuarios/*")
                    .hasRole("ADMIN")

                // TRABAJADORES
                .requestMatchers(HttpMethod.GET, "/api/v1/trabajadores")
                    .hasAnyRole("ADMIN", "ANALISTA")

                .requestMatchers(HttpMethod.GET, "/api/v1/trabajadores/*")
                    .hasAnyRole("ADMIN", "ANALISTA")

                .requestMatchers(HttpMethod.POST, "/api/v1/trabajadores")
                    .hasRole("ADMIN")

                .requestMatchers(HttpMethod.PUT, "/api/v1/trabajadores/*")
                    .hasRole("ADMIN")

                .requestMatchers(HttpMethod.DELETE, "/api/v1/trabajadores/*")
                    .hasRole("ADMIN")



                // TÉCNICOS

                .requestMatchers(HttpMethod.GET, "/api/v1/tecnicos")
                    .hasAnyRole("ADMIN", "ANALISTA")

                .requestMatchers(HttpMethod.GET, "/api/v1/tecnicos/*")
                    .hasAnyRole("ADMIN", "ANALISTA", "TECNICO")

                .requestMatchers(HttpMethod.POST, "/api/v1/tecnicos/*")
                    .hasRole("ADMIN")

                .requestMatchers(HttpMethod.PUT, "/api/v1/tecnicos/*")
                    .hasRole("ADMIN")


                // PROGRAMADORES

                .requestMatchers(HttpMethod.GET, "/api/v1/programadores")
                    .hasAnyRole("ADMIN", "ANALISTA")

                .requestMatchers(HttpMethod.GET, "/api/v1/programadores/*")
                    .hasAnyRole("ADMIN", "ANALISTA", "PROGRAMADOR")

                .requestMatchers(HttpMethod.POST, "/api/v1/programadores/*")
                    .hasRole("ADMIN")

                .requestMatchers(HttpMethod.PUT, "/api/v1/programadores/*")
                    .hasRole("ADMIN")


                // CONDUCTORES

                .requestMatchers(HttpMethod.GET, "/api/v1/conductores")
                    .hasAnyRole("ADMIN", "ANALISTA")

                .requestMatchers(HttpMethod.GET, "/api/v1/conductores/*")
                    .hasAnyRole("ADMIN", "ANALISTA", "CONDUCTOR")

                .requestMatchers(HttpMethod.POST, "/api/v1/conductores/*")
                    .hasRole("ADMIN")

                .requestMatchers(HttpMethod.PUT, "/api/v1/conductores/*")
                    .hasRole("ADMIN")



                // DETALLES DE PEDIDO

                .requestMatchers(HttpMethod.POST, "/api/v1/pedidos/*/detalles")
                    .hasRole("CLIENTE")

                .requestMatchers(HttpMethod.GET,"/api/v1/pedidos/*/detalles")
                    .hasAnyRole("ADMIN", "ANALISTA", "CLIENTE")

                .requestMatchers(HttpMethod.PUT,"/api/v1/pedidos/*/detalles/*")
                    .hasRole("CLIENTE")

                

                // PEDIDOS

                .requestMatchers(HttpMethod.GET,"/api/v1/pedidos")
                    .hasAnyRole("ADMIN","ANALISTA","PROGRAMADOR","CONDUCTOR","TECNICO","CLIENTE")

                .requestMatchers(HttpMethod.GET,"/api/v1/pedidos/*")
                    .hasAnyRole("ADMIN","ANALISTA","PROGRAMADOR","CONDUCTOR","TECNICO","CLIENTE")

                .requestMatchers(HttpMethod.GET,"/api/v1/pedidos/*/seguimiento")
                    .hasAnyRole("ADMIN","ANALISTA","PROGRAMADOR","CONDUCTOR","TECNICO","CLIENTE")

                .requestMatchers(HttpMethod.POST,"/api/v1/pedidos")
                    .hasAnyRole("ADMIN","CLIENTE")

                .requestMatchers(HttpMethod.PATCH,"/api/v1/pedidos/*/estado")
                    .hasAnyRole("ADMIN","PROGRAMADOR", "CONDUCTOR")

                .requestMatchers(HttpMethod.PATCH,"/api/v1/pedidos/*/cancelar")
                    .hasAnyRole("ADMIN","CLIENTE")

                // PRODUCTOS

                .requestMatchers(HttpMethod.GET,"/api/v1/productos")
                    .hasAnyRole("ADMIN","ANALISTA","PROGRAMADOR","CONDUCTOR","TECNICO","CLIENTE")

                .requestMatchers(HttpMethod.GET, "/api/v1/productos/*")
                    .hasAnyRole("ADMIN","ANALISTA","PROGRAMADOR","CONDUCTOR","TECNICO","CLIENTE")

                .requestMatchers(HttpMethod.POST,"/api/v1/productos")
                    .hasRole("ADMIN")

                .requestMatchers(HttpMethod.PUT,"/api/v1/productos/*")
                    .hasRole("ADMIN")

                .requestMatchers(HttpMethod.DELETE,"/api/v1/productos/*")
                    .hasRole("ADMIN")
                
                
                // CLIENTES

                .requestMatchers(
                    HttpMethod.GET,
                    "/api/v1/cliente"
                )
                    .hasAnyRole("ADMIN", "ANALISTA")

                .requestMatchers(
                    HttpMethod.GET,
                    "/api/v1/cliente/*"
                )
                    .hasAnyRole("ADMIN", "ANALISTA", "CLIENTE")

                .requestMatchers(
                    HttpMethod.POST,
                    "/api/v1/cliente"
                )
                    .hasRole("ADMIN")

                .requestMatchers(
                    HttpMethod.DELETE,
                    "/api/v1/cliente/*"
                )
                    .hasRole("ADMIN")


                // CISTERNAS

                .requestMatchers(HttpMethod.GET,"/api/v1/cisternas")
                    .hasAnyRole("ADMIN","ANALISTA","PROGRAMADOR","CONDUCTOR","TECNICO")

                .requestMatchers(HttpMethod.GET,"/api/v1/cisternas/placa/*")
                    .hasAnyRole("ADMIN","ANALISTA","PROGRAMADOR","CONDUCTOR","TECNICO")

                .requestMatchers(HttpMethod.GET,"/api/v1/cisternas/nombre/*")
                    .hasAnyRole("ADMIN","ANALISTA","PROGRAMADOR","CONDUCTOR","TECNICO")

                .requestMatchers(HttpMethod.POST,"/api/v1/cisternas")
                    .hasRole("ADMIN")

                .requestMatchers(HttpMethod.PUT,"/api/v1/cisternas")   
                    .hasRole("ADMIN")

                .requestMatchers(HttpMethod.DELETE,"/api/v1/cisternas")
                    .hasRole("ADMIN")
                



                // FALLAS

                .requestMatchers(HttpMethod.GET,"/api/v1/falla")
                    .hasAnyRole("ADMIN","ANALISTA","PROGRAMADOR","CONDUCTOR","TECNICO")

                .requestMatchers(HttpMethod.GET,"/api/v1/falla/idCisterna/*")
                    .hasAnyRole("ADMIN","ANALISTA","PROGRAMADOR","CONDUCTOR","TECNICO")

                .requestMatchers(HttpMethod.POST,"/api/v1/falla")
                    .hasAnyRole("ADMIN", "TECNICO")

                .requestMatchers(HttpMethod.PUT,"/api/v1/falla/solucionar")
                    .hasAnyRole("ADMIN", "TECNICO")


                // HISTORIAL DE MANTENIMIENTO

                .requestMatchers(HttpMethod.GET,"/api/v1/historial")
                    .hasAnyRole("ADMIN","ANALISTA","PROGRAMADOR","CONDUCTOR","TECNICO")

                .requestMatchers(HttpMethod.GET,"/api/v1/historial/cisterna/*")
                    .hasAnyRole("ADMIN","ANALISTA","PROGRAMADOR","CONDUCTOR","TECNICO")

                .requestMatchers(HttpMethod.GET,"/api/v1/historial/nombre/*")
                    .hasAnyRole("ADMIN","ANALISTA","PROGRAMADOR","CONDUCTOR","TECNICO")

                .requestMatchers(HttpMethod.GET,"/api/v1/historial/fecha/*")
                    .hasAnyRole("ADMIN","ANALISTA","PROGRAMADOR","CONDUCTOR","TECNICO")

                .requestMatchers(HttpMethod.POST,"/api/v1/historial")
                    .hasAnyRole("ADMIN", "TECNICO")

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