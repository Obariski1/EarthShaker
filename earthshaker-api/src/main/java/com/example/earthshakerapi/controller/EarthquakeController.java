package com.example.earthshakerapi.controller;

import com.example.earthshakerapi.service.EarthquakeService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Mono;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;

@RestController
@RequestMapping("/api")
public class EarthquakeController {

    private final EarthquakeService earthquakeService;

    @Autowired
    public EarthquakeController(EarthquakeService earthquakeService) {
        this.earthquakeService = earthquakeService;
    }

    @GetMapping("/earthquakes")
    public Mono<String> getAllEarthquakes() {
        return this.earthquakeService.getAllEarthquakes();
    }
}
