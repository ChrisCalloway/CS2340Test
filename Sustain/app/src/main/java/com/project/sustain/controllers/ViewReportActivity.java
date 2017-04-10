package com.project.sustain.controllers;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.TextView;

import com.project.sustain.R;
import com.project.sustain.model.Report;
import com.project.sustain.model.WaterPurityReport;
import com.project.sustain.model.WaterSourceReport;

/**
 * Created by Anish on 3/7/17.
 */

public class ViewReportActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_single_report);

        //gets the Report passed in as a serialized object
        final Intent intent = getIntent();
        Report fromPrevActivity = (Report) intent.getSerializableExtra("report");

        //loads the report text into the TextView
        if (fromPrevActivity != null) {
            TextView txtReportText = (TextView) findViewById(R.id.txtReportText);
            if (fromPrevActivity instanceof WaterSourceReport) {
                txtReportText.setText(((WaterSourceReport) fromPrevActivity).toString());
            } else if (fromPrevActivity instanceof WaterPurityReport) {
                txtReportText.setText(((WaterPurityReport) fromPrevActivity).toString());
            }
        }

    }
}
