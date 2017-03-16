package com.project.sustain.controllers;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.Button;
import android.widget.ScrollView;
import android.widget.TextView;

import com.google.android.gms.vision.text.Text;
import android.support.v7.recyclerview.*;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.project.sustain.R;
import com.project.sustain.model.WaterReport;

import java.util.ArrayList;
import java.util.List;


/**
 * Created by georgiainstituteoftechnology on 3/6/17.
 */

public class ViewReportsActivity extends AppCompatActivity {
//    private FirebaseUser fireBaseUser;
    private FirebaseDatabase fireBaseDatabase;
    private DatabaseReference waterReportsRef;
    private DatabaseReference waterReports;
    private List<WaterReport> waterReportList = new ArrayList<WaterReport>();
    private RecyclerView wtrRepRecyclerView;
    private WaterReportAdapter wRAdapter;
    private Button backButton;

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

        //
//        fireBaseUser = FirebaseAuth.getInstance().getCurrentUser();
        fireBaseDatabase = FirebaseDatabase.getInstance();
        waterReportsRef = fireBaseDatabase.getReference().child("waterReports");

        wtrRepRecyclerView = (RecyclerView) findViewById(R.id.recycler_view);

        wRAdapter = new WaterReportAdapter(waterReportList);

        RecyclerView.LayoutManager mLayoutManager =
                new LinearLayoutManager(getApplicationContext());

        wtrRepRecyclerView.setLayoutManager(mLayoutManager);
        wtrRepRecyclerView.setItemAnimator(new DefaultItemAnimator());
        wtrRepRecyclerView.setAdapter(wRAdapter);

        waterReportsRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                for (DataSnapshot wtrRepSnapshot: dataSnapshot.getChildren()) {
                    WaterReport toPaste = wtrRepSnapshot.getValue(WaterReport.class);
                    waterReportList.add(toPaste);
                    wRAdapter.notifyDataSetChanged();
                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
//                toCheck.setText(databaseError.getMessage());
            }
        });

        wtrRepRecyclerView.addOnItemTouchListener(
                new WaterReportRecyclerTouchListener(getApplicationContext(),
                        wtrRepRecyclerView, new WaterReportRecyclerTouchListener.ClickListener() {
            @Override
            public void onClick(View view, int position) {
                WaterReport waterReportClicked = waterReportList.get(position);
                //intent to be created.
                Intent intent = new Intent(ViewReportsActivity.this, ViewReportActivity.class);

                // Will use the same string as an identifier for the water source report clicked
                // in this view to show its data in the ViewReportActivity
                intent.putExtra("waterReportIntentData", waterReportClicked);
                startActivity(intent);
            }

            @Override
            public void onLongClick(View view, int position) {}
        }));
    }
}

