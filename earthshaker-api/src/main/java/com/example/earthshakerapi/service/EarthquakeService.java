package com.example.earthshakerapi.service;

import com.example.earthshakerapi.models.Earthquake;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

@Service
public class EarthquakeService {
    public Earthquake[] getEarthquakes() {
        String url = "https://earthquake.usgs.gov/fdsnws/event/1/query?format=geojson&starttime=2014-01-01&endtime=2014-01-02";
        RestTemplate restTemplate = new RestTemplate();
        EarthquakeResponse response = restTemplate.getForObject(url, EarthquakeResponse.class);
        return response.getFeatures();
    }

    static class EarthquakeResponse {
        private Earthquake[] features;

        public Earthquake[] getFeatures() {
            return features;
        }

        public void setFeatures(Earthquake[] features) {
            this.features = features;
        }
    }
}
