package dev.koshy.cityguide_backend;

import com.google.maps.model.PlacesSearchResponse;
import org.springframework.stereotype.Component;

@Component
public class MockPlacesService implements GeoService {

    @Override
    public PlacesSearchResponse getNearbyPlaces(double latitude, double longitude, int radius) {
        // Return a mock response
        PlacesSearchResponse mockResponse = new PlacesSearchResponse();
        // Populate mock response with dummy data
        return mockResponse;
    }
}
