package dev.koshy.cityguide_backend;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.UserRecord;

public class GetUserEmail {
    static Loggable logger = new SimpleLogger("log.txt");
    static Loggable errorLogger = new ErrorLogger("log.txt");

    public static String getEmailFromUid(String uid) {
        FirebaseAuth auth = FirebaseAuth.getInstance();
        String email;
        try {
            UserRecord userRecord = auth.getUser(uid);
            email = userRecord.getEmail();
            logger.log("getEmailFromUid called with " + email);
            System.out.println("Email: " + email);
        } catch (Exception e) {
            errorLogger.log("getEmailFromUid called with " + e.getMessage());
            e.printStackTrace();
            email = null;
        }
        return email;
    }
}
