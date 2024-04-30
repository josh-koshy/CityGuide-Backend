package dev.koshy.cityguide_backend;

import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;


public abstract class Loggable {
    protected String filename;

    public Loggable(String filename) {
        this.filename = filename;
    }

    // Abstract method to format the log message
    public abstract String formatMessage(String message);

    // Concrete method to log the message to a file
    public void log(String message) {
        try (PrintWriter out = new PrintWriter(new FileWriter(filename, true))) {
            out.println(formatMessage(message));
        } catch (IOException e) {
            System.err.println("Error writing to log file: " + e.getMessage());
        }
    }

}
