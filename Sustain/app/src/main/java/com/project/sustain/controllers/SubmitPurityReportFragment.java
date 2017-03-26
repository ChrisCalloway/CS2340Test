package com.project.sustain.controllers;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.Spinner;

import com.project.sustain.R;
import com.project.sustain.model.OverallWaterCondition;
import com.project.sustain.model.WaterPurityReport;

/**
 * Created by Marcia on 3/22/2017.
 */

public class SubmitPurityReportFragment extends Fragment {
    private WaterPurityReport mWaterPurityReport;
    private EditText editPlaceName;
    private Spinner spnOverallCondition;
    private EditText editVirusPpm;
    private EditText editContaminantPpm;


    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mWaterPurityReport = (WaterPurityReport) getActivity().getIntent().getSerializableExtra("report");
        //note: layout is inflated in a separate method, onCreateView.
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        //set the layout xml for this fragment, but don't show it yet (false).
        //view will be added and shown by the activity.
        View v = inflater.inflate(R.layout.purity_report, container, false);

        editPlaceName = (EditText) v.findViewById(R.id.editPlaceName);
        spnOverallCondition = (Spinner) v.findViewById(R.id.spinOverallCondition);
        spnOverallCondition.setAdapter(new ArrayAdapter<>(this.getContext(),
                R.layout.support_simple_spinner_dropdown_item,
                OverallWaterCondition.values()));
        if (mWaterPurityReport != null) {
            if (mWaterPurityReport.getAddress() != null) {
                String place = mWaterPurityReport.getAddress().getPlaceName();
                editPlaceName.setText(place);
            }
        }

        return v;

    }
}
