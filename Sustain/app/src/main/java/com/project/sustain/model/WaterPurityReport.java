package com.project.sustain.model;

/**
 * Created by Chris on 3/13/17.
 */

public class WaterPurityReport extends WaterSourceReport {
    private OverallWaterCondition reportedOverallWaterCondition;
    private double reportedVirusPPM;
    private double reportedContaminantPPM;

    public WaterPurityReport() {}

    // Constructor for WaterPurityReport
    public WaterPurityReport(String reporterName, double reportedLatitude, double reportedLongitude,
                             WaterType reportedWaterType, WaterCondition reportedWaterCondition,
                             OverallWaterCondition reportedOverallWaterCondition,
                             double reportedVirusPPM, double reportedContaminantPPM) {
        super(reporterName, reportedLatitude, reportedLongitude, reportedWaterType,
                reportedWaterCondition);
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

    @Override
    public String toString() {
        return super.toString() + "\nOverall: " + this.reportedOverallWaterCondition +
                "\nVirusPPM: " + this.reportedVirusPPM + "\nContaminantPPM: " +
                this.reportedContaminantPPM;
    }
}
