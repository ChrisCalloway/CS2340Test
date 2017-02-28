package com.project.sustain.model;

import com.google.firebase.database.IgnoreExtraProperties;

/**
 * Created by Marcia on 2/27/2017.
 */

@IgnoreExtraProperties
public class Address {
    private String mStreetAddress1;
    private String mStreetAddress2;
    private String mCity;
    private String mStateOrProvince;
    private String mCountry;
    private String mZipCode;


    public String getCountry() {
        return mCountry;
    }

    public void setCountry(String country) {
        mCountry = country;
    }

    public String getCity() {
        return mCity;
    }

    public void setCity(String city) {
        mCity = city;
    }

    public String getStateOrProvince() {
        return mStateOrProvince;
    }

    public void setStateOrProvince(String stateOrProvince) {
        mStateOrProvince = stateOrProvince;
    }

    public String getStreetAddress1() {
        return mStreetAddress1;
    }

    public void setStreetAddress1(String streetAddress1) {
        mStreetAddress1 = streetAddress1;
    }

    public String getStreetAddress2() {
        return mStreetAddress2;
    }

    public void setStreetAddress2(String streetAddress2) {
        mStreetAddress2 = streetAddress2;
    }

    public String getZipCode() {
        return mZipCode;
    }

    public void setZipCode(String zipCode) {
        mZipCode = zipCode;
    }


}
