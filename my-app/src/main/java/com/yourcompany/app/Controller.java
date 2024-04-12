package com.yourcompany.app;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import com.google.maps.model.PlacesSearchResponse;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseToken;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;

@RestController
public class Controller {

    private static final Logger logger = LoggerFactory.getLogger(Controller.class);
    private final RequestHandler requestHandler;

    @Autowired  // Constructor injection for better practice
    public Controller(RequestHandler requestHandler) {
        this.requestHandler = requestHandler;
    }

    @GetMapping("/getNearbyPlaces")
    public PlacesSearchResponse getNearbyPlaces(
            @RequestParam double latitude, 
            @RequestParam double longitude, 
            @RequestParam int radius) {
        return requestHandler.getNearbyPlaces(latitude, longitude, radius);
    }

    @PostMapping("/secure/data")
    public ResponseEntity<String> getSecureData(@RequestBody String requestBody, @RequestHeader(value = "Authorization") String authorizationHeader) {
        try {
            String idToken = authorizationHeader.substring(7); // Remove "Bearer " prefix
            FirebaseToken decodedToken = FirebaseAuth.getInstance().verifyIdToken(idToken);
            String uid = decodedToken.getUid();  // Get UID from the decoded token
            
            // Log the token and UID
            logger.info("Received token: {}", idToken);
            logger.info("Decoded UID: {}", uid);

            // Returning the token and UID as a response
            return ResponseEntity.ok("Token: " + idToken + ", UID: " + uid);
        } catch (Exception e) {
            logger.error("Authentication failed", e);
            return ResponseEntity.status(500).body("Authentication failed: " + e.getMessage());
        }
    }
}
