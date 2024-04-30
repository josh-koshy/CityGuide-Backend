package dev.koshy.cityguide_backend;

import java.util.Map;
import java.util.concurrent.ExecutionException;

public interface UserServiceInterface {
    void updateFirestoreProfile(boolean isLogin, String uid, String userEmail) throws ExecutionException, InterruptedException;
    boolean checkUserIsActive(String uid) throws ExecutionException, InterruptedException;
    void updateUserProfile(String uid, Map<String, Object> profileData) throws ExecutionException, InterruptedException;
}

