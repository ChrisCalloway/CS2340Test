package com.project.sustain.controllers;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;

import com.jjoe64.graphview.GraphView;
import com.jjoe64.graphview.series.BarGraphSeries;
import com.jjoe64.graphview.series.DataPoint;
import com.jjoe64.graphview.series.LineGraphSeries;
import com.project.sustain.R;
import com.project.sustain.model.HistoricalGraphData;
import com.project.sustain.model.HistoricalGraphDataCalculator;
import com.project.sustain.model.Month;
import com.project.sustain.model.WaterPurityReport;
import com.project.sustain.model.WaterReportManager;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by Julio de Sa on 4/1/2017.
 */
public class ViewHistoricalGraph extends AppCompatActivity {
    private GraphView graph;
    private HistoricalGraphDataCalculator graphData;
    // Have the basic data done in the Historical Graph Data,
    // and then do the calculations here.
    private QueryEntireListListener qeListener;
    private WaterReportManager mReportManager;
    private List<WaterPurityReport> mReportList;
    private Map<Month, Double> coordinatePointData;
    private String year;
    private String dataType;
    private String location;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_historical_graph);

        graphData = (HistoricalGraphDataCalculator) getIntent().getSerializableExtra("graphData");
        year = graphData.getYear();
        dataType = graphData.getDataType();
        location = graphData.getLocation();
        mReportManager = new WaterReportManager();
        mReportList = new ArrayList<>();
        qeListener = new QueryEntireListListener() {
            @Override
            public <T> void onComplete(List<T> list) {
                mReportList = ((ArrayList<WaterPurityReport>) list);
                Log.d("GraphData-OnComplete", "Got reportList about to call processWaterPurityReports");
                // Process the list of water purity reports
                // It seems that we need to listen for when coordinatePointData is assigned a value.
                processWaterPurityReports();
            }

            @Override
            public void onError(Throwable error) {
            }
        };
        mReportManager.setQueryEntireListListener(qeListener);
        mReportManager.getEntireWaterPurityReportList();

    }

    public void createGraph(Map<Month, Double> coordinatePointData) {
        // Use graphData for information to display
        graph = (GraphView) findViewById(R.id.graph);
        DataPoint[] dataPoints = new DataPoint[12];
        for (int i = 0; i < Month.values().length; i++) {
            Log.d("DataPointGenerator", "Y value is: " + coordinatePointData.get(Month.values()[i]));
            dataPoints[i] = new DataPoint(i, coordinatePointData.get(Month.values()[i]));
        }
        BarGraphSeries<DataPoint> series = new BarGraphSeries<>(dataPoints
//                new DataPoint[]{

//                new DataPoint(0, coordinatePointData.get(Month.values()[0])),
//                new DataPoint(1, coordinatePointData.get(Month.values()[1])),
//                new DataPoint(2, coordinatePointData.get(Month.values()[2])),
//                new DataPoint(3, coordinatePointData.get(Month.values()[3])),
//                new DataPoint(4, coordinatePointData.get(Month.values()[4])),
//                new DataPoint(5, coordinatePointData.get(Month.values()[5])),
//                new DataPoint(6, coordinatePointData.get(Month.values()[6])),
//                new DataPoint(7, coordinatePointData.get(Month.values()[7])),
//                new DataPoint(8, coordinatePointData.get(Month.values()[8])),
//                new DataPoint(9, coordinatePointData.get(Month.values()[9])),
//                new DataPoint(10, coordinatePointData.get(Month.values()[10])),
//                new DataPoint(11, coordinatePointData.get(Month.values()[11]))
//                new DataPoint(0, 0.0),
//                new DataPoint(1, 0.0),
//                new DataPoint(2, 0.18),
//                new DataPoint(3, 0.0),
//                new DataPoint(4, 0.0),
//                new DataPoint(5, 0.0),
//                new DataPoint(6, 0.0),
//                new DataPoint(7, 0.0),
//                new DataPoint(8, 0.0),
//                new DataPoint(9, 0.0),
//                new DataPoint(10, 0.0),
//                new DataPoint(11, 0.0)
//        }
        );

        graph.getViewport().setYAxisBoundsManual(true);
        graph.getViewport().setMinY(0);
        graph.getViewport().setMaxY(2);

        graph.getViewport().setXAxisBoundsManual(true);
        graph.getViewport().setMinX(0);
        graph.getViewport().setMaxX(12);

        // enable scaling and scrolling
        graph.getViewport().setScalable(true);
        graph.getViewport().setScalableY(false);

        graph.addSeries(series);
    }

    /**
     * Helper method to process the WaterPurityReport list returned from the database.
     * Take using the year selected, the datatype (virus or contaminant), and the location,
     * created 12 tuples such that each corresponds to a month of the year selected wherein
     * each month is the average of the amount in PPM of the datatype selected.
     */
    public void processWaterPurityReports() {
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
        // total PPM for the provided datatype.
        if(this.dataType.equals("virus")) {
            for (WaterPurityReport currentWaterPurityReport : matchedWaterPurityReports) {
                // Get the current report's key
                Log.d("ViewHistoricalGraph", "Month found: " + Month.valueOf(currentWaterPurityReport.getDateOfReport().
                        substring(0, currentWaterPurityReport.getDateOfReport().indexOf(' ')).toUpperCase()));
//                Log.d("ViewHistoricalGraph", "Month found: " + Month.valueOf("MAR"));
                        month = Month.valueOf(currentWaterPurityReport.getDateOfReport().
                        substring(0, currentWaterPurityReport.getDateOfReport().indexOf(' ')).toUpperCase());

                // Get the current report's value
                ppmValue = currentWaterPurityReport.getReportedVirusPPM();

                initialCount = 1;

                // If key already in map, or if a value already in map for given month,
                // aggregate the value of ppm
                if(graphAggregateValues.containsKey(month)) {
                    // Aggregate the cumulative ppm value with the current ppm value
                    newPPMValue = ppmValue + graphAggregateValues.get(month);
                    // Insert the newly aggregated ppm value back into the hashmap
                    graphAggregateValues.put(month, newPPMValue);
                    // Increment the cumulative count by one
                    initialCount++;
                    // Insert the just incremented count back into the hashmap
                    graphCountValues.put(month, initialCount);

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
//                Log.d("Calculating Average", graphAggregateValues.get(month).toString());
                // divide the total ppm for each month by count of instances per month
                if(graphAggregateValues.get(month) <= 0) {
                    Log.d("Calculating Avg, zero", graphAggregateValues.get(month).toString());
                    averagePPMForMonth = (int) graphAggregateValues.get(month).doubleValue();
                    graphAveragePPMValues.put(month, averagePPMForMonth);
                } else {
                    averagePPMForMonth = graphAggregateValues.get(month) /
                            (double) graphCountValues.get(month);
                    Log.d("Calculated Average", graphAggregateValues.get(month).toString());
                    graphAveragePPMValues.put(month, averagePPMForMonth);
                }
            }
        } else if(this.dataType.equals("contaminant")) {
            for (WaterPurityReport currentWaterPurityReport : matchedWaterPurityReports) {
                Log.d("ViewHistoricalGraph", "Month found: " + Month.valueOf(currentWaterPurityReport.getDateOfReport().
                        substring(0, currentWaterPurityReport.getDateOfReport().indexOf(' ')).toUpperCase()));
                // Get the current report's key
                month = Month.valueOf(currentWaterPurityReport.getDateOfReport().
                        substring(0, currentWaterPurityReport.getDateOfReport().indexOf(' ')).toUpperCase());

                // Get the current report's value
                ppmValue = currentWaterPurityReport.getReportedContaminantPPM();

                initialCount = 1;

                // If key already in map, or if a value already in map for given month,
                // aggregate the value of ppm
                if(graphAggregateValues.containsKey(month)) {
                    // Aggregate the cumulative ppm value with the current ppm value
                    newPPMValue = ppmValue + graphAggregateValues.get(month);
                    // Insert the newly aggregated ppm value back into the hashmap
                    graphAggregateValues.put(month, newPPMValue);
                    // Increment the cumulative count by one
                    initialCount++;
                    // Insert the just incremented count back into the hashmap
                    graphCountValues.put(month, initialCount);

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
//                Log.d("Calculating Average", graphAggregateValues.get(month).toString());
                // divide the total ppm for each month by count of instances per month
                if(graphAggregateValues.get(month) <= 0) {
                    Log.d("Calculating Avg, zero", graphAggregateValues.get(month).toString());
                    averagePPMForMonth = graphAggregateValues.get(month);
                    graphAveragePPMValues.put(month, averagePPMForMonth);
                } else {
                    averagePPMForMonth = graphAggregateValues.get(month) / graphCountValues.get(month);
//                    Log.d("Calculated Average", graphAggregateValues.get(month).toString());
                    Log.d("Calculating Average", "numerator is " + graphAggregateValues.get(month));
                    Log.d("Calculated Average", "denominator is " + graphCountValues.get(month));
                    Log.d("Calculated Average", "" + averagePPMForMonth);


                    graphAveragePPMValues.put(month, averagePPMForMonth);
                }
            }
        }
//        return graphAveragePPMValues;
        createGraph(graphAveragePPMValues);

    }
}
