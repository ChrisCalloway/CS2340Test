package com.project.sustain.controllers;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.view.View;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import com.project.sustain.R;
import com.project.sustain.model.ReportManagementFacade;
import com.project.sustain.model.WaterType;
import com.project.sustain.model.WaterReport;
import com.project.sustain.model.WaterCondition;

public class SubmitWaterSourceReportActivity extends AppCompatActivity {
    private Spinner spinnerWaterType;
    private Spinner spinnerWaterCondition;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_submit_water_source_report);

        // Back the spinner with an array list
        spinnerWaterType = (Spinner) findViewById(R.id.spinnerWaterType);
        spinnerWaterType.setAdapter(new ArrayAdapter<WaterType>(this,
                R.layout.support_simple_spinner_dropdown_item, WaterType.values()));

        spinnerWaterCondition = (Spinner) findViewById(R.id.spinnerWaterCondition);
        spinnerWaterCondition.setAdapter(new ArrayAdapter<WaterCondition>(this,
                R.layout.support_simple_spinner_dropdown_item, WaterCondition.values()));
    }

    /**
     * Handle user click to submit the report
     * @view The view that was clicked on the screen
     */
    public void addWaterSourceReportButtonClick(View view) {
        // Get the singleton intance of the report manager facade to be able to make call
        // to water report manager
        ReportManagementFacade reportManagementFacade = ReportManagementFacade
                .getReportManagementInstance();

        // Get values inputted by user to pass on to the report manager facade
        String reporterName = ((EditText) findViewById(R.id.editReporterNameWaterSource))
                .getText().toString();
        double reportedLatitude = Double
                .parseDouble(((EditText) findViewById(R.id.editLatitude)).getText().toString());
        double reportedLongitude = Double
                .parseDouble(((EditText) findViewById(R.id.editLongitude)).getText().toString());
        WaterType reportedWaterType = (WaterType) spinnerWaterType.getSelectedItem();
        WaterCondition reportedWaterCondition = (WaterCondition) spinnerWaterCondition
                .getSelectedItem();

        // Make call down to the WaterReportManager to add a new water source report
        reportManagementFacade.addNewWaterSourceReport(reporterName, reportedLatitude,
                reportedLongitude, reportedWaterType, reportedWaterCondition);

        // Display to the User that the water source report was saved
        Toast displayToast = Toast.makeText(getApplicationContext(), "Added water source report",
                Toast.LENGTH_SHORT);
        displayToast.show();
    }
}
