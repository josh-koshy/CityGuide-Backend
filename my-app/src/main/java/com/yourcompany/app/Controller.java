package com.yourcompany.app;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseToken;
import com.google.firebase.auth.FirebaseAuthException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;

import java.util.HashMap;
import java.util.Map;

@RestController
public class Controller {

    private static final Logger logger = LoggerFactory.getLogger(Controller.class);
    private final RequestHandler requestHandler;
    private final UserService userService;

    @Autowired  // Constructor injection for better practice
    public Controller(RequestHandler requestHandler, UserService userService) {
        this.requestHandler = requestHandler;
        this.userService = userService;
    }

    @GetMapping("/getNearbyPlaces")
    public ResponseEntity<?> getNearbyPlaces(
            @RequestParam double latitude, 
            @RequestParam double longitude, 
            @RequestParam int radius) {
        try {
            return ResponseEntity.ok(requestHandler.getNearbyPlaces(latitude, longitude, radius));
        } catch (Exception e) {
            return ResponseEntity.badRequest().body("Failed to retrieve places: " + e.getMessage());
        }
    }

    @PostMapping("/login")
        public ResponseEntity<?> loginUser(@RequestBody Map<String, String> loginData) {
            String idToken = loginData.get("idToken");

            try {
                FirebaseToken decodedToken = FirebaseAuth.getInstance().verifyIdToken(idToken);
                String uid = decodedToken.getUid();

                boolean isActive = userService.checkUserIsActive(uid);
                if (!isActive) {
                    userService.checkOrCreateUser(uid);
                }

                Map<String, Object> response = new HashMap<>();
                response.put("uid", uid);
                response.put("isActive", isActive);
                response.put("message", "User status checked successfully.");

                return ResponseEntity.ok(response);
            } catch (FirebaseAuthException e) {
                logger.error("Authentication error", e);
                return ResponseEntity.status(500).body("Authentication failed: " + e.getMessage());
            } catch (Exception e) {
                logger.error("Failed to process login", e);
                return ResponseEntity.status(500).body("Server error: " + e.getMessage());
            }
        }
        @PostMapping("/updateProfile")
        public ResponseEntity<?> updateProfile(@RequestBody Map<String, Object> profileData) {
        String uid = profileData.remove("uid").toString(); // Ensure UID is passed from the client

        try {
            userService.updateUserProfile(uid, profileData);
            return ResponseEntity.ok("Profile updated successfully.");
        } catch (Exception e) {
            logger.error("Failed to update user profile", e);
            return ResponseEntity.status(500).body("Failed to update profile: " + e.getMessage());
        }
    }
}