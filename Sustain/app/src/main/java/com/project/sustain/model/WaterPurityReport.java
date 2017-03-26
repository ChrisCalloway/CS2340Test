package com.project.sustain.model;

import com.google.firebase.database.IgnoreExtraProperties;

import java.io.Serializable;

/**
 * Created by Chris on 3/13/17.
 */
@IgnoreExtraProperties
public class WaterPurityReport extends Report implements Serializable {
    private OverallWaterCondition reportedOverallWaterCondition;
    private double reportedVirusPPM;
    private double reportedContaminantPPM;


    // Constructors for WaterPurityReport
    public WaterPurityReport() {}

    public WaterPurityReport(Address address, String reporterName, String reportUserId,
                             String dateOfReport, String timeOfReport, int reportNumber,
                             OverallWaterCondition reportedOverallWaterCondition,
                             double reportedVirusPPM, double reportedContaminantPPM,
                             String reportId) {
        super(address, reporterName, reportUserId, dateOfReport, timeOfReport,
                reportId);
        this.reportedOverallWaterCondition = reportedOverallWaterCondition;
        this.reportedVirusPPM = reportedVirusPPM;
        this.reportedContaminantPPM = reportedContaminantPPM;
    }

    public void setReportedOverallWaterCondition(OverallWaterCondition condition) {
        this.reportedOverallWaterCondition = condition;
    }
    public OverallWaterCondition getReportedOverallWaterCondition() {
        return this.reportedOverallWaterCondition;
    }

    public void setReportedVirusPPM(double virusPPM) {
        this.reportedVirusPPM = virusPPM;
    }

    public double getReportedVirusPPM() {
        return this.reportedVirusPPM;
    }

    public void setReportedContaminantPPM(double contaminantPPM) {
        this.reportedContaminantPPM = contaminantPPM;
    }

    public double getReportedContaminantPPM() {
        return this.reportedContaminantPPM;
    }

    @Override
    public String toString() {
        return super.toString()
                + "\nOverall Condition: " + this.reportedOverallWaterCondition
                + "\nVirus PPM: " + this.reportedVirusPPM
                + "\nContaminant PPM: " + this.reportedContaminantPPM;
    }
}
