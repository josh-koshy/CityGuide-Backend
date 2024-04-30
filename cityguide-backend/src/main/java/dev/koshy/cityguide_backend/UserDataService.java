package dev.koshy.cityguide_backend;

import com.google.auth.oauth2.GoogleCredentials;
import com.google.cloud.firestore.Firestore;
import com.google.cloud.firestore.FirestoreOptions;
import org.springframework.core.io.ClassPathResource;

import java.io.IOException;

public abstract class UserDataService {
    protected final Firestore db;


    protected UserDataService() throws IOException {
        GoogleCredentials credentials = GoogleCredentials.fromStream(
                new ClassPathResource("cityguide-cis454-firebase-adminsdk-mjb2s-78ae161d25.json").getInputStream()
        );

        FirestoreOptions firestoreOptions = FirestoreOptions.newBuilder()
                .setCredentials(credentials)
                .setProjectId("cityguide-cis454")
                .build();

        this.db = firestoreOptions.getService();
    }

    protected void logError(String message, Exception e) {
        Loggable logger = new ErrorLogger("log.txt");
        logger.log(message + ": " + e.getMessage());
        System.err.println(message + ": " + e.getMessage());
    }
}

