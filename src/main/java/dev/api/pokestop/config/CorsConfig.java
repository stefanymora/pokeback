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

        // ✅ Permitir origen de tu frontend en Vercel
        config.setAllowedOrigins(List.of("https://pokestop-app.vercel.app"));

        // ✅ Permitir todos los métodos, incluyendo OPTIONS
        config.setAllowedMethods(List.of("GET", "POST", "PUT", "DELETE", "OPTIONS"));

        // ✅ Permitir todos los headers
        config.setAllowedHeaders(List.of("*"));

        // ✅ Permitir credenciales si algún día manejas JWT o cookies
        config.setAllowCredentials(true);

        // ✅ Configurar y registrar el filtro
        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
        source.registerCorsConfiguration("/", config);

        return new CorsFilter(source);
    }
}