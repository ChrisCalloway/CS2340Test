package com.project.sustain.controllers;

import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Geocoder;
import android.location.Location;
import android.os.Bundle;
import android.os.Handler;
import android.os.ResultReceiver;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.location.places.Places;
import com.project.sustain.R;
import com.project.sustain.services.FetchAddressConstants;
import com.project.sustain.services.FetchAddressIntentService;

import static com.project.sustain.services.FetchAddressConstants.LOCATION_RESULT_LATITUDE;
import static com.project.sustain.services.FetchAddressConstants.LOCATION_RESULT_LONGITUDE;

public class TestActivity extends AppCompatActivity implements
        GoogleApiClient.ConnectionCallbacks, GoogleApiClient.OnConnectionFailedListener {
    protected Location mLastLocation;
    private AddressResultReceiver mResultReceiver;

    private boolean mAddressRequested;
    private boolean mLocationPermissionGranted;
    private GoogleApiClient mGoogleApiClient;
    private static final int PERMISSIONS_REQUEST_ACCESS_FINE_LOCATION = 1;
    private static final String TAG = TestActivity.class.getSimpleName();
    private Button mTestGetAddressButton;
    private Button mTestGetLatLongButton;
    private String requestedAction = "";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_test);
        mTestGetAddressButton = (Button) findViewById(R.id.btnTestGetAddress);
        mTestGetLatLongButton = (Button) findViewById(R.id.btnTestGetLatLong);



        View.OnClickListener ButtonListener = new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Only start the service to fetch the address if GoogleApiClient is
                // connected.
                if (mGoogleApiClient.isConnected() && mLastLocation != null) {
                    Log.d(TAG, "Starting intent service");
                    if (v.getId() == R.id.btnTestGetAddress) {
                        requestedAction = FetchAddressConstants.ACTION_FETCH_ADDRESS;
                    }
                    else if (v.getId() == R.id.btnTestGetLatLong) {
                        requestedAction = FetchAddressConstants.ACTION_FETCH_LATLONG;
                    }
                    startIntentService(requestedAction);
                }
                // If GoogleApiClient isn't connected, process the user's request by
                // setting mAddressRequested to true. Later, when GoogleApiClient connects,
                // launch the service to fetch the address. As far as the user is
                // concerned, pressing the Fetch Address button
                // immediately kicks off the process of getting the address.
                mAddressRequested = true;
            }
        };

        mTestGetAddressButton.setOnClickListener(ButtonListener);
        mTestGetLatLongButton.setOnClickListener(ButtonListener);

        mResultReceiver = new AddressResultReceiver(new Handler());

        mGoogleApiClient = new GoogleApiClient.Builder(this)
                .enableAutoManage(this /* FragmentActivity */,
                        this /* OnConnectionFailedListener */)
                .addConnectionCallbacks(this)
                .addApi(LocationServices.API)
                .addApi(Places.GEO_DATA_API)
                .addApi(Places.PLACE_DETECTION_API)
                .build();
        mGoogleApiClient.connect();
    }

    @Override
    public void onConnected(@Nullable Bundle bundle) {
        // Gets the best and most recent location currently available,
        // which may be null in rare cases when a location is not available.

        requestPermissionForLocation();
        if (mLastLocation != null) {
            // Determine whether a Geocoder is available.
            if (!Geocoder.isPresent()) {
                Toast.makeText(this, "No geocoder available",
                        Toast.LENGTH_LONG).show();
                return;
            }

            if (mAddressRequested) {
                startIntentService(requestedAction);
            }
        } else {
            Log.d(TAG, "Unable to get permission and/or last location.");
        }
    }

    @Override
    public void onConnectionSuspended(int i) {

    }

    @Override
    public void onConnectionFailed(@NonNull ConnectionResult connectionResult) {

    }

    protected void startIntentService(String action) {
        Intent intent = new Intent(this, FetchAddressIntentService.class);
        intent.setAction(action);
        intent.putExtra(FetchAddressConstants.RECEIVER, mResultReceiver);
        intent.putExtra(FetchAddressConstants.LOCATION_DATA_EXTRA, mLastLocation);
        intent.putExtra(FetchAddressConstants.LOCATION_ADDRESS_EXTRA, "25 Dunwoody Springs Dr., Sandy " +
                        "Springs, GA");
        startService(intent);
    }

    /*
  * Request location permission, so that we can get the location of the device.
  * The result of the permission request is handled by a callback,
  * onRequestPermissionsResult.
  */
    private void requestPermissionForLocation() {
        if (ContextCompat.checkSelfPermission(this.getApplicationContext(),
                android.Manifest.permission.ACCESS_FINE_LOCATION)
                == PackageManager.PERMISSION_GRANTED) {
            mLocationPermissionGranted = true;
        } else {
            ActivityCompat.requestPermissions(this,
                    new String[]{android.Manifest.permission.ACCESS_FINE_LOCATION},
                    PERMISSIONS_REQUEST_ACCESS_FINE_LOCATION);
        }
        mLocationPermissionGranted = true;
        /*
         * Get the best and most recent location of the device, which may be null in rare
         * cases when a location is not available.
         */
        if (mLocationPermissionGranted) {
            mLastLocation = LocationServices.FusedLocationApi
                    .getLastLocation(mGoogleApiClient);
        }


    }

    /*
     * Handles the result of the request for location permissions.
     */
    @Override
    public void onRequestPermissionsResult(int requestCode,
                                           @NonNull String permissions[],
                                           @NonNull int[] grantResults) {
        mLocationPermissionGranted = false;
        switch (requestCode) {
            case PERMISSIONS_REQUEST_ACCESS_FINE_LOCATION: {
                // If request is cancelled, the result arrays are empty.
                if (grantResults.length > 0
                        && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    mLocationPermissionGranted = true;
                }
            }
        }
    }


    class AddressResultReceiver extends ResultReceiver {
        public AddressResultReceiver(Handler handler) {
            super(handler);
        }

        @Override
        protected void onReceiveResult(int resultCode, Bundle resultData) {
            String mAddressOutput = "";
            // Display the address string
            // or an error message sent from the intent service.
            mAddressOutput = resultData.getString(FetchAddressConstants.LOCATION_RESULT_ADDRESS);
            double latitude = resultData.getDouble(LOCATION_RESULT_LATITUDE);
            double longitude = resultData.getDouble(LOCATION_RESULT_LONGITUDE);

            //displayAddressOutput();

            // Show a toast message if an address was found.
            if (resultCode == FetchAddressConstants.SUCCESS_RESULT) {
                showToast("latitude: " + latitude + ", longitude: " + longitude);

            }

        }

        public void showToast(String toast) {
            Toast.makeText(getApplicationContext(), toast, Toast.LENGTH_LONG).show();
        }
    }
}
