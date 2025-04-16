package com.example.securityproject.config.securityConfig;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;
import org.springframework.web.filter.CorsFilter;

@Configuration
public class CorsConfig {

    @Bean
    public CorsFilter corsFilter() {
        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
        CorsConfiguration config = new CorsConfiguration();
        
        // Разрешаем запросы с любого источника
        config.addAllowedOrigin("*");
        // Разрешаем все методы (GET, POST, PUT, DELETE и т.д.)
        config.addAllowedMethod("*");
        // Разрешаем все заголовки
        config.addAllowedHeader("*");
        // Разрешаем передачу учетных данных
        config.setAllowCredentials(true);
        
        source.registerCorsConfiguration("/**", config);
        return new CorsFilter(source);
    }
} 