package com.project.sustain.controllers;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.project.sustain.R;
import com.project.sustain.model.Report;

import java.util.List;

/**
 * Created by Anish on 3/7/17.
 */

public class WaterReportAdapter extends RecyclerView.Adapter<WaterReportAdapter.WaterViewHolder>{
    private List<Report> wtrReportList;

    public class WaterViewHolder extends RecyclerView.ViewHolder {
        public TextView reportNumText;
        public TextView reportDateText;
        public TextView reportPlaceText;

        public WaterViewHolder(View view) {
            super(view);
            reportPlaceText = (TextView) view.findViewById(R.id.reportPlace);
            reportNumText = (TextView) view.findViewById(R.id.reportNum);
            reportDateText = (TextView) view.findViewById(R.id.reportDate);
        }
    }

    public WaterReportAdapter(List<Report> wtrReportList) {
        this.wtrReportList = wtrReportList;
    }

    @Override
    public WaterViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View reportView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.water_report_list_row, parent, false);
        return new WaterViewHolder(reportView);
    }

    @Override
    public void onBindViewHolder(WaterViewHolder holder, int position) {
        Report waterReport = wtrReportList.get(position);
        holder.reportPlaceText.setText(waterReport.getAddress().getPlaceName());
        holder.reportNumText.setText(waterReport.getReportId());
        holder.reportDateText.setText(waterReport.getDateOfReport());
    }

    @Override
    public int getItemCount() {
        return wtrReportList.size();
    }
}
