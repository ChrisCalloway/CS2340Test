package com.project.sustain.model;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Chris on 3/13/17.
 */

class WaterReportManager {
    private List<Report> waterSourceReports;
    private List<Report> waterPurityReports;

    /**
     * Constructor
     */
    WaterReportManager() {
        waterSourceReports = new ArrayList<>();
        waterPurityReports = new ArrayList<>();
    }

    /**
     * Add a WaterSourceReport to list of water source reports
     *
     * @param reporterName Name of the reporter
     * @param reportedLatitude Latitude of the water source report
     * @param reportedLongitude Longitude of the water source report
     * @param reportedWaterType Water type
     * @param reportedWaterCondition Water condition
     */
    void addNewWaterSourceReport(String reporterName, double reportedLatitude,
                                 double reportedLongitude,
                                 WaterType reportedWaterType,
                                 WaterCondition reportedWaterCondition) {
        WaterSourceReport newWaterSourceReport = new WaterSourceReport(reporterName,
                reportedLatitude, reportedLongitude, reportedWaterType, reportedWaterCondition);
        waterSourceReports.add(newWaterSourceReport);
    }

    /**
     * Add a WaterPurityReport to list of water purity reports
     *
     * @param reporterName Name of the reporter
     * @param reportedLatitude Latitude of the water source report
     * @param reportedLongitude Longitude of the water source report
     * @param reportedOverallWaterCondition Overall water condition
     * @param reportedVirusPPM Virus amount in parts per million
     * @param reportedContaminantPPM Contaminant amount in parts per million
     */
    void addNewWaterPurityReport(String reporterName, double reportedLatitude,
                                 double reportedLongitude,
                                 OverallWaterCondition reportedOverallWaterCondition,
                                 double reportedVirusPPM, double reportedContaminantPPM) {
        WaterPurityReport newWaterPurityReport = new WaterPurityReport(reporterName,
                reportedLatitude, reportedLongitude, reportedOverallWaterCondition,
                reportedVirusPPM, reportedContaminantPPM);
        waterPurityReports.add(newWaterPurityReport);
    }

    /**
     * This is package visible because only facade/model should be asking for this data
     *
     * @return list of water source reports
     */
    List<Report> getWaterSourceReports() {
        return waterSourceReports;
    }

    /**
     * This is package visible because only facade/model should be asking for this data
     *
     * @return list of water purity reports
     */
    List<Report> getWaterPurityReports() {
        return waterPurityReports;
    }
}
