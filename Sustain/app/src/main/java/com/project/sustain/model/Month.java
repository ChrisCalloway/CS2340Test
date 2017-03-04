package com.project.sustain.model;

/**
 * Created by georgiainstituteoftechnology on 3/2/17.
 */

public enum Month {
    JANUARY("January"),
    FEBRUARY("February"),
    MARCH("March"),
    APRIL("April"),
    MAY("May"),
    JUNE("June"),
    JULY("July"),
    AUGUST("August"),
    SEPTEMBER("September"),
    OCTOBER("October"),
    NOVEMBER("November"),
    DECEMBER("December");

    private String monthReported;

    Month (String monthPassed) {
        monthReported = monthPassed;
    }

    public String toString() {
        return monthReported;
    }
}
