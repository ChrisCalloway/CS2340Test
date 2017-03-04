package com.project.sustain.model;

/**
 * Created by Marcia on 2/27/2017.
 */

public enum PhoneType {
    HOME("Home"),
    WORK("Work"),
    MOBILE("Mobile");

    private String mDescription;
    PhoneType(String phoneDescription) {
        mDescription = phoneDescription;
    }

    @Override
    public String toString() {
        return mDescription;
    }
}
