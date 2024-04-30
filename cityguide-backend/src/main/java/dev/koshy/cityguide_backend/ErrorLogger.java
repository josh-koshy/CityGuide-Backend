package dev.koshy.cityguide_backend;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class ErrorLogger extends Loggable {
    public ErrorLogger(String filename) {
        super(filename);
    }

    @Override
    public String formatMessage(String message) {
        String timestamp = LocalDateTime.now().format(DateTimeFormatter.ISO_LOCAL_DATE_TIME);
        return "ERROR: Log (" + timestamp + "): " + message;
    }
}