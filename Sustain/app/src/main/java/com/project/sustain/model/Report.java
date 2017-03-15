package com.project.sustain.model;

/**
 * Created by Chris on 3/13/17.
 * This is the abstract Report class.  Have chosen to use this
 * since the WaterSourceReport and WaterPurityReport have similar
 * methods and fields.
 */

public abstract class Report {
    protected Location reportedLocation;
    protected String reporterName;
    protected int reportNumber;
    protected static int REPORT_ID = 0;

    public String getReporterName() {
        return reporterName;
    }

    // Method to get latitude of this location
    public double getLatitude() {
        return reportedLocation.getLatitude();
    }

    // Method to get longitude of this location
    public double getLongitude() {
        return reportedLocation.getLongitude();
    }

    @Override
    public String toString() {
        return "Reporter: " + reporterName + "\nLatitude: " + this.getLatitude() +
                "\nLongitude: " + this.getLongitude();
    }
}
