package pharmacy_inventory_management.pharmacy_inventory_management.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.cors.*;

import java.util.List;

@Configuration
public class CorsConfig {

    @Bean
    public CorsConfigurationSource corsConfigurationSource() {

        CorsConfiguration configuration = new CorsConfiguration();

        configuration.setAllowedOrigins(List.of(
    "https://pharmacy-frontend-l05wv5ys7-arunkotus-projects.vercel.app"
));

configuration.setAllowedMethods(List.of(
    "GET", "POST", "PUT", "DELETE", "OPTIONS"
));

configuration.setAllowedHeaders(List.of("*"));

configuration.setAllowCredentials(true);

        UrlBasedCorsConfigurationSource source =
                new UrlBasedCorsConfigurationSource();

        source.registerCorsConfiguration("/**", configuration);

        return source;
    }

}
