package com.project.sustain.controllers;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.project.sustain.R;
import com.project.sustain.model.Address;
import com.project.sustain.model.Report;
import com.project.sustain.model.WaterCondition;
import com.project.sustain.model.WaterPurityReport;
import com.project.sustain.model.WaterSourceReport;
import com.project.sustain.model.WaterType;

/**
 * Created by georgiainstituteoftechnology on 3/7/17.
 */

public class ViewReportActivity extends AppCompatActivity {
    private String date;
    private String time;
    private int reportNum;
    private String name;
    private Address locationReceived;
    private WaterType typeReceived;
    private WaterCondition conditionReceived;
    private String userIDReceived;
    private TextView titleSet;
    private TextView dateSet;
    private TextView timeSet;
    private TextView nameSet;
    private TextView strAddSet1;
    private TextView strAddSet2;
    private TextView citySet;
    private TextView stateSet;
    private TextView countrySet;
    private TextView zipCodeSet;
    private TextView waterInfo;
    private Button backToRepBut;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.viewwaterreport);
        titleSet = (TextView) findViewById(R.id.wtrRepTitle);
        dateSet = (TextView) findViewById(R.id.dateReported);
        timeSet = (TextView) findViewById(R.id.timeReported);
        nameSet = (TextView) findViewById(R.id.nameReported);
        strAddSet1 = (TextView) findViewById(R.id.add1Reported);
        strAddSet2 = (TextView) findViewById(R.id.add2Reported);
        citySet = (TextView) findViewById(R.id.cityReported);
        stateSet = (TextView) findViewById(R.id.stateReported);
        countrySet = (TextView) findViewById(R.id.countryReported);
        zipCodeSet = (TextView) findViewById(R.id.zipReported);

        waterInfo = (TextView) findViewById(R.id.editWaterInfo);
        backToRepBut = (Button) findViewById(R.id.backButton);

        //gets the package passed in as an intent and initializes the instance data from the package
        final Intent intent = getIntent();
        Report fromPrevActivity = (Report) intent.getSerializableExtra("report");

        StringBuilder info = new StringBuilder();  //build a multiline string of the reported information.
        date = fromPrevActivity.getDateOfReport();
        time = fromPrevActivity.getTimeOfReport();
        reportNum = fromPrevActivity.getReportNumber();
        name = fromPrevActivity.getReporterName();
        locationReceived = fromPrevActivity.getAddress();

        if (fromPrevActivity instanceof WaterSourceReport) {
            info.append("Water Type: ");
            info.append(((WaterSourceReport) fromPrevActivity).getWaterType().toString());
            info.append("\n");
            info.append("Water Condition: ");
            info.append(((WaterSourceReport) fromPrevActivity).getWaterCondition().toString());
        } else if (fromPrevActivity instanceof WaterPurityReport) {
            info.append("Overall Condition: ");
            info.append((((WaterPurityReport) fromPrevActivity)
                    .getReportedOverallWaterCondition().toString()));
            info.append("\n");
            info.append("Virus PPM: ");
            info.append(((WaterPurityReport) fromPrevActivity).getReportedVirusPPM());
            info.append("\n");
            info.append("Contaminant PPM: ");
            info.append(((WaterPurityReport) fromPrevActivity).getReportedContaminantPPM());
        }



        dateSet.setText(date);
        timeSet.setText(time);
        nameSet.setText(name);
        if (locationReceived != null) {
            titleSet.setText("Water Report: " + locationReceived.getPlaceName());
            strAddSet1.setText(locationReceived.getStreetAddress1());
            strAddSet2.setText(locationReceived.getStreetAddress2());
            citySet.setText(locationReceived.getCity());
            stateSet.setText(locationReceived.getStateOrProvince());
            countrySet.setText(locationReceived.getCountry());
            zipCodeSet.setText(locationReceived.getZipCode());
        } else {
            titleSet.setText("Water Report");
        }

        waterInfo.setText(info.toString());

        backToRepBut.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(ViewReportActivity.this, ViewReportsActivity.class));
                finish();
            }
        });
    }
}
