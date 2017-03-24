package com.project.sustain.controllers;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.AppCompatActivity;

import com.project.sustain.R;
import com.project.sustain.model.Report;
import com.project.sustain.model.WaterSourceReport;

/**
 * Created by Marcia on 3/23/2017.
 * This is the activity that runs the Fragments for submission of different report types.
 */

public class ReportActivity extends AppCompatActivity {
    private Report mReport;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_report);
        //there should be a Report object waiting for us.
        mReport = (Report) getIntent().getSerializableExtra("report");
        if (mReport == null) {
            mReport = new WaterSourceReport(); //default
        }

        //get FragmentManager (Android)
        FragmentManager fm = getSupportFragmentManager();

        //see if there is already a fragment loaded into the frame container
        Fragment fragment = fm.findFragmentById(R.id.fragment_container);
        if (fragment == null) {
            if (mReport instanceof WaterSourceReport) {
                fragment = new SubmitWaterReportFragment();
            } else {
                fragment = new SubmitPurityReportFragment();
            }
            fm.beginTransaction()
                    .add(R.id.fragment_container, fragment)
                    .commit();
        }

    }
}
