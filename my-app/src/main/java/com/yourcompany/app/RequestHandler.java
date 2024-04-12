package com.yourcompany.app;

import org.springframework.stereotype.Component;
import com.google.maps.GeoApiContext;
import com.google.maps.PlacesApi;
import com.google.maps.errors.ApiException;
import com.google.maps.model.LatLng;
import com.google.maps.model.PlacesSearchResponse;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseToken;

@Component
public class RequestHandler {

    public PlacesSearchResponse getNearbyPlaces(double latitude, double longitude, int radius) {
        try {
            GeoApiContext context = new GeoApiContext.Builder()
                .apiKey("AIzaSyC7SRSipgUCOxnWLsE6Qo0P6YbkB7JJpUM")  // Replace "YOUR_API_KEY" with the actual API key
                .build();
            LatLng location = new LatLng(latitude, longitude);
            return PlacesApi.nearbySearchQuery(context, location).radius(radius).await();
        } catch (ApiException | InterruptedException | java.io.IOException e) {
            e.printStackTrace();
            return null;
        }
    }

    public String getSecureData(String idToken) {
        try {
            FirebaseToken decodedToken = FirebaseAuth.getInstance().verifyIdToken(idToken);
            return "Access granted for UID: " + decodedToken.getUid();
        } catch (Exception e) {
            e.printStackTrace();
            return "Authentication failed: " + e.getMessage();
        }
    }
}
