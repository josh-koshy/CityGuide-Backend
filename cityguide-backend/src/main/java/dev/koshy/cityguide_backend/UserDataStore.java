package dev.koshy.cityguide_backend;

import java.util.Map;

public interface UserDataStore {
    void updateUserProfile(String uid, Map<String, Object> profileData) throws Exception;
    boolean checkUserIsActive(String uid) throws Exception;

}
