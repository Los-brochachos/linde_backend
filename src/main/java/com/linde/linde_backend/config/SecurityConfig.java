package com.linde.linde_backend.config;

import java.util.Arrays;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
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
            .csrf(AbstractHttpConfigurer::disable)
            .cors(Customizer.withDefaults())
            .authorizeHttpRequests(auth -> auth
                // Públicos: no necesitan token
                .requestMatchers("/auth/**", "/info/**").permitAll()

                // Solo ADMIN
                .requestMatchers("/api/v1/admin/**").hasRole("ADMIN")
                .requestMatchers("/api/v1/trabajador/**").hasRole("ADMIN")

                // ADMIN y PROGRAMADOR
                .requestMatchers("/api/v1/programador/**")
                    .hasAnyRole("ADMIN", "PROGRAMADOR")

                // ADMIN y CONDUCTOR
                .requestMatchers("/api/v1/conductor/**")
                    .hasAnyRole("ADMIN", "CONDUCTOR")

                // ADMIN y TECNICO
                .requestMatchers("/api/v1/tecnico/**")
                    .hasAnyRole("ADMIN", "TECNICO")

                // ADMIN y CLIENTE
                .requestMatchers("/api/v1/cliente/**")
                    .hasAnyRole("ADMIN", "CLIENTE")

                // Todo lo demás necesita estar autenticado
                .anyRequest().authenticated()
        
            
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