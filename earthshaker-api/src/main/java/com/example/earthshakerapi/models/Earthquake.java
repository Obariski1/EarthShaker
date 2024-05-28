package com.example.earthquake.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@JsonIgnoreProperties(ignoreUnknown = true)
public class Earthquake {
    private String id;
    private Properties properties;
    private Geometry geometry;

    // Getter und Setter

    public static class Properties {
        private String title;
        private double mag;
        private long time;

        // Getter und Setter
    }

    public static class Geometry {
        private double[] coordinates;

        // Getter und Setter
    }
}
