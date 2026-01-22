package com.example.demo.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod; // Ajout important
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
@EnableWebSecurity
public class SecurityConfig {

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        http
            .csrf(csrf -> csrf.disable())
            .cors(cors -> cors.disable()) // Désactive aussi le CORS pour le test
            .authorizeHttpRequests(auth -> auth
                .requestMatchers("/api/users/**").permitAll()
                // On autorise explicitement tous les GET sur events
                .requestMatchers(HttpMethod.GET, "/api/events/**").permitAll()
                // On autorise aussi les POST pour l'instant pour éviter les 403 à la création
                .requestMatchers(HttpMethod.POST, "/api/events/**").permitAll()
                .anyRequest().authenticated()
            );

        return http.build();
    }
}