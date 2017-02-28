package com.project.sustain.model;

import com.google.firebase.database.IgnoreExtraProperties;

/**
 * Created by Marcia on 2/27/2017.
 */

@IgnoreExtraProperties
public class PhoneNumber {
    private int mCountryCode;
    private int mAreaCode;
    private int mPrefix;
    private int mSuffix;

    public int getCountryCode() {
        return mCountryCode;
    }

    public void setCountryCode(int countryCode) {
        mCountryCode = countryCode;
    }

    public int getAreaCode() {
        return mAreaCode;
    }

    public void setAreaCode(int areaCode) {
        mAreaCode = areaCode;
    }

    public int getPrefix() {
        return mPrefix;
    }

    public void setPrefix(int prefix) {
        mPrefix = prefix;
    }

    public int getSuffix() {
        return mSuffix;
    }

    public void setSuffix(int suffix) {
        mSuffix = suffix;
    }


}
