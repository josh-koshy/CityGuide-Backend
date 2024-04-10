package com.yourcompany.app;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import com.google.maps.model.*;

@RestController
public class Controller {

    @GetMapping("/getUID")
    public String getUID(@RequestParam String userEmail) {
        return RequestHandler.getUserUid(userEmail);
    }

    @GetMapping("/getNearbyPlaces")
    public PlacesSearchResponse getNearbyPlaces(
            @RequestParam double latitude, 
            @RequestParam double longitude, 
            @RequestParam int radius) {
        String apiKey = "AIzaSyC7SRSipgUCOxnWLsE6Qo0P6YbkB7JJpUM";
        return RequestHandler.getNearbyPlaces(apiKey, latitude, longitude, radius);
    }
}
