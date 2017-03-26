package com.project.sustain.controllers;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.Button;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.project.sustain.R;
import com.project.sustain.model.WaterSourceReport;

import java.util.ArrayList;
import java.util.List;

//import com.google.android.gms.vision.text.Text;


/**
 * Created by georgiainstituteoftechnology on 3/6/17.
 */

public class ViewReportsActivity extends AppCompatActivity {
    private FirebaseDatabase fireBaseDatabase;
    private DatabaseReference waterReportsRef;
    private DatabaseReference waterReports;
    private List<WaterSourceReport> mWaterSourceReportList = new ArrayList<WaterSourceReport>();
    private RecyclerView wtrRepRecyclerView;
    private WaterReportAdapter wRAdapter;
    private Button backButton;
//    private TextView toCheck;
//    private ScrollView toMess;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.viewwaterreports);

        backButton = (Button) findViewById(R.id.backButtonWtrRepList);

        backButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(ViewReportsActivity.this, MainActivity.class));
            }
        });

        fireBaseDatabase = FirebaseDatabase.getInstance();
        waterReportsRef = fireBaseDatabase.getReference().child("waterReports");

//        toCheck = (TextView) findViewById(R.id.databaseTest);
//        toMess = (ScrollView) findViewById(R.id.masterScroll);
        wtrRepRecyclerView = (RecyclerView) findViewById(R.id.recycler_view);

        wRAdapter = new WaterReportAdapter(mWaterSourceReportList);
        RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(getApplicationContext());
        wtrRepRecyclerView.setLayoutManager(mLayoutManager);
        wtrRepRecyclerView.setItemAnimator(new DefaultItemAnimator());
        wtrRepRecyclerView.setAdapter(wRAdapter);

        waterReportsRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                for (DataSnapshot wtrRepSnapshot: dataSnapshot.getChildren()) {
                    WaterSourceReport toPaste = wtrRepSnapshot.getValue(WaterSourceReport.class);
                    mWaterSourceReportList.add(toPaste);
                    wRAdapter.notifyDataSetChanged();
                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
//                toCheck.setText(databaseError.getMessage());
            }
        });

        wtrRepRecyclerView.addOnItemTouchListener(new WaterReportRecyclerTouchListener(getApplicationContext(),
                wtrRepRecyclerView, new WaterReportRecyclerTouchListener.ClickListener() {
            @Override
            public void onClick(View view, int position) {
                WaterSourceReport waterSourceReportClicked = mWaterSourceReportList.get(position);
                //intent to be created.
                Intent intent = new Intent(ViewReportsActivity.this, ViewReportActivity.class);
                intent.putExtra("report", waterSourceReportClicked);
                startActivity(intent);
            }

            @Override
            public void onLongClick(View view, int position) {}
        }));
    }
}

