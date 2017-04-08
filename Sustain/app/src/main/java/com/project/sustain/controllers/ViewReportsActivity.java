package com.project.sustain.controllers;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;

import com.project.sustain.R;
import com.project.sustain.model.Report;
import com.project.sustain.model.User;
import com.project.sustain.model.WaterReportManager;

import java.util.ArrayList;
import java.util.List;

//import com.google.android.gms.vision.text.Text;


/**
 * Created by georgiainstituteoftechnology on 3/6/17.
 */

public class ViewReportsActivity extends AppCompatActivity {
    private List<Report> mReportList;
    private WaterReportAdapter wRAdapter;
    private Button backButton;
    private WaterReportManager mReportManager;
    private QueryListResultListener qrListener;
    private User mUser;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_water_source);


        String reportTypeToShow = getIntent().getStringExtra("reportType");
        mUser = (User) getIntent().getSerializableExtra("user");
        //add Toolbar as ActionBar with menu
        Toolbar mToolbar = (Toolbar) findViewById(R.id.activity_water_source_toolbar);
        mToolbar.setTitle("Water Source Reports");
        this.setSupportActionBar(mToolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);

        FloatingActionButton mFab = (FloatingActionButton) findViewById(R.id.activity_water_source_fab);
        mFab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(ViewReportsActivity.this, SetAddressActivity.class)
                        .putExtra("user", mUser));
            }
        });

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

        if (reportTypeToShow.equals("source")) {
            mReportManager.getWaterSourceReports();
        } else if (reportTypeToShow.equals("purity")) {
            mReportManager.getWaterPurityReports();
        }

        RecyclerView wtrRepRecyclerView = (RecyclerView) findViewById(R.id.activity_water_source_recycler_view);


        RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(getApplicationContext());
        wtrRepRecyclerView.setLayoutManager(mLayoutManager);
        wtrRepRecyclerView.setItemAnimator(new DefaultItemAnimator());
        wtrRepRecyclerView.setAdapter(wRAdapter);


        wtrRepRecyclerView.addOnItemTouchListener(new WaterReportRecyclerTouchListener(getApplicationContext(),
                wtrRepRecyclerView, new WaterReportRecyclerTouchListener.ClickListener() {
            @Override
            public void onClick(View view, int position) {
                Report reportClicked = mReportList.get(position);
                //intent to be created.
                Intent intent = new Intent(ViewReportsActivity.this, ViewReportActivity.class);
                intent.putExtra("report", reportClicked);
                startActivity(intent);
            }

            @Override
            public void onLongClick(View view, int position) {}
        }));
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

    //Back button on Toolbar
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                finish();
                return true;
            default:
                // If we got here, the user's action was not recognized.
                // Invoke the superclass to handle it.
                return super.onOptionsItemSelected(item);

        }
    }
}

