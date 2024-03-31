package com.yourcompany.app;

public class MainClass {
    
    public static void main(String[] args) {
        // 初始化Firebase
        FirebaseAdminInit.initializeFirebase();
        
        // 假设你要查找的用户邮箱是"user@example.com"
        String userEmail = "user@example.com";
        
        // 获取用户UID
        String userUid = FirebaseAdminInit.getUserUidByEmail(userEmail);
        
        // 打印用户UID
        System.out.println("User UID for " + userEmail + ": " + userUid);
    }
}
