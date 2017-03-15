package com.project.sustain.model;

/**
 * Created by Chris on 3/13/17.
 */

public class WaterSourceReport extends Report{

    private WaterType reportedWaterType;
    private WaterCondition reportedWaterCondition;

    public WaterSourceReport() {}

    // Constructor
    public WaterSourceReport(String reporterName, double reportedLatitude, double reportedLongitude,
                             WaterType reportedWaterType, WaterCondition reportedWaterCondition) {
        this.reporterName = reporterName;
        this.reportedLocation = new Location(reportedLatitude, reportedLongitude);
        this.reportNumber = REPORT_ID++;
        this.reportedWaterType = reportedWaterType;
        this.reportedWaterCondition = reportedWaterCondition;
    }

    public WaterType getReportedWaterType() {
        return this.reportedWaterType;
    }

    public WaterCondition getReportedWaterCondition() {
        return this.reportedWaterCondition;
    }

    @Override
    public String toString() {
        return super.toString() + "\nWater Type: " + getReportedWaterType() +
                "\nCondition: " + getReportedWaterCondition();
    }
}
