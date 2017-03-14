package com.project.sustain.model;

/**
 * Created by Chris on 3/13/17.
 */

public class WaterPurityReport extends Report {
    private OverallWaterCondition reportedOverallWaterCondition;
    private double reportedVirusPPM;
    private double reportedContaminantPPM;

    // Constructor for WaterPurityReport
    public WaterPurityReport(String reporterName, double reportedLatitude, double reportedLongitude,
                             OverallWaterCondition reportedOverallWaterCondition,
                             double reportedVirusPPM, double reportedContaminantPPM) {
        this.reporterName = reporterName;
        this.reportedLocation = new Location(reportedLatitude, reportedLongitude);
        this.reportNumber = REPORT_ID++;
        this.reportedOverallWaterCondition = reportedOverallWaterCondition;
        this.reportedVirusPPM = reportedVirusPPM;
        this.reportedContaminantPPM = reportedContaminantPPM;
    }

    public OverallWaterCondition getReportedOverallWaterCondition() {
        return this.reportedOverallWaterCondition;
    }

    public double getReportedVirusPPM() {
        return this.reportedVirusPPM;
    }

    public double getReportedContaminantPPM() {
        return this.reportedContaminantPPM;
    }
}
