package com.yourcompany.app;

import com.google.maps.*;
import com.google.maps.errors.ApiException;
import com.google.maps.model.*;

import java.io.IOException;

public class RequestHandler {

    // Function to get user UID
    public static String getUserUid(String userEmail) {
        // You can implement your logic here to fetch user UID
        // For now, let's return a dummy UID
        return "dummyUid123";
    }

    // Function to call Google Places API to get nearby places
    public static PlacesSearchResponse getNearbyPlaces(String apiKey, double latitude, double longitude, int radius) {
        try {
            GeoApiContext context = new GeoApiContext.Builder()
                    .apiKey(apiKey)
                    .build();

            LatLng location = new LatLng(latitude, longitude);
            PlacesSearchResponse response = PlacesApi.nearbySearchQuery(context, location)
                    .radius(radius)
                    .await();

            return response;
        } catch (ApiException | InterruptedException | IOException e) {
            e.printStackTrace();
            return null;
        }
    }
}
