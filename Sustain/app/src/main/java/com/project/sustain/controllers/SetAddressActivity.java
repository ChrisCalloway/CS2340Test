package com.project.sustain.controllers;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.RadioGroup;

import com.project.sustain.R;
import com.project.sustain.model.UserType;

/**
 * Created by Marcia on 3/22/2017.
 */

public class SetAddressActivity extends AppCompatActivity {
    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        UserType userType = UserType.WORKER; //just to test it

        setContentView(R.layout.activity_get_address);
        RadioGroup reportOptions = (RadioGroup) findViewById(R.id.radReportType);
        reportOptions.setVisibility((userType != UserType.USER ? View.VISIBLE : View.GONE));
        if (reportOptions.getVisibility() == View.VISIBLE) {
            reportOptions.check(R.id.radbtnWaterReport); //set Water Report as default.
        }
    }
    
}
