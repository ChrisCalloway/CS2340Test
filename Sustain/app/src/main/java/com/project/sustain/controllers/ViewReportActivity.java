package com.project.sustain.controllers;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.project.sustain.R;
import com.project.sustain.model.Address;
import com.project.sustain.model.ReportLocation;
import com.project.sustain.model.WaterCondition;
import com.project.sustain.model.WaterReport;
import com.project.sustain.model.WaterType;

/**
 * Created by georgiainstituteoftechnology on 3/7/17.
 */

public class ViewReportActivity extends AppCompatActivity {
    private String date;
    private String time;
    private int reportNum;
    private String name;
    private Double latitude;
    private Double longitude;
//    private ReportLocation locationReceived;
    private WaterType typeReceived;
    private WaterCondition conditionReceived;
    private TextView titleSet;
    private TextView dateSet;
    private TextView timeSet;
    private TextView nameSet;
    private TextView typeSet;
    private TextView conditionSet;
    private TextView latitudeSet;
    private TextView longitudeSet;
    private Button backToRepBut;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.viewwaterreport);

        // Placeholders for data to display relevant for water report clicked
        titleSet = (TextView) findViewById(R.id.wtrRepTitle);
        dateSet = (TextView) findViewById(R.id.dateReported);
        timeSet = (TextView) findViewById(R.id.timeReported);
        nameSet = (TextView) findViewById(R.id.nameReported);
        typeSet = (TextView) findViewById(R.id.typeReported);
        conditionSet = (TextView) findViewById(R.id.conditionReported);
        latitudeSet = (TextView) findViewById(R.id.latitudeReported);
        longitudeSet = (TextView) findViewById(R.id.longitudeReported);
        backToRepBut = (Button) findViewById(R.id.backButton);

        //gets the package passed in as an intent and initializes the instance data from the package
        final Intent intent = getIntent();

        // This is grabbing the instance data from the Water Source Report clicked and assigning
        // the field values to the various widgets to be displayed in this activity.
        WaterReport fromPrevActivity =
                (WaterReport) intent.getParcelableExtra("waterReportIntentData");
        date = fromPrevActivity.getDate();
        time = fromPrevActivity.getTime();
        reportNum = fromPrevActivity.getReportNumber();
        name = fromPrevActivity.getName();
        typeReceived = fromPrevActivity.getTypeWater();
        conditionReceived = fromPrevActivity.getConditionWater();
        latitude = fromPrevActivity.getLocation().getLatitude();
        longitude = fromPrevActivity.getLocation().getLongitude();

        // Setting the widgets in the activity to the values from the water source report
        // that was clicked on in the ViewReportsActivity.
        titleSet.setText("Water Report #" + reportNum);
        dateSet.setText(date);
        timeSet.setText(time);
        nameSet.setText(name);
        typeSet.setText(typeReceived.toString());
        conditionSet.setText(conditionReceived.toString());
        latitudeSet.setText(Double.toString(latitude));
        longitudeSet.setText(Double.toString(longitude));

        // Button to enable user to navigate back to the list of water source reports
        backToRepBut.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(ViewReportActivity.this, ViewReportsActivity.class));
                finish();
            }
        });
    }
}
