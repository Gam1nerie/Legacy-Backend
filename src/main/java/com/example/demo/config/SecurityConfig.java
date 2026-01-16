package com.example.demo.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
@EnableWebSecurity
public class SecurityConfig {

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        http
            // 1. Désactive le CSRF (nécessaire pour que Retrofit puisse envoyer des POST)
            .csrf(csrf -> csrf.disable())
            
            // 2. Configure les autorisations
            .authorizeHttpRequests(auth -> auth
                // On autorise tout ce qui commence par /api/users/ sans authentification
                .requestMatchers("/api/users/**").permitAll()
                // Le reste nécessite d'être connecté (si tu ajoutes d'autres fonctions plus tard)
                .anyRequest().authenticated()
            );

        return http.build();
    }
}