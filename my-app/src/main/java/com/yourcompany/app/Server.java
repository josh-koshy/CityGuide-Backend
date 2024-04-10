package com.yourcompany.app;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;
import com.google.maps.model.PlacesSearchResponse;

public class Server {

    public static void startServer(int port) {
        System.out.println("Server is starting on port " + port + "...");

        try (ServerSocket serverSocket = new ServerSocket(port)) {
            System.out.println("Server started and listening on port " + port);

            while (true) { // Continuously accept new connections
                try (Socket clientSocket = serverSocket.accept()) {
                    handleClientRequest(clientSocket);
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

    private static void handleClientRequest(Socket clientSocket) {
        try (BufferedReader in = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));
             PrintWriter out = new PrintWriter(clientSocket.getOutputStream(), true)) {

            String requestType = in.readLine(); // Read the type of request from the client
            switch (requestType) {
                case "GetUID":
                    String userEmail = in.readLine(); // Read user email sent from the client
                    String userUid = RequestHandler.getUserUid(userEmail); // Get user UID
                    out.println("User UID: " + userUid);
                    break;
                case "GetNearbyPlaces":
                    // Read latitude, longitude, and radius from the client
                    double latitude = Double.parseDouble(in.readLine());
                    double longitude = Double.parseDouble(in.readLine());
                    int radius = Integer.parseInt(in.readLine());

                    // Call the function to get nearby places
                    PlacesSearchResponse nearbyPlaces = RequestHandler.getNearbyPlaces(
                            "AIzaSyC7SRSipgUCOxnWLsE6Qo0P6YbkB7JJpUM", latitude, longitude, radius);

                    // You can handle the response here, e.g., send it back to the client
                    out.println("Nearby places response: " + nearbyPlaces);
                    break;
                default:
                    out.println("Invalid request type");
            }
        } catch (Exception e) {
            System.out.println("An error occurred while handling a connection: " + e.getMessage());
            e.printStackTrace();
        }
    }
}
