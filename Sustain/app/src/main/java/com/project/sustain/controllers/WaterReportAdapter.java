package com.project.sustain.controllers;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.project.sustain.R;
import com.project.sustain.model.WaterReport;

import java.util.List;

/**
 * Created by georgiainstituteoftechnology on 3/7/17.
 */

// TODO: 3/8/17 Ask Anish/Julio about this, why this is not in ViewReportsActivity 
public class WaterReportAdapter extends RecyclerView.Adapter<WaterReportAdapter.WaterViewHolder>{
    private List<WaterReport> wtrReportList;

    // Inner class?
    public class WaterViewHolder extends RecyclerView.ViewHolder {
        public TextView reportNumText;
        public TextView reportDateText;

        public WaterViewHolder(View view) {
            super(view);
            reportNumText = (TextView) view.findViewById(R.id.reportNum);
            reportDateText = (TextView) view.findViewById(R.id.reportDate);
        }
    }

    // Constructor, expects a List of WaterReport objects
    public WaterReportAdapter(List<WaterReport> wtrReportList) {
        this.wtrReportList = wtrReportList;
    }

    @Override
    public WaterViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View reportView = LayoutInflater.from(parent.getContext()).inflate(R.layout.water_report_list_row, parent, false);
        return new WaterViewHolder(reportView);
    }

    @Override
    public void onBindViewHolder(WaterViewHolder holder, int position) {
        WaterReport waterReport = wtrReportList.get(position);
        holder.reportNumText.setText("Water Report #" + waterReport.getReportNumber());
        holder.reportDateText.setText(waterReport.getDate());
    }

    @Override
    public int getItemCount() {
        return wtrReportList.size();
    }
}
