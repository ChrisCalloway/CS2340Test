package com.project.sustain.controllers;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import com.project.sustain.R;
import com.project.sustain.model.OverallWaterCondition;
import com.project.sustain.model.WaterPurityReport;
import com.project.sustain.model.WaterReportManager;

public class PurityReportActivity extends AppCompatActivity {
    private WaterReportManager mReportManager;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.purity_report);

        //basic data for the report was given to this activity
        //by SetAddressActivity.
        final WaterPurityReport report = (WaterPurityReport) getIntent()
                .getSerializableExtra("report");

        //display place name that was provided
        String placeName = report.getAddress().getPlaceName();
        EditText editPlace = (EditText) findViewById(R.id.editPlaceName);
        editPlace.setText(placeName);
        editPlace.setEnabled(false);

        //WaterReportManager will be responsible for saving
        //the report to the database.
        mReportManager = new WaterReportManager();

        final Spinner spinOverallCond = (Spinner) findViewById(R.id.spinOverallCondition);
        final EditText editVirusPpm = (EditText) findViewById(R.id.editVirusPpm);
        final EditText editContaminantPpm = (EditText) findViewById(R.id.editContaminantPpm);

        spinOverallCond.setAdapter(new ArrayAdapter<>(this,
                R.layout.support_simple_spinner_dropdown_item,
                OverallWaterCondition.values()));

        Button btnSave = (Button) findViewById(R.id.btnSave);

        btnSave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                report.setReportedContaminantPPM(Double.parseDouble(editContaminantPpm.getText()
                    .toString()));
                report.setReportedOverallWaterCondition((OverallWaterCondition)
                        spinOverallCond.getSelectedItem());
                report.setReportedVirusPPM(Double.parseDouble(editVirusPpm.getText().toString()));
                mReportManager.savePurityReport(report);
                Toast.makeText(getApplicationContext(), "Report saved.", Toast.LENGTH_SHORT).show();
                finish();
            }
        });

        Button btnCancel = (Button) findViewById(R.id.btnCancel);

        btnCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });


    }
}
