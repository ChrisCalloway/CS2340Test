package com.example.georgiainstituteoftechnology.locationdummy;

import android.*;
import android.Manifest;
import android.content.pm.PackageManager;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.ActivityCompat;
import android.support.v4.widget.TextViewCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.location.FusedLocationProviderApi;
import com.google.android.gms.location.LocationRequest;
import com.google.android.gms.location.LocationServices;

public class MainActivity extends AppCompatActivity implements GoogleApiClient.ConnectionCallbacks, GoogleApiClient.OnConnectionFailedListener, LocationListener {
    TextView latitudeText;
    TextView longitudeText;
    private FusedLocationProviderApi locationProvider = LocationServices.FusedLocationApi;
    private GoogleApiClient googleApiClient;
    private LocationRequest locationRequest = new LocationRequest();
    private Location mLastLocation;
    private double myLatitude;
    private double myLongitude;
    protected static final String TAG = "MainActivity";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        latitudeText = (TextView) findViewById(R.id.latitudeText);
        longitudeText = (TextView) findViewById(R.id.longitudeText);

        locationRequest.setInterval(1000);
        locationRequest.setFastestInterval(5000);
        locationRequest.setPriority(LocationRequest.PRIORITY_HIGH_ACCURACY);

        buildGoogleApiClient();

    }

    protected synchronized void buildGoogleApiClient() {
        googleApiClient = new GoogleApiClient.Builder(this)
                .addConnectionCallbacks(this)
                .addOnConnectionFailedListener(this)
                .addApi(LocationServices.API)
                .build();
    }

    @Override
    public void onConnected(@Nullable Bundle bundle) {
//        if (ActivityCompat.checkSelfPermission(this, android.Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, android.Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
//            // TODO: Consider calling
//
//            ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.ACCESS_FINE_LOCATION},100);
//            //    ActivityCompat#requestPermissions
//            // here to request the missing permissions, and then overriding
//            //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
//            //                                          int[] grantResults)
//            // to handle the case where the user grants the permission. See the documentation
//            // for ActivityCompat#requestPermissions for more details.
//
//        } else {
//            googleApiClient.connect();
//            mLastLocation = LocationServices.FusedLocationApi.getLastLocation(googleApiClient);
//            if (mLastLocation != null) {
//                myLatitude = mLastLocation.getLatitude();
//                latitudeText.setText("latitude: " + myLatitude);
//                myLongitude = mLastLocation.getLongitude();
//                longitudeText.setText("longitude: " + myLongitude);
//            } else {
//                latitudeText.setText("location not detected");
//            }
//        }
        int permission = ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION);
        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION)
                != PackageManager.PERMISSION_GRANTED) {
            // Check Permissions Now
//            final int REQUEST_LOCATION = 2;
            ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.ACCESS_FINE_LOCATION}, 3);
            // permission has been granted, continue as usual
        } else {
            googleApiClient.connect();
//            LocationServices.FusedLocationApi.requestLocationUpdates(googleApiClient, locationRequest, (com.google.android.gms.location.LocationListener) this);
            mLastLocation = LocationServices.FusedLocationApi.getLastLocation(googleApiClient);
            myLatitude = mLastLocation.getLatitude();
            latitudeText.setText("Your Latitude: " + myLatitude);
            myLongitude = mLastLocation.getLongitude();
            longitudeText.setText("Your Longitude: " + myLongitude);
        }
//            Location myLocation =
//

    }

    @Override
    public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults) {
        int REQUEST_LOCATION = 3;
        if (requestCode == REQUEST_LOCATION) {
            if(grantResults.length == 1 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                // We can now safely use the API we requested access to
//                LocationServices.FusedLocationApi.(googleApiClient, locationRequest, (com.google.android.gms.location.LocationListener) this);
                mLastLocation = LocationServices.FusedLocationApi.getLastLocation(googleApiClient);
                myLatitude = mLastLocation.getLatitude();
                latitudeText.setText("Your Latitude is: " + myLatitude);
                myLongitude = mLastLocation.getLongitude();
                longitudeText.setText("Your Longitude is: " + myLongitude);
            }
        } else {
            // Permission was denied or request was cancelled
        }
    }



//    private void requestLocationUpdates() {
////        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
////            return;
////        }
//        if (ActivityCompat.checkSelfPermission(this, android.Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, android.Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
//            // TODO: Consider calling
//            //    ActivityCompat#requestPermissions
//            // here to request the missing permissions, and then overriding
//            //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
//            //                                          int[] grantResults)
//            // to handle the case where the user grants the permission. See the documentation
//            // for ActivityCompat#requestPermissions for more details.
//            return;
//        }
//        LocationServices.FusedLocationApi.requestLocationUpdates(googleApiClient, locationRequest, (com.google.android.gms.location.LocationListener) this);
//
//    }

    @Override
    public void onConnectionSuspended(int i) {
        Log.i(TAG, "Connection suspended");
        googleApiClient.connect();
        System.out.println("Hello World");
    }

    @Override
    public void onConnectionFailed(@NonNull ConnectionResult connectionResult) {
        Log.i(TAG, "Connection suspended");
        googleApiClient.connect();
    }

    @Override
    public void onLocationChanged(Location location) {
        myLatitude = location.getLatitude();
        myLongitude = location.getLongitude();
        latitudeText.setText("Latitude : " + String.valueOf(myLatitude));
        longitudeText.setText("Longitude : " + String.valueOf(myLongitude));
    }

    @Override
    public void onStatusChanged(String provider, int status, Bundle extras) {

    }

    @Override
    public void onProviderEnabled(String provider) {

    }

    @Override
    public void onProviderDisabled(String provider) {

    }

    @Override
    protected void onStart() {
        super.onStart();
        googleApiClient.connect();
    }

    @Override
    protected void onResume() {
        super.onResume();
//        if (googleApiClient.isConnected()) {
//            requestLocationUpdates();
//        }
    }

    @Override
    protected void onPause() {
        super.onPause();
//        LocationServices.FusedLocationApi.requestLocationUpdates(googleApiClient, this);
    }

    @Override
    protected void onStop() {
        super.onStop();
        googleApiClient.disconnect();
    }

//    protected void startLocationUpdates() {
//        // Create the location request
//        locationRequest = LocationRequest.create()
//                .setPriority(LocationRequest.PRIORITY_HIGH_ACCURACY)
//                .setInterval(10*1000)
//                .setFastestInterval(2000);
//        // Request location updates
//        LocationServices.FusedLocationApi.requestLocationUpdates(googleApiClient,
//                locationRequest, this);
//    }

}
