package com.yourcompany.app;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;

public class Server {

    public static void startServer(int port) {
        FirebaseAdminInit.initializeFirebase(); // Ensure Firebase is initialized
        System.out.println("Server is starting on port " + port + "...");

        try (ServerSocket serverSocket = new ServerSocket(port)) {
            System.out.println("Server started and listening on port " + port);

            while (true) { // Continuously accept new connections
                try (Socket clientSocket = serverSocket.accept();
                     BufferedReader in = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));
                     PrintWriter out = new PrintWriter(clientSocket.getOutputStream(), true)) {

                    String userEmail = in.readLine(); // Read user email sent from the client
                    String userUid = FirebaseAdminInit.getUserUidByEmail(userEmail); // Fetch UID for the given email
                    System.out.println("User UID for " + userEmail + ": " + userUid);

                    // Respond back to the client if needed
                    out.println("User UID for " + userEmail + ": " + userUid);
                } catch (Exception e) {
                    System.out.println("An error occurred while handling a connection: " + e.getMessage());
                    e.printStackTrace();
                }
            }
        } catch (Exception e) {
            System.out.println("Could not start the server on port " + port + ": " + e.getMessage());
            e.printStackTrace();
        }
    }
}
