package dev.koshy.cityguide_backend;

import org.springframework.context.annotation.*;

@Configuration
public class PlacesServiceConfig {

    @Bean
    @Profile("dev")
    public GeoService mockPlacesService() {
        return new MockPlacesService();
    }

    @Bean
    @Profile("prod")
    public GeoService googlePlacesService() {
        return new GooglePlacesService();
    }
}
