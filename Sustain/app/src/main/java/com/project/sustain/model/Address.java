package com.project.sustain.model;

import com.google.firebase.database.IgnoreExtraProperties;

import java.io.Serializable;

/**
 * Model class for storing address information.
 * @author Marcia
 */

@IgnoreExtraProperties
public class Address implements Serializable {
    private String streetAddress1;
    private String streetAddress2;
    private String city;
    private String stateOrProvince;
    private String country;
    private String zipCode;
    private Location mLocation;
    private String placeName;

    public Address() {
        streetAddress1 = "";
        streetAddress2 = "";
        city = "";
        stateOrProvince = "";
        country = "";
        zipCode = "";
        placeName = "";
    }

    public Location getLocation() {
        return mLocation;
    }

    public void setLocation(Location location) {
        mLocation = location;
    }

    public String getStreetAddress1() {
        return streetAddress1;
    }

    public void setStreetAddress1(String streetAddress1) {
        this.streetAddress1 = streetAddress1;
    }

    public String getStreetAddress2() { return streetAddress2; }

    public void setStreetAddress2(String streetAddress2) {
        this.streetAddress2 = streetAddress2;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getStateOrProvince() {
        return stateOrProvince;
    }

    public void setStateOrProvince(String stateOrProvince) {
        this.stateOrProvince = stateOrProvince;
    }

    public String getCountry() {
        return country;
    }

    public void setCountry(String country) {
        this.country = country;
    }

    public String getZipCode() {
        return zipCode;
    }

    public void setZipCode(String zipCode) {
        this.zipCode = zipCode;
    }

    public String getPlaceName() { return this.placeName; }

    public void setPlaceName(String placeName) { this.placeName = placeName; }

    @Override
    public String toString() {
        return streetAddress1 + ", " + city + ", " + stateOrProvince;
    }
}
