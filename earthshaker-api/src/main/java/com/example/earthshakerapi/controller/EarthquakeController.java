package com.example.earthshakerapi.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;
import org.springframework.http.ResponseEntity;

@RestController
public class EarthquakeController {

    @GetMapping("/earthquakes")
    public ResponseEntity<String> getEarthquakes() {
        String url = "https://earthquake.usgs.gov/fdsnws/event/1/query?format=geojson";
        RestTemplate restTemplate = new RestTemplate();
        String result = restTemplate.getForObject(url, String.class);
        return ResponseEntity.ok(result);
    }

    @GetMapping("/earthquakes/{id}")
    public ResponseEntity<String> getEarthquakeById(@PathVariable String id) {
        String url = "https://earthquake.usgs.gov/fdsnws/event/1/query?format=geojson&eventid=" + id;
        RestTemplate restTemplate = new RestTemplate();
        String result = restTemplate.getForObject(url, String.class);
        return ResponseEntity.ok(result);
    }
}
