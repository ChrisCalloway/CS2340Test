package com.project.sustain.controllers;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.project.sustain.R;
import com.project.sustain.model.WaterSourceReport;

/**
 * Created by Marcia on 3/22/2017.
 */

public class SubmitWaterReportFragment extends Fragment {
    private WaterSourceReport mWaterSourceReport;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mWaterSourceReport = new WaterSourceReport();
        //note: layout is inflated in a separate method, onCreateView.
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        //set the layout xml for this fragment, but don't show it yet (false).
        //view will be added and shown by the activity.
        return inflater.inflate(R.layout.activity_get_address, container, false);
    }
}
