package com.project.sustain.controllers;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.IdRes;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.RadioGroup;

import com.project.sustain.R;

public class ChooseReportActivity extends AppCompatActivity {
    private String reportType = "source";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_choose_report);

        Button btnCancel = (Button) findViewById(R.id.btnSelectReportCancel);
        Button btnOK = (Button) findViewById(R.id.btnSelectReportOK);

        RadioGroup reportChoices = (RadioGroup) findViewById(R.id.radGrpReportListType);
        reportChoices.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, @IdRes int checkedId) {
                switch (checkedId) {
                    case R.id.radBtnReportListPurityReport:
                        reportType = "purity";
                        break;
                    case R.id.radBtnReportListSourceReport:
                        reportType = "source";
                        break;

                }
            }
        });

        reportChoices.check(R.id.radBtnReportListSourceReport); //set SourceReport as default type

        btnCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        btnOK.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(ChooseReportActivity.this, ViewReportsActivity.class);
                intent.putExtra("reportType", reportType);
                startActivity(intent);
                finish();
            }
        });


    }
}
