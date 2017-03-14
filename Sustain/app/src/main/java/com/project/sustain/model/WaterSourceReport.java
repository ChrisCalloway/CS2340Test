package com.project.sustain.model;

/**
 * Created by Chris on 3/13/17.
 */

public class WaterSourceReport extends Report{

    private WaterType reportedWaterType;
    private WaterCondition reportedWaterCondition;

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

    public WaterCondition getreportedWaterCondition() {
        return this.reportedWaterCondition;
    }
}
