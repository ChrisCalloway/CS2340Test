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

    /**
     * Constructor for the location class
     */
   @SuppressWarnings("unused")
   public Location() {

   }

    /**
     * Constructor for the location class
     * @param latitude The latitude of the location
     * @param longitude The longitude of the location
     */
    public Location(double latitude, double longitude) {
        this.latitude = latitude;
        this.longitude = longitude;
    }

    /**
     * Method that will get the location's latitude
     * @return the latitude of the location
     */
    public double getLatitude() {
        return this.latitude;
    }

    /**
     * Method that will get the location's longitude
     * @return the longitude of the location
     */
    public double getLongitude() {
        return this.longitude;
    }



    @Override
    public String toString() {
        return "Latitude: " + latitude + "\nLongitude: " + longitude;
    }
}
