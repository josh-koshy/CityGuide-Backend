package dev.koshy.cityguide_backend;

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
    Loggable textLogger = new SimpleLogger("log.txt");
    Loggable textErrorLogger = new ErrorLogger("log.txt");
    private static final Logger logger = LoggerFactory.getLogger(Controller.class);
    private final GooglePlacesService requestHandler;
    private final UserService userService;

    @Autowired  // Constructor injection for better practice
    public Controller(GooglePlacesService requestHandler, UserService userService) {
        this.requestHandler = requestHandler;
        this.userService = userService;
    }

    @GetMapping("/getNearbyPlaces")
    public ResponseEntity<?> getNearbyPlaces(
            @RequestParam double latitude, 
            @RequestParam double longitude, 
            @RequestParam int radius) {
        textLogger.log("/getNearbyPlaces called");
        try {
            return ResponseEntity.ok(requestHandler.getNearbyPlaces(latitude, longitude, radius));
        } catch (Exception e) {
            textErrorLogger.log("/getNearbyPlaces failed with error: " + e.getMessage());
            return ResponseEntity.badRequest().body("Failed to retrieve places: " + e.getMessage());
        }
    }

    @PostMapping("/login")
        public ResponseEntity<?> loginUser(@RequestBody Map<String, String> loginData) {
            String idToken = loginData.get("idToken");
            String userEmail;
            // long currentTime = System.currentTimeMillis() / 1000L;

        try {
                FirebaseToken decodedToken = FirebaseAuth.getInstance().verifyIdToken(idToken);
                String uid = decodedToken.getUid();
                userEmail = GetUserEmail.getEmailFromUid(uid);
                boolean isLogin = true;

                boolean isActive = userService.checkUserIsActive(uid);
                if (!isActive) {
                    userService.updateFirestoreProfile(isLogin, uid, userEmail);
                }

                Map<String, Object> response = new HashMap<>();
                response.put("uid", uid);
                response.put("isActive", isActive);
                response.put("message", "User status checked successfully.");
                textLogger.log("/login called with idToken: " + idToken);
                return ResponseEntity.ok(response);
            } catch (FirebaseAuthException e) {
                textErrorLogger.log("/login called with FirebaseAuthException error: " + e.getMessage());
                logger.error("Authentication error", e);
                return ResponseEntity.status(500).body("Authentication failed: " + e.getMessage());
            } catch (Exception e) {
                textErrorLogger.log("/login called with error: " + e.getMessage());
                logger.error("Failed to process login", e);
                return ResponseEntity.status(500).body("Server error: " + e.getMessage());
            }
        }
        @PostMapping("/updateProfile")
        public ResponseEntity<?> updateProfile(@RequestBody Map<String, Object> profileData) {
            textLogger.log("/updateProfile called");
            String uid = (String) profileData.get("uid");
            if (uid == null || uid.isEmpty()) {
                textErrorLogger.log("/updateProfile called with null or empty uid");
                return ResponseEntity.badRequest().body("{\"error\":\"UID is required.\"}");
            }
            profileData.remove("uid"); // Now remove uid after checking it's not null or empty
            try {
                userService.updateUserProfile(uid, profileData);
                return ResponseEntity.ok("{\"message\":\"Profile updated successfully.\"}");
            } catch (Exception e) {
                textErrorLogger.log("/updateProfile called with error: " + e.getMessage());
                logger.error("Failed to update user profile", e);
                return ResponseEntity.status(500).body("{\"error\":\"Failed to update profile: " + e.getMessage() + "\"}");
            }
        }
}