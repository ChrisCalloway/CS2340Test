package com.project.sustain.controllers;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Button;

import com.project.sustain.R;
import com.project.sustain.model.Report;
import com.project.sustain.model.WaterReportManager;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by derek on 4/5/17.
 */

public class WaterSourceReportsActivity extends AppCompatActivity{
    private List<Report> mReportList;
    private RecyclerView wtrRepRecyclerView;
    private WaterReportAdapter wRAdapter;
    private Button backButton;
    private WaterReportManager mReportManager;
    private QueryListResultListener qrListener;
    private Toolbar mToolbar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.viewwaterreports);

        //add Toolbar as ActionBar with menu
        mToolbar = (Toolbar) findViewById(R.id.activity_water_source_toolbar);
        mToolbar.setTitle("Water Source Reports");
        this.setSupportActionBar(mToolbar);

        //String reportTypeToShow = getIntent().getStringExtra("reportType");

        //backButton = (Button) findViewById(R.id.backButtonWtrRepList);

        /*backButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
                //MainActivity should still be running
            }
        });
        */
        mReportManager = new WaterReportManager();
        mReportList = new ArrayList<>();
        wRAdapter = new WaterReportAdapter(mReportList);

        qrListener = new QueryListResultListener() {
            @Override
            public <T, K> void onComplete(T item, K key) {
                ((Report) item).setReportId((String)key);
                mReportList.add((Report)item);
                wRAdapter.notifyDataSetChanged();

            }

            @Override
            public void onError(Throwable error) {

            }
        };
        mReportManager.setQueryListResultListener(qrListener);
        mReportManager.getWaterSourceReports();
        wtrRepRecyclerView = (RecyclerView) findViewById(R.id.recycler_view);

        RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(getApplicationContext());
        wtrRepRecyclerView.setLayoutManager(mLayoutManager);
        wtrRepRecyclerView.setItemAnimator(new DefaultItemAnimator());
        wtrRepRecyclerView.setAdapter(wRAdapter);


        /*wtrRepRecyclerView.addOnItemTouchListener(new WaterReportRecyclerTouchListener(getApplicationContext(),
                wtrRepRecyclerView, new WaterReportRecyclerTouchListener.ClickListener() {
            @Override
            public void onClick(View view, int position) {
                Report reportClicked = mReportList.get(position);
                //intent to be created.
                Intent intent = new Intent(this, ViewReportActivity.class);
                intent.putExtra("report", reportClicked);
                startActivity(intent);
            }

            @Override
            public void onLongClick(View view, int position) {}
        }));*/
    }

    @Override
    protected void onStop() {
        if (mReportManager != null) {
            mReportManager.removeQueryListResultListener();
        }
        super.onStop();
    }

    @Override
    protected void onResume() {
        if (mReportManager != null && qrListener != null) {
            mReportManager.setQueryListResultListener(qrListener);
        }
        super.onResume();
    }
}
