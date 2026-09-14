package org.example.loldemoserver.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;

import java.util.Arrays;
import java.util.List;

/**
 * Configuration class to import standard cors configurations accross multiple security config
 */
@Configuration
public class CorsConfig {

    private static final List<String> ALLOWED_ORIGINS = Arrays.asList("http://localhost:3000");
    private static final List<String> ALLOWED_METHODS = Arrays.asList("GET", "POST", "PUT", "OPTIONS");
    private static final List<String> ALLOWED_HEADERS = Arrays.asList("Content-Type", "Authorization", "Accept");
    private final static long PREFLIGHT_CACHE_MAX_AGE = 3600L;

    @Bean
    public UrlBasedCorsConfigurationSource getCorsConfigurationSource() {
        CorsConfiguration configuration = new CorsConfiguration();
        configuration.setAllowedOrigins(ALLOWED_ORIGINS);
        configuration.setAllowedMethods(ALLOWED_METHODS);
        configuration.setAllowedHeaders(ALLOWED_HEADERS);
        configuration.setMaxAge(PREFLIGHT_CACHE_MAX_AGE);
        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
        source.registerCorsConfiguration("/**", configuration);
        return source;
    }
}
