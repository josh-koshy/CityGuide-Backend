package dev.koshy.cityguide_backend;

import org.springframework.stereotype.Component;
import com.google.maps.GeoApiContext;
import com.google.maps.PlacesApi;
import com.google.maps.errors.ApiException;
import com.google.maps.model.LatLng;
import com.google.maps.model.PlacesSearchResponse;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseToken;

import java.io.IOException;

@Component
public class GooglePlacesService implements GeoService {
    Loggable logger = new SimpleLogger("log.txt");
    Loggable errorLogger = new ErrorLogger("log.txt");

    private final GeoApiContext context;

    public GooglePlacesService() {
        this.context = new GeoApiContext.Builder()
                .apiKey("AIzaSyC7SRSipgUCOxnWLsE6Qo0P6YbkB7JJpUM")
                .build();
    }

    @Override
    public PlacesSearchResponse getNearbyPlaces(double latitude, double longitude, int radius) throws ApiException, InterruptedException, IOException {
        LatLng location = new LatLng(latitude, longitude);
        logger.log("getNearbyPlaces called");
        return PlacesApi.nearbySearchQuery(context, location).radius(radius).await();
    }

    public String getSecureData(String idToken) throws Exception {
        FirebaseToken decodedToken = FirebaseAuth.getInstance().verifyIdToken(idToken);
        logger.log("getSecureData called");
        return "Access granted for UID: " + decodedToken.getUid();
    }
}
