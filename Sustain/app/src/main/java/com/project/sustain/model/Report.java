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
    private Address address;
    private String reporterName;
    private String reporterUserId;
    private String dateOfReport;
    private String timeOfReport;
    private String reportId;

    Report() {
        this(null, "","","","", "");

    }

    @SuppressWarnings("SameParameterValue")
    private Report(Address address, String reporterName, String reporterUserId, String dateOfReport,
                   String timeOfReport, String reportId) {
        this.address = address;
        this.reporterName = reporterName;
        this.reporterUserId = reporterUserId;
        this.dateOfReport = dateOfReport;
        this.timeOfReport = timeOfReport;
        this.reportId = reportId;

    }

    /**
     * Method that will get the reporter's user id
     * @return the reporter's user id
     */
    @SuppressWarnings("unused")
    public String getReporterUserId() {
        return reporterUserId;
    }

    /**
     * Method that will set the reporter's user id
     * @param reporterUserId the user id to be set
     */
    public void setReporterUserId(String reporterUserId) {
        this.reporterUserId = reporterUserId;
    }

    /**
     * Method that will get the report's id
     * @return the report's id
     */
    public String getReportId() { return reportId; }

    /**
     * Method that will set the report's id
     * @param reportId the id to be set
     */
    public void setReportId(String reportId) { this.reportId = reportId; }

    /**
     * Method that will get the report's date
     * @return the report's date
     */
    public String getDateOfReport() {
        return dateOfReport;
    }

    /**
     * Method that will set the report's date
     * @param dateOfReport the date to be set
     */
    public void setDateOfReport(String dateOfReport) {
        this.dateOfReport = dateOfReport;
    }

    /**
     * Method that will get the report's time
     * @return the report's time
     */
    @SuppressWarnings("unused")
    public String getTimeOfReport() {
        return timeOfReport;
    }

    /**
     * Method that will set the report's time
     * @param timeOfReport the time to be set
     */
    public void setTimeOfReport(String timeOfReport) {
        this.timeOfReport = timeOfReport;
    }

    /**
     * Method that will get the reporter's name
     * @return the reporter's name
     */
    @SuppressWarnings("unused")
    public String getReporterName() {
        return reporterName;
    }

    /**
     * Method that will set the reporter's name
     * @param reporterName the name to be set
     */
    public void setReporterName(String reporterName) { this.reporterName = reporterName; }

    /**
     * Method that will get the report's address
     * @return the report's address
     */
    public Address getAddress() {
        return address;
    }

    /**
     * Method that will set the report's address
     * @param address the address to be set
     */
    public void setAddress(Address address) {
        this.address = address;
    }

    @Override
    public String toString() {
        String place = address.getPlaceName() + "";
        String start = "";
        if (!"".equals(place)) {
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
