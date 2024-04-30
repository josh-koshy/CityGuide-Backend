package dev.koshy.cityguide_backend;

import com.google.maps.errors.ApiException;
import com.google.maps.model.PlacesSearchResponse;

import java.io.IOException;

public interface GeoService {
    PlacesSearchResponse getNearbyPlaces(double latitude, double longitude, int radius) throws ApiException, InterruptedException, IOException;
}
