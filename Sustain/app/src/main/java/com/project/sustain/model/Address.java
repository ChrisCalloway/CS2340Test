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

    /**
     * Constructor for the Address class
     * Initializes all fields to empty strings.
     */
    public Address() {
        streetAddress1 = "";
        streetAddress2 = "";
        city = "";
        stateOrProvince = "";
        country = "";
        zipCode = "";
        placeName = "";
    }

    /**
     * Method that will return the address's location
     * @return mLocation the location of the address
     */
    public Location getLocation() {
        return mLocation;
    }

    /**
     * Method that will set the address's location
     * @param location set to the address's location
     */
    public void setLocation(Location location) {
        mLocation = location;
    }

    /**
     * Method that will return the address's street address 1
     * @return streetAddress1 the first part of the street address
     */
    public String getStreetAddress1() {
        return streetAddress1;
    }

    /**
     * Method that will set the address's street address 1
     * @param streetAddress1 the first part of the street address
     */
    public void setStreetAddress1(String streetAddress1) {
        this.streetAddress1 = streetAddress1;
    }

    /**
     * Method that will return the address's street address 2
     * @return streetAddress2 the second part of the street address
     */
    public String getStreetAddress2() { return streetAddress2; }

    /**
     * Method that will set the address's street address 2
     * @param streetAddress2 the second part of the street address
     */
    public void setStreetAddress2(String streetAddress2) {
        this.streetAddress2 = streetAddress2;
    }

    /**
     * Method that will get the address's city
     * @return the address's city
     */
    public String getCity() {
        return city;
    }

    /**
     * Method that will set the address's city
     * @param city the city to be set
     */
    public void setCity(String city) {
        this.city = city;
    }

    /**
     * Method that will get the address's state or province
     * @return the address's state or province
     */
    public String getStateOrProvince() {
        return stateOrProvince;
    }

    /**
     * Method that will set the address's state or province
     * @param stateOrProvince the state or province to be set
     */
    public void setStateOrProvince(String stateOrProvince) {
        this.stateOrProvince = stateOrProvince;
    }

    /**
     * Method that will get the address's country
     * @return the address's country
     */
    public String getCountry() {
        return country;
    }

    /**
     * Method that will set the address's county
     * @param country the country to be set
     */
    public void setCountry(String country) {
        this.country = country;
    }

    /**
     * Method that will get the address's zip code
     * @return the address's zip code
     */
    public String getZipCode() {
        return zipCode;
    }

    /**
     * Method that will set the address's zip code
     * @param zipCode the zip code to be set
     */
    public void setZipCode(String zipCode) {
        this.zipCode = zipCode;
    }

    /**
     * Method that will get the address's place's name
     * @return the place's name in the address
     */
    public String getPlaceName() { return this.placeName; }

    /**
     * Method that will set the address's place's name
     * @param placeName the name to be set
     */
    public void setPlaceName(String placeName) { this.placeName = placeName; }

    @Override
    public String toString() {
        return streetAddress1 + ", " + city + ", " + stateOrProvince;
    }
}
