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
import com.project.sustain.model.WaterCondition;
import com.project.sustain.model.WaterSourceReport;
import com.project.sustain.model.WaterType;

/**
 * Created by Marcia on 3/22/2017.
 */

public class SubmitWaterReportFragment extends Fragment {
    private WaterSourceReport mWaterSourceReport;
    private EditText placeName;
    private Spinner spnWaterType;
    private Spinner spnWaterCondition;


    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mWaterSourceReport = (WaterSourceReport) getActivity().getIntent().getSerializableExtra("report");
        //note: layout is inflated in a separate method, onCreateView.
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        //set the layout xml for this fragment, but don't show it yet (false).
        //view will be added and shown by the activity.
        View v = inflater.inflate(R.layout.fragment_submit_water_report, container, false);

        placeName = (EditText) v.findViewById(R.id.editPlaceName);
        spnWaterType = (Spinner) v.findViewById(R.id.spinWaterType);
        spnWaterCondition = (Spinner) v.findViewById(R.id.spinWaterCondition);
        spnWaterType.setAdapter(new ArrayAdapter<>(this.getContext(),
                R.layout.support_simple_spinner_dropdown_item,
                WaterType.values()));
        spnWaterCondition.setAdapter(new ArrayAdapter<>(this.getContext(),
                R.layout.support_simple_spinner_dropdown_item,
                WaterCondition.values()));
        if (mWaterSourceReport != null) {
            if (mWaterSourceReport.getAddress() != null) {
                String place = mWaterSourceReport.getAddress().getPlaceName();
                placeName.setText(place);
            }
        }

        return v;

    }
}
