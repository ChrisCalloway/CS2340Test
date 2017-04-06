package com.project.sustain.model;

import java.io.Serializable;

/**
 * Created by Chris on 3/10/17.
 * This is the model class for Location.  Location has a latitude and
 * longitude value.  In our app, we use the Model as the structuring of
 * storing the data in our Firebase database.  Location is used for
 * the abstract Report, which we subclass into WaterSourceReport
 * and PurityReport
 */


public class Location implements Serializable {
    private double latitude;
    private double longitude;

    public Location() {

    }

    public Location(double latitude, double longitude) {
        this.latitude = latitude;
        this.longitude = longitude;
    }

    public double getLatitude() {
        return this.latitude;
    }
    public double getLongitude() {
        return this.longitude;
    }



    @Override
    public String toString() {
        return "Latitude: " + latitude + "\nLongitude: " + longitude;
    }
}
