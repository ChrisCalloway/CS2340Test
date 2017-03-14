package com.project.sustain.model;

import java.util.List;

/**
 * Created by Chris on 3/13/17.
 */

public class ReportManagementFacade {
    private WaterReportManager waterReportManager;

    /**
     * Use singleton pattern, try to replace with dependency injection?
     */
    private static ReportManagementFacade reportManagementInstance = new ReportManagementFacade();

    /**
     * private constructor for facade pattern
     */
    private ReportManagementFacade() {
        waterReportManager = new WaterReportManager();
    }

    /**
     * Singleton pattern accessor for instance
     *
     * @return The one and only instance of this facade
     */
    public static ReportManagementFacade getReportManagementInstance() {
        return reportManagementInstance;
    }

    /**
     * Get list of water source reports
     * Law of Demeter
     * @return list of water source reports
     */
    public List<Report> getWaterSourceReports() {
        return waterReportManager.getWaterSourceReports();
    }

    /**
     * Get list of water purity reports
     * Law of Demeter
     * @return list of water source reports
     */
    public List<Report> getWaterPurityReports() {
        return waterReportManager.getWaterPurityReports();
    }

    /**
     * Add water source report
     *
     * @param reporterName Name of the reporter
     * @param reportedLatitude Latitude of the water source report
     * @param reportedLongitude Latitude of the water source report
     * @param reportedWaterType Water type
     * @param reportedWaterCondition Water condition
     */
    public void addNewWaterSourceReport(String reporterName, double reportedLatitude,
                                        double reportedLongitude,
                                        WaterType reportedWaterType,
                                        WaterCondition reportedWaterCondition) {
        waterReportManager.addNewWaterSourceReport(reporterName, reportedLatitude,
                reportedLongitude, reportedWaterType, reportedWaterCondition);
    }

    /**
     * Add water purity report
     *
     * @param reporterName Name of the reporter
     * @param reportedLatitude Latitude of the water source report
     * @param reportedLongitude Latitude of the water source report
     * @param reportedOverallWaterCondition Overall water condition
     * @param reportedVirusPPM Virus amount in parts per million
     * @param reportedContaminantPPM Contaminant amount in parts per million
     */
    public void addNewWaterPurityReport(String reporterName, double reportedLatitude,
                                        double reportedLongitude,
                                        OverallWaterCondition reportedOverallWaterCondition,
                                        double reportedVirusPPM, double reportedContaminantPPM) {
        waterReportManager.addNewWaterPurityReport(reporterName, reportedLatitude,
                reportedLongitude, reportedOverallWaterCondition, reportedVirusPPM,
                reportedContaminantPPM);
    }
}
