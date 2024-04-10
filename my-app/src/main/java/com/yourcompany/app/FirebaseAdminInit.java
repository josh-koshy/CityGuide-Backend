package com.yourcompany.app;

import com.google.auth.oauth2.GoogleCredentials;
import com.google.firebase.FirebaseApp;
import com.google.firebase.FirebaseOptions;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.UserRecord;

import java.io.IOException;
import java.io.InputStream;

public class FirebaseAdminInit {

    // Method to initialize Firebase
    public static void initializeFirebase() {
        try {
            // Load the Firebase config file from the resources directory
            InputStream serviceAccount = FirebaseAdminInit.class.getClassLoader().getResourceAsStream("city-guide-app-d48a9-firebase-adminsdk-6yu7j-b007994a73.json");
            FirebaseOptions options = new FirebaseOptions.Builder()
                    .setCredentials(GoogleCredentials.fromStream(serviceAccount))
                    .build();

            // Initialize the default FirebaseApp instance
            FirebaseApp.initializeApp(options);
        } catch (IOException e) {
            System.err.println("Failed to initialize Firebase: " + e.getMessage());
            e.printStackTrace();
        }
    }

    // Method to get a user's UID by their email
    public static String getUserUidByEmail(String email) {
        try {
            UserRecord userRecord = FirebaseAuth.getInstance().getUserByEmail(email);
            return userRecord.getUid();
        } catch (Exception e) {
            System.err.println("Failed to get user UID by email: " + e.getMessage());
            e.printStackTrace();
            return "Failed to get user UID.";
        }
    }
}
