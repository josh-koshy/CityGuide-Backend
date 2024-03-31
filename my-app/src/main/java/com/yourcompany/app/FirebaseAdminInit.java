package com.yourcompany.app;
import com.google.auth.oauth2.GoogleCredentials;
import com.google.firebase.FirebaseApp;
import com.google.firebase.FirebaseOptions;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.UserRecord;

import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;

public class FirebaseAdminInit {

    public static void initializeFirebase() {
        try {
            // Load the Firebase config file from the resources directory
            InputStream serviceAccount = FirebaseAdminInit.class.getClassLoader().getResourceAsStream("city-guide-app-d48a9-firebase-adminsdk-6yu7j-b007994a73.json");
    
            if (serviceAccount == null) {
                throw new FileNotFoundException("Firebase config file not found.");
            }
    
            FirebaseOptions options = new FirebaseOptions.Builder()
                    .setCredentials(GoogleCredentials.fromStream(serviceAccount))
                    .build();
    
            FirebaseApp.initializeApp(options);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    

    public static String getUserUidByEmail(String email) {
        try {
            UserRecord userRecord = FirebaseAuth.getInstance().getUserByEmail(email);
            return userRecord.getUid();
        } catch (Exception e) {
            e.printStackTrace();
            return "Failed to get user UID.";
        }
    }

    public static void startServer(int port) {
        try (ServerSocket serverSocket = new ServerSocket(port)) {
            System.out.println("Server is listening on port " + port);
            
            while (true) {
                try (Socket clientSocket = serverSocket.accept()) {
                    PrintWriter out = new PrintWriter(clientSocket.getOutputStream(), true);
                    out.println("Hello, you are connected to FirebaseAdminInit server!");
                    // 这里可以根据需要处理来自客户端的请求
                } catch (IOException e) {
                    System.out.println("Exception caught when trying to listen on port " + port + " or listening for a connection");
                    System.out.println(e.getMessage());
                }
            }
        } catch (IOException e) {
            System.out.println("Could not start server on port " + port);
            e.printStackTrace();
        }
    }

    public static void main(String[] args) {
        initializeFirebase();
        String userUid = getUserUidByEmail("aaa@example.com"); // Use the actual user email here
        System.out.println("User UID: " + userUid);
        
        // 启动监听9090端口的服务器
        startServer(9090);
    }
}
