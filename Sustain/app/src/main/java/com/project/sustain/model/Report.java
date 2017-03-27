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
    protected String reportId;

    public Report() {
        this(null, "","","","", "");

    }

    public Report(Address address, String reporterName, String reporterUserId, String dateOfReport,
                  String timeOfReport, String reportId) {
        this.address = address;
        this.reporterName = reporterName;
        this.reporterUserId = reporterUserId;
        this.dateOfReport = dateOfReport;
        this.timeOfReport = timeOfReport;
        this.reportId = reportId;

    }

    public String getReporterUserId() {
        return reporterUserId;
    }

    public void setReporterUserId(String reporterUserId) {
        this.reporterUserId = reporterUserId;
    }

    public String getReportId() { return reportId; }

    public void setReportId(String reportId) { this.reportId = reportId; }

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
        String start = "";
        if (!place.equals("")) {
            start = "Place: " + place + "\n";
        }
        return start +
                "Reporter: " + reporterName
                + "\nReport ID: " + this.reportId
                + "\nLatitude: " + this.address.getLocation().getLatitude()
                + "\nLongitude: " + this.address.getLocation().getLongitude()
                + "\nAddress:\n" + this.address.toString()
                + "\nCountry: " + this.address.getCountry();

    }
}