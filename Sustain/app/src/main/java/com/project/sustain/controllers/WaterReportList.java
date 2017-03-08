package com.project.sustain.controllers;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.project.sustain.R;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.project.sustain.model.UserProfile;
import com.project.sustain.model.WaterReport;

import java.util.ArrayList;
import java.util.Map;
import java.util.HashMap;
import java.util.List;


public class WaterReportList extends AppCompatActivity {

    private FirebaseAuth mFirebaseAuth;
    private FirebaseUser mFirebaseUser;
    private FirebaseDatabase mDatabase;
    private DatabaseReference mWaterReports;
    private Button btnCancel;
    private ListView waterReportListView;
    private List<String> waterReportsList;
    private ArrayAdapter<String> waterReportListAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_water_report_list);

        //get refs to Firebase user objects
        mFirebaseAuth = FirebaseAuth.getInstance();
        mFirebaseUser = mFirebaseAuth.getCurrentUser();
        mDatabase = FirebaseDatabase.getInstance();
        mWaterReports = mDatabase.getReference().child("waterReports");

        // Instantiate the view widgets
        waterReportListView = (ListView) findViewById(R.id.waterReportListView);
        btnCancel = (Button) findViewById(R.id.buttonWaterReportListCancel);

        waterReportsList = new ArrayList<>();
        waterReportListAdapter = new ArrayAdapter<String>(
                this,
                android.R.layout.simple_dropdown_item_1line,
                waterReportsList
        );

        waterReportListView.setAdapter(waterReportListAdapter);
//        waterReportListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
//            @Override
//            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
//                String currentEntry = ((TextView) view).getText().toString();
//            }
//        });


        mWaterReports.addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(DataSnapshot dataSnapshot, String s) {
                WaterReport addedWaterReport = dataSnapshot.getValue(WaterReport.class);
                waterReportsList.add("Report Number: " + addedWaterReport.getReportNumber()
                    + " by " + addedWaterReport.getName());
                waterReportListAdapter.notifyDataSetChanged();
            }

            @Override
            public void onChildChanged(DataSnapshot dataSnapshot, String s) {

            }

            @Override
            public void onChildRemoved(DataSnapshot dataSnapshot) {
                WaterReport deletedWaterReport = dataSnapshot.getValue(WaterReport.class);
                waterReportsList.remove("Report Number: " + deletedWaterReport.getReportNumber()
                        + " by " + deletedWaterReport.getName());
                waterReportListAdapter.notifyDataSetChanged();
            }

            @Override
            public void onChildMoved(DataSnapshot dataSnapshot, String s) {

            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });

        // Code to ensure this point of program execution was reached
//        System.out.println("Made it to WaterReportList.java");

        // Getting data from Firebase Database.  Note that the values come back
        // in a Map data type, must convert to list to populate this view?
//        mWaterReports.addValueEventListener(new ValueEventListener() {
//            @Override
//            public void onDataChange(DataSnapshot dataSnapshot) {
//                Map<String, WaterReport> waterReportsMap = (Map<String,WaterReport>) dataSnapshot.getValue();
//                List<String> waterReportsList = new ArrayList<String>();
//                List<WaterReport> waterReportsList = new ArrayList<WaterReport>(waterReportsMap.values());
//
//                // Code to ensure that values were being retrieved
//                for (Map.Entry<String, WaterReport> waterReport : waterReportsMap.entrySet()) {
//                    Map singleWaterReport = (Map) waterReport.getValue();
//                    if (singleWaterReport.get("name") != null) {
//                        System.out.println("reportee name is " + singleWaterReport.get("name"));
//                    }
//                    waterReportsList.add("Report Number: " + singleWaterReport.get("reportNumber")
//                        + " by " + singleWaterReport.get("name"));
//                }
//            }
//
//            @Override
//            public void onCancelled(DatabaseError databaseError) {
//
//            }
//        });


        btnCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(WaterReportList.this, MainActivity.class));
            }
        });
    }
}
