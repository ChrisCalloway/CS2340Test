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
import com.project.sustain.model.WaterCondition;
import com.project.sustain.model.WaterReportManager;
import com.project.sustain.model.WaterSourceReport;
import com.project.sustain.model.WaterType;

/**
 * Activity that handles viewing of the water source reports
 */
public class SourceReportActivity extends AppCompatActivity {
    private WaterReportManager mReportManager;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.source_report);

        //basic data for the report was given to this activity
        //by SetAddressActivity.
        final WaterSourceReport report = (WaterSourceReport) getIntent()
                .getSerializableExtra("report");

        //display place name that was provided
        String placeName = report.getAddress().getPlaceName();
        EditText editPlace = (EditText) findViewById(R.id.editPlaceName);
        editPlace.setText(placeName);
        editPlace.setEnabled(false);

        //WaterReportManager will be responsible for saving
        //the report to the database.
        mReportManager = new WaterReportManager();

        final Spinner spinWaterType = (Spinner) findViewById(R.id.spinWaterType);
        final Spinner spinWaterCondition = (Spinner) findViewById(R.id.spinWaterCondition);


        spinWaterType.setAdapter(new ArrayAdapter<>(this,
                R.layout.support_simple_spinner_dropdown_item,
                WaterType.values()));

        spinWaterCondition.setAdapter(new ArrayAdapter<>(this,
                R.layout.support_simple_spinner_dropdown_item,
                WaterCondition.values()));

        Button btnSave = (Button) findViewById(R.id.btnSave);

        btnSave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                report.setWaterType((WaterType) spinWaterType.getSelectedItem());
                report.setWaterCondition((WaterCondition)
                        spinWaterCondition.getSelectedItem());
                mReportManager.saveSourceReport(report);
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
