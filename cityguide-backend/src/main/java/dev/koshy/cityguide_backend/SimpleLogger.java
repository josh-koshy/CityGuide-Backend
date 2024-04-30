package dev.koshy.cityguide_backend;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class SimpleLogger extends Loggable {
    public SimpleLogger(String filename) {
        super(filename);
    }

    @Override
    public String formatMessage(String message) {
        String timestamp = LocalDateTime.now().format(DateTimeFormatter.ISO_LOCAL_DATE_TIME);
        return "Log (" + timestamp + "): " + message;
    }
}