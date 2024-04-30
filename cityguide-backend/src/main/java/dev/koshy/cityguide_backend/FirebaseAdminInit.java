package dev.koshy.cityguide_backend;

import com.google.auth.oauth2.GoogleCredentials;
import com.google.firebase.FirebaseApp;
import com.google.firebase.FirebaseOptions;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.ClassPathResource;

import javax.annotation.PostConstruct;
import java.io.IOException;



import org.springframework.core.io.Resource;

@Configuration
public class FirebaseAdminInit {
    @PostConstruct
    public void initialize() {
        try {
            ClassPathResource serviceAccount = new ClassPathResource("cityguide-cis454-firebase-adminsdk-mjb2s-78ae161d25.json");

            FirebaseOptions options = new FirebaseOptions.Builder()
                    .setCredentials(GoogleCredentials.fromStream(serviceAccount.getInputStream()))
                    .build();

            if (FirebaseApp.getApps().isEmpty()) {
                FirebaseApp.initializeApp(options);
            }
        } catch (IOException e) {
            throw new IllegalStateException("Failed to initialize Firebase", e);
        }
    }
}

