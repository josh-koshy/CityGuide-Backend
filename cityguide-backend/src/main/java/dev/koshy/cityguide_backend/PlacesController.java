package dev.koshy.cityguide_backend;

import com.google.maps.model.PlacesSearchResponse;
import org.springframework.beans.factory.annotation.*;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
public class PlacesController {

    private final GeoService placesService;
    Loggable logger = new SimpleLogger("log.txt");
    Loggable errorLogger = new ErrorLogger("log.txt");
    public PlacesController(@Qualifier("googlePlacesService") GeoService placesService) {
        this.placesService = placesService;
    }

    @GetMapping("/places/nearby")
    public ResponseEntity<?> getNearbyPlaces(@RequestParam double latitude, @RequestParam double longitude, @RequestParam int radius) {
        try {
            PlacesSearchResponse response = placesService.getNearbyPlaces(latitude, longitude, radius);
            logger.log("getNearbyPlaces called with response: " + response);
            return ResponseEntity.ok(response);
        } catch (Exception e) {
            errorLogger.log("getNearbyPlaces called with exception: " + e.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Error fetching places: " + e.getMessage());
        }
    }
}

