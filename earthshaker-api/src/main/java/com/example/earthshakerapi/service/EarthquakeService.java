package com.example.earthshakerapi.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;

@Service
public class EarthquakeService {

    private final WebClient webClient;

    public EarthquakeService(WebClient.Builder webClientBuilder) {
        this.webClient = webClientBuilder.baseUrl("https://earthquake.usgs.gov/fdsnws/event/1/query").build();
    }

    public Mono<String> getAllEarthquakes() {
        return this.webClient.get()
                .uri(uriBuilder -> uriBuilder
                        .queryParam("format", "geojson")
                        .queryParam("starttime", "2024-01-25")
                        .queryParam("endtime", "2024-01-26")
                        .build())
                .retrieve()
                .bodyToMono(String.class);
    }
}
