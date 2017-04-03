package com.project.sustain.controllers;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.RadioGroup;
import android.widget.Spinner;
import android.widget.Toast;

import com.project.sustain.R;
import com.project.sustain.model.HistoricalGraphData;
import com.project.sustain.model.Report;
import com.project.sustain.model.WaterReportManager;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

public class SelectHistoricalData extends AppCompatActivity {

    private Spinner spinLocationName;
    private RadioGroup radDataType;
    private Spinner spinYear;
    private Button btnCancel;
    private Button btnContinue;
    private WaterReportManager mReportManager;
    private List<String> mLocationList;
    private QueryListResultListener qrListener;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_select_historical_data);

        spinLocationName = (Spinner) findViewById(R.id.spinLocationName);
        radDataType = (RadioGroup) findViewById(R.id.radDataType);
        spinYear = (Spinner) findViewById(R.id.spinYear);
        btnCancel = (Button) findViewById(R.id.btnCancel);
        btnContinue = (Button) findViewById(R.id.btnContinue);

        mReportManager = new WaterReportManager();
        mLocationList = new ArrayList<>();
        final ArrayAdapter<String> locationAdapter = new ArrayAdapter<>(this,
                android.R.layout.simple_spinner_dropdown_item, mLocationList);

        spinLocationName.setAdapter(locationAdapter);

        qrListener = new QueryListResultListener() {
            @Override
            public <T, K> void onComplete(T item, K key) {
                ((Report) item).setReportId((String)key);
                mLocationList.add(((Report) item).getAddress().getPlaceName());
                locationAdapter.notifyDataSetChanged();
            }

            @Override
            public void onError(Throwable error) {
            }
        };
        mReportManager.setQueryListResultListener(qrListener);
        mReportManager.getWaterPurityReports();

        ArrayList<String> years = getYearsArray();
        ArrayAdapter<String> yearAdapter = new ArrayAdapter<>(this,
                android.R.layout.simple_spinner_dropdown_item, years);
        spinYear.setAdapter(yearAdapter);


        btnCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //close and go back to MainActivity.
                //startActivity(new Intent(SelectHistoricalData.this, MainActivity.class));
                finish();
            }
        });

        btnContinue.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                HistoricalGraphData graphData = new HistoricalGraphData();
                // Check to ensure all fields have data inputted.
                // If so, use data to do a search on the Firebase database
                // and use returned results to build out history graph
                if (spinYear.getSelectedItemPosition() < 0) {
                    Toast.makeText(getApplicationContext(),
                            "Please select a year.",
                            Toast.LENGTH_SHORT).show();
                    return;
                }
                if (spinLocationName.getSelectedItemPosition() < 0) {
                    Toast.makeText(getApplicationContext(),
                            "Please select a location.",
                            Toast.LENGTH_SHORT).show();
                    return;
                }
                if (mLocationList.isEmpty()) {
                    Toast.makeText(getApplicationContext(),
                            "No locations found.",
                            Toast.LENGTH_SHORT).show();
                    return;
                }
                if (radDataType.getCheckedRadioButtonId() == R.id.radbtnVirusOpt) {
                    graphData.setDataType("virus");
                } else if (radDataType.getCheckedRadioButtonId() == R.id.radbtnContaminantOpt) {
                    graphData.setDataType("contaminant");
                } else {
                    // Did not select an option, prompt user with toast to select an option
                    Toast.makeText(getApplicationContext(),
                            "Please select either virus or contaminant option.",
                            Toast.LENGTH_SHORT).show();
                    return;
                }
                graphData.setYear(spinYear.getSelectedItem().toString());
                graphData.setLocation(spinLocationName.getSelectedItem().toString());
                startActivity(new Intent(SelectHistoricalData.this, ViewHistoricalGraph.class)
                        .putExtra("graphData", graphData));
            }
        });
    }

    /**
     * Create an array of years from 2000 to present to show in spinner.
     * @return years An ArrayList of years from 2000 to present.
     */
    private ArrayList<String> getYearsArray() {
        ArrayList<String> years = new ArrayList<String>();
        int thisYear = Calendar.getInstance().get(Calendar.YEAR);
        for (int i = 2000; i <= thisYear; i++) {
            years.add(Integer.toString(i));
        }
        return years;
    }
}
