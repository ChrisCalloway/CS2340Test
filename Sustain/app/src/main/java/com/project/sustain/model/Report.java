package com.project.sustain.model;

import com.google.firebase.database.IgnoreExtraProperties;

import java.io.Serializable;

/**
 * Created by Chris on 3/13/17.
 * This is the abstract Report class.  Have chosen to use this
 * since the WaterSourceReport and WaterPurityReport have similar
 * methods and fields.
 */
@IgnoreExtraProperties
public abstract class Report implements Serializable {
    protected Address address;
    protected String reporterName;
    protected String reporterUserId;
    protected String dateOfReport;
    protected String timeOfReport;
    protected int reportNumber;
    protected static int REPORT_ID = 0;

    public Report() {}

    public Report(Address address, String reporterName, String reporterUserId, String dateOfReport,
                  String timeOfReport, int reportNumber) {
        this.address = address;
        this.reporterName = reporterName;
        this.reporterUserId = reporterUserId;
        this.dateOfReport = dateOfReport;
        this.timeOfReport = timeOfReport;
        this.reportNumber = reportNumber;
    }

    public String getReporterUserId() {
        return reporterUserId;
    }

    public void setReporterUserId(String reporterUserId) {
        this.reporterUserId = reporterUserId;
    }

    public String getDateOfReport() {
        return dateOfReport;
    }

    public void setDateOfReport(String dateOfReport) {
        this.dateOfReport = dateOfReport;
    }


    public String getTimeOfReport() {
        return timeOfReport;
    }

    public void setTimeOfReport(String timeOfReport) {
        this.timeOfReport = timeOfReport;
    }

    public int getReportNumber() {
        return reportNumber;
    }

    public void setReportNumber(int reportNumber) {
        this.reportNumber = reportNumber;
    }

    public String getReporterName() {
        return reporterName;
    }

    public void setReporterName(String reporterName) { this.reporterName = reporterName; }

    public Address getAddress() {
        return address;
    }

    public void setAddress(Address address) {
        this.address = address;
    }

    @Override
    public String toString() {
        String place = address.getPlaceName() + "";
        if (!place.equals("")) {
            return "Place: " + place + "\nReporter: " + reporterName + "\nLatitude: " +
                    this.address.getLocation().getLatitude() +
                    "\nLongitude: " + this.address.getLocation().getLongitude();
        }
        return "Reporter: " + reporterName + "\nLatitude: " +
                this.address.getLocation().getLatitude() +
                "\nLongitude: " + this.address.getLocation().getLongitude();
    }
}
