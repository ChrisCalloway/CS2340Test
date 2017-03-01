package com.project.sustain.model;

import com.google.firebase.database.IgnoreExtraProperties;

/**
 * Created by Marcia on 2/27/2017.
 */

@IgnoreExtraProperties
public class PhoneNumber {
    private int countryCode;
    private int areaCode;
    private int prefix;
    private int suffix;

    public PhoneNumber() {
        countryCode = 0;
        areaCode = 0;
        prefix = 0;
        suffix = 0;
    }
    public int getCountryCode() {
        return countryCode;
    }

    public void setCountryCode(int countryCode) {
        this.countryCode = countryCode;
    }

    public int getAreaCode() {
        return areaCode;
    }

    public void setAreaCode(int areaCode) {
        this.areaCode = areaCode;
    }

    public int getPrefix() {
        return prefix;
    }

    public void setPrefix(int prefix) {
        this.prefix = prefix;
    }

    public int getSuffix() {
        return suffix;
    }

    public void setSuffix(int suffix) {
        this.suffix = suffix;
    }


}
