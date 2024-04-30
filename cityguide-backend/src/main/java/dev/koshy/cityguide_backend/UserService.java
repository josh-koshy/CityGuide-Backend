package dev.koshy.cityguide_backend;

import com.google.cloud.firestore.DocumentReference;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.ExecutionException;
import java.io.IOException;

@Service
public class UserService extends UserDataService implements UserServiceInterface {
    public UserService() throws IOException {
        super();
    }

    public void updateFirestoreProfile(boolean isLogin, String uid, String userEmail) throws ExecutionException, InterruptedException {
        if (isLogin) {
            DocumentReference docRef = db.collection("users").document(uid);
            if (!docRef.get().get().exists()) {
                Map<String, Object> newUser = new HashMap<>();
                newUser.put("uid", uid);
                newUser.put("email", userEmail);
                newUser.put("isActive", false);
                newUser.put("createdAt", com.google.cloud.Timestamp.now());
                docRef.set(newUser).get();
            } else {
                Map<String, Object> existingUserUpdateMap = new HashMap<>();
                existingUserUpdateMap.put("lastLoginTime", com.google.cloud.Timestamp.now());
                docRef.update(existingUserUpdateMap).get();
            }
        }
    }

    public boolean checkUserIsActive(String uid) throws ExecutionException, InterruptedException {
        DocumentReference docRef = db.collection("users").document(uid);
        if (docRef.get().get().exists()) {
            Boolean isActive = docRef.get().get().getBoolean("isActive");
            return isActive != null && isActive;
        } else {
            return false;
        }
    }
    public void updateUserProfile(String uid, Map<String, Object> profileData) throws ExecutionException, InterruptedException {
        DocumentReference docRef = db.collection("users").document(uid);
        docRef.update(profileData).get();  // This will update existing fields or add new fields
    }
}
