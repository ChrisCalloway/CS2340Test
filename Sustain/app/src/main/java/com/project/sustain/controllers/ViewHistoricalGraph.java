package com.project.sustain.controllers;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

import com.jjoe64.graphview.GraphView;
import com.jjoe64.graphview.series.DataPoint;
import com.jjoe64.graphview.series.LineGraphSeries;
import com.project.sustain.R;
import com.project.sustain.model.HistoricalGraphData;

/**
 * Created by Julio de Sa on 4/1/2017.
 */
public class ViewHistoricalGraph extends AppCompatActivity {
    private GraphView graph;
    private HistoricalGraphData graphData;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_historical_graph);

        graphData = (HistoricalGraphData) getIntent().getSerializableExtra("graphData");

        // Now, need to make calls to get the necessary information


        createGraph();
    }

    public void createGraph() {
        graph = (GraphView) findViewById(R.id.graph);
        LineGraphSeries<DataPoint> series = new LineGraphSeries<>(new DataPoint[]{
                new DataPoint(0, 500),
                new DataPoint(1, 350),
                new DataPoint(2, 200),
                new DataPoint(3, 800),
                new DataPoint(4, 50)
        });

        graph.getViewport().setYAxisBoundsManual(true);
        graph.getViewport().setMinY(0);
        graph.getViewport().setMaxY(1000);

        graph.getViewport().setXAxisBoundsManual(true);
        graph.getViewport().setMinX(0);
        graph.getViewport().setMaxX(5);

        // enable scaling and scrolling
        graph.getViewport().setScalable(true);
        graph.getViewport().setScalableY(true);

        graph.addSeries(series);
    }
}
