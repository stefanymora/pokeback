package dev.api.pokestop.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;
import org.springframework.web.filter.CorsFilter;

import java.util.List;

@Configuration
public class CorsConfig {

    @Bean
    public CorsFilter corsFilter() {
        CorsConfiguration config = new CorsConfiguration();

        // ✅ Permitir origen de tu frontend en Vercel y local
        config.setAllowedOrigins(List.of(
                "http://localhost:3000",
                "https://pokestop-app.vercel.app"
        ));

        // ✅ Permitir todos los métodos
        config.setAllowedMethods(List.of("GET", "POST", "PUT", "DELETE", "OPTIONS"));

        // ✅ Permitir todos los headers
        config.setAllowedHeaders(List.of("*"));

        // ✅ Permitir credenciales
        config.setAllowCredentials(true);

        // ✅ Registrar para TODOS los endpoints
        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
        source.registerCorsConfiguration("/**", config);

        return new CorsFilter(source);
    }
}
