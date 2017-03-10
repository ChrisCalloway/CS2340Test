package com.project.sustain.model;

/**
 * Created by Chris on 3/10/17.
 */

public class Location {
    private double _latitude;
    private double _longitude;

    public Location(double lat, double longit) {
        _latitude = lat;
        _longitude = longit;
    }

    public double getLatitude() { return _latitude; }
    public double getLongitude() { return _longitude; }
}
