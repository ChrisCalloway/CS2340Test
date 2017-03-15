package com.project.sustain.controllers;

import android.support.v4.app.FragmentActivity;
import android.os.Bundle;

import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.model.MarkerOptions;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.project.sustain.R;
import com.project.sustain.model.WaterReport;


public class MapsMarkerActivity extends FragmentActivity implements OnMapReadyCallback {

    private FirebaseDatabase fireBaseDatabase;
    private DatabaseReference waterReportsRef;

    // The entry point to Google Play services, used by the Places API and Fused Location Provider.
    private GoogleMap mMap;


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

        // Setting a click event hanlder for the map
        mMap.setOnMapClickListener(new GoogleMap.OnMapClickListener() {

            @Override
            public void onMapClick(LatLng latLng) {

                // Creating a marker
                MarkerOptions markerOptions = new MarkerOptions();

                // Setting the position for the marker
                markerOptions.position(latLng);

                // Setting the title for the marker

            }
        });

    }

}
