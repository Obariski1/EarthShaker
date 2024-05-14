package com.example.earthshakerapi.models;

import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
public class Earthquake {
    private List<Feature> features;
}

@Getter
@Setter

class Feature {
    private String type;
    private Properties properties;
    private Geometry geometry;
    private String id;
}

@Getter
class Properties {
    private double mag;
    private String place;
    private long time;
    private long updated;
    private String url;
    private String detail;
    private Integer felt;
    private Integer cdi;
    private Integer mmi;
    private String alert;
    private String status;
    private Integer tsunami;
    private Integer sig;
    private String net;
    private String code;
    private String ids;
    private String sources;
    private String types;
    private Integer nst;
    private double dmin;
    private double rms;
    private Integer gap;
    private String magType;
    private String title;
}

@Getter
@Setter
class Geometry {
    private String type;
    private List<Double> coordinates;
}
