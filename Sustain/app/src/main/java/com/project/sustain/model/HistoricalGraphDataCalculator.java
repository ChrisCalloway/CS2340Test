package com.project.sustain.model;

import android.util.Log;

import com.project.sustain.controllers.QueryEntireListListener;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


/**
 * Created by Chris on 4/2/17.
 */

public class HistoricalGraphDataCalculator implements Serializable {
    private String location;
    private String year;
    private String dataType;
    private WaterReportManager mReportManager;
    private List<WaterPurityReport> mReportList;
    private QueryEntireListListener qeListener;
    private Map<Month, Double> coordinatePointData;


    public HistoricalGraphDataCalculator(String location, String year, String dataType) {
        this.location = location;
        this.year = year;
        this.dataType = dataType;

    }

    // Uses constructor chaining to invoke above constructor with empty strings.
    public HistoricalGraphDataCalculator() {
        this("","","");
    }

    /**
     *  method to calculate for a location and a year, for each month, take average
     *  of PPM measure of whatever data type passed in (virus or contaminant).
     */
    public void calculateCoordinatePointData() {
        mReportManager.getEntireWaterPurityReportList();
    }

    // TODO
    /**
     * Helper method to process the WaterPurityReport list returned from the database.
     * Take using the year selected, the data type (virus or contaminant), and the location,
     * created 12 tuples such that each corresponds to a month of the year selected wherein
     * each month is the average of the amount in PPM of the data type selected.
     */
    public Map<Month, Double> processWaterPurityReports() {
        Log.d("GraphData-Process", "Got to processWaterPurityReports");
        // First need to iterate through each object and see if its location matches.
        // Then, while iterating through, if the location matches, then need to see if
        // the year matches.  If both do, then select this water purity report.
        List<WaterPurityReport> matchedWaterPurityReports = new ArrayList<>();
        Map<Month, Double> graphAggregateValues = new HashMap<>(Month.values().length);
        Map<Month, Integer> graphCountValues = new HashMap<>(Month.values().length);
        Map<Month, Double> graphAveragePPMValues = new HashMap<>(Month.values().length);
        Month month;
        Integer initialCount;
        Integer newCount;
        Double ppmValue;
        Double newPPMValue;

        for (WaterPurityReport currentWaterPurityReport : mReportList) {
            Log.d("GraphData-WaterReport", "Found: "
                    + currentWaterPurityReport.getAddress().getPlaceName());
            if(currentWaterPurityReport.getAddress().getPlaceName().equals(this.location)
                    && currentWaterPurityReport.getDateOfReport().substring(
                            currentWaterPurityReport.getDateOfReport().lastIndexOf(',') + 1).trim()
                    .equals(this.year)) {
                matchedWaterPurityReports.add(currentWaterPurityReport);
                Log.d("GraphData", "Found: " +
                        currentWaterPurityReport.getAddress().getPlaceName()
                        + " : " + currentWaterPurityReport.getDateOfReport());
            }
        }
        // Now, we have all those water purity reports that match the year
        // and location.  Now, need to divide these into months.
        // Am instantiating a hashmap where the key is the month, the value is the
        // total PPM for the provided data type.
        if(this.dataType.equals("virus")) {
            for (WaterPurityReport currentWaterPurityReport : matchedWaterPurityReports) {
                // Get the current report's key
                month = Month.valueOf(currentWaterPurityReport.getDateOfReport().
                        substring(0, currentWaterPurityReport.getDateOfReport().indexOf(' ')));

                // Get the current report's value
                ppmValue = currentWaterPurityReport.getReportedVirusPPM();

                initialCount = 0;

                // If key already in map, or if a value already in map for given month,
                // aggregate the value of ppm
                if(graphAggregateValues.containsKey(month)) {
                    // Aggregate the cumulative ppm value with the current ppm value
                    newPPMValue = ppmValue
                            + graphAggregateValues.get(month);
                    // Insert the newly aggregated ppm value back into the hashmap
                    graphAggregateValues.put(month, newPPMValue);
                    // Increment the cumulative count by one
                    newCount = graphCountValues.get(month) + 1;
                    // Insert the just incremented count back into the hashmap
                    graphCountValues.put(month, newCount);

                } else {
                    // If this key is not yet used, initialize the ppm value and count.
                    graphAggregateValues.put(month, ppmValue);
                    graphCountValues.put(month, initialCount);
                }
            }

            // Now need to go through graphTupleValues and make sure there is zero for unused
            // keys
            for (int i = 0; i < Month.values().length; i++) {
                month = Month.values()[i];
                if(!graphAggregateValues.containsKey(month)) {
                    graphAggregateValues.put(month, 0.0);
                }
            }

            // Finally, find average for each month's ppm
            double averagePPMForMonth;
            for (int i = 0; i < Month.values().length; i++) {
                month = Month.values()[i];
                // divide the total ppm for each month by count of instances per month
                averagePPMForMonth = graphAggregateValues.get(month) /
                        (double) graphCountValues.get(month);
                graphAveragePPMValues.put(month, averagePPMForMonth);
            }
        } else if(this.dataType.equals("contaminant")) {
            for (WaterPurityReport currentWaterPurityReport : matchedWaterPurityReports) {
                // Get the current report's key
                month = Month.valueOf(currentWaterPurityReport.getDateOfReport().
                        substring(0, currentWaterPurityReport.getDateOfReport().indexOf(' ')));

                // Get the current report's value
                ppmValue = currentWaterPurityReport.getReportedContaminantPPM();

                initialCount = 0;

                // If key already in map, or if a value already in map for given month,
                // aggregate the value of ppm
                if(graphAggregateValues.containsKey(month)) {
                    // Aggregate the cumulative ppm value with the current ppm value
                    newPPMValue = ppmValue
                            + graphAggregateValues.get(month);
                    // Insert the newly aggregated ppm value back into the hashmap
                    graphAggregateValues.put(month, newPPMValue);
                    // Increment the cumulative count by one
                    newCount = graphCountValues.get(month) + 1;
                    // Insert the just incremented count back into the hashmap
                    graphCountValues.put(month, newCount);

                } else {
                    // If this key is not yet used, initialize the ppm value and count.
                    graphAggregateValues.put(month, ppmValue);
                    graphCountValues.put(month, initialCount);
                }
            }

            // Now need to go through graphTupleValues and make sure there is zero for unused
            // keys
            for (int i = 0; i < Month.values().length; i++) {
                month = Month.values()[i];
                if(!graphAggregateValues.containsKey(month)) {
                    graphAggregateValues.put(month, 0.0);
                }
            }

            // Finally, find average for each month's ppm
            double averagePPMForMonth;
            for (int i = 0; i < Month.values().length; i++) {
                month = Month.values()[i];
                // divide the total ppm for each month by count of instances per month
                averagePPMForMonth = graphAggregateValues.get(month) /
                        (double) graphCountValues.get(month);
                graphAveragePPMValues.put(month, averagePPMForMonth);
            }
        }
        return graphAveragePPMValues;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public String getLocation() {
        return this.location;
    }

    public void setYear(String year) {
        Log.d("GraphData-WaterReport", "Setting year: " + year);
        this.year = year;
    }

    public String getYear() {
        return this.year;
    }

    public void setDataType(String dataType) {
        this.dataType = dataType;
    }

    public String getDataType() {
        return this.dataType;
    }

    public Map<Month, Double> getCoordinatePointData() {
        return this.coordinatePointData;
    }
}
