package com.project.sustain.controllers;

import android.support.v4.app.FragmentActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.gms.maps.model.Marker;

import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.project.sustain.R;
import com.project.sustain.model.WaterReport;
import com.project.sustain.model.WaterType;
import com.project.sustain.model.WaterCondition;


public class MapsMarkerActivity extends FragmentActivity implements OnMapReadyCallback {


    // The entry point to Google Play services, used by the Places API and Fused Location Provider.
    private GoogleMap mMap;


    // Reference to the water reports child in the Firebase Database
    private DatabaseReference mWaterReportsRef =
            FirebaseDatabase.getInstance().getReference("waterReports");

    private ChildEventListener mChildEventListener;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // Retrieve the content view thar renders the map
        setContentView(R.layout.activity_maps_marker);

        // Get the SupportMapFragrment and request notification when the map is ready to be used.
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);

    }

    /**
     * Manipulates the map when it's available.  This callback
     * is triggered when the map is ready to be used.
     * This is where we can add markers or lines, add listeners or move the camera.  In this
     * case, we are just going to add a marker near Sydney, Australia.
     */
    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;


//        // Setting a click event hanlder for the map
//        mMap.setOnMapClickListener(new GoogleMap.OnMapClickListener() {
//
//            @Override
//            public void onMapClick(LatLng latLng) {
//
//                // Creating a marker
//                MarkerOptions markerOptions = new MarkerOptions();
//
//                // Setting the position for the marker
//                markerOptions.position(latLng);
//
//                // Setting the title for the marker
//                // This is displayed on tapping the marker
//                // This should be retrieved from the corresponding report
//                markerOptions.title("Title Test");
//                markerOptions.snippet("Description Test");
//                // This will
//
//                // Animating the touched position
//                mMap.animateCamera(CameraUpdateFactory.newLatLng(latLng));
//
//                // Placing a marker on the touched position
//                mMap.addMarker(markerOptions);
//
//            }
//        });

        // Get marker from Firebase Database and add to map
         addMarkersToMap(mMap);

        // Here is where we populate the map with markers for already created reports
        // get list of reports
        // iterate through list of reports where for each report, do mMap.addMarker()
//        List<Report> reportList = mFacade.getReports();
//        for (Report r : reportList) {
        // Will need to get the latitude and longitude from the water report
//            LatLng loc = new LatLng(r.getLatitude(), r.getLongitude());
        // Will need to figure how to present the information for the report using just the title
        // and the snippet?  Will need to show the report number, date, time, reporter's name, water type, water condition
//            mMap.addMarker(new MarkerOptions().position(loc).title(r.getName()).snippet(r.getDescription()));
//            mMap.moveCamera(CameraUpdateFactory.newLatLng(loc));
//        }

        mMap.setInfoWindowAdapter(new CustomInfoWindowAdapter());

    }

    private void addMarkersToMap(GoogleMap googleMap) {
        final GoogleMap map = googleMap;
        mChildEventListener = mWaterReportsRef.addChildEventListener(new ChildEventListener() {
            // On adding a child, a marker is added to the map
            @Override
            public void onChildAdded(DataSnapshot dataSnapshot, String s) {
                WaterReport addedWaterReport = dataSnapshot.getValue(WaterReport.class);
                String date = addedWaterReport.getDate();
                String time = addedWaterReport.getTime();
                int reportNumber = addedWaterReport.getReportNumber();
                double latitude = addedWaterReport.getLocation().getLatitude();
                double longitude = addedWaterReport.getLocation().getLongitude();
                LatLng location = new LatLng(latitude, longitude);
                WaterType waterType = addedWaterReport.getTypeWater();
                WaterCondition waterCondition = addedWaterReport.getConditionWater();
                map.addMarker(new MarkerOptions().position(location)
                        .title("Report Number: " + Integer.toString(reportNumber)
                                + "\n" + date + "\n" + time)
                        .snippet(waterType.toString() + "\n" + waterCondition.toString()));
            }

            @Override
            public void onChildChanged(DataSnapshot dataSnapshot, String s) {

            }

            @Override
            public void onChildRemoved(DataSnapshot dataSnapshot) {

            }

            @Override
            public void onChildMoved(DataSnapshot dataSnapshot, String s) {

            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
    }

    // This class is only used in this activity
    class CustomInfoWindowAdapter implements GoogleMap.InfoWindowAdapter {
        private final View myContentsView;

        // Constructor
        CustomInfoWindowAdapter() {
            myContentsView = getLayoutInflater().inflate(R.layout.custom_info_contents, null);
        }

        @Override
        public View getInfoContents(Marker marker) {
            // Populate the info for the marker

//            .title(Integer.toString(reportNumber) + "\n" + date + "\n" + time)
//                    .snippet(waterType.toString() + "\n" + waterCondition.toString()));
            TextView textReportNumber = ((TextView)myContentsView.findViewById(R.id.customMarkerTitle));
            textReportNumber.setText(marker.getTitle());
            TextView textSnippet = ((TextView)myContentsView.findViewById(R.id.customMarkerSnippet));
            textSnippet.setText(marker.getSnippet());
//            textSnippet.setText("This is a test of using values \nacross multiple\n lines");

            // The snippet will

            return myContentsView;
        }

        @Override
        public View getInfoWindow(Marker marker) {
            return null;
        }

    }
}
