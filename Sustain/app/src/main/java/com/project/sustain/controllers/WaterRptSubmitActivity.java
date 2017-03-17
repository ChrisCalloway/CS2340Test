package com.project.sustain.controllers;

import android.app.Activity;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.icu.text.DateFormat;
import android.icu.text.SimpleDateFormat;
import android.icu.util.Calendar;
import android.icu.util.TimeZone;
import android.location.Geocoder;
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
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.location.places.Places;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.project.sustain.R;
import com.project.sustain.model.Address;
import com.project.sustain.model.Location;
import com.project.sustain.model.WaterCondition;
import com.project.sustain.model.WaterReport;
import com.project.sustain.model.WaterType;
import com.project.sustain.services.FetchAddressConstants;
import com.project.sustain.services.FetchAddressIntentService;

import java.util.ArrayList;
import java.util.Date;
import java.util.Locale;

import static com.project.sustain.services.FetchAddressConstants.LOCATION_RESULT_LATITUDE;
import static com.project.sustain.services.FetchAddressConstants.LOCATION_RESULT_LONGITUDE;
import static com.project.sustain.services.FetchAddressConstants.RESULT_DATA_KEY;

/**
 * Created by georgiainstituteoftechnology on 3/2/17.
 */

public class WaterRptSubmitActivity extends AppCompatActivity implements
        GoogleApiClient.ConnectionCallbacks, GoogleApiClient.OnConnectionFailedListener{
    protected android.location.Location mLastLocation;
    private AddressResultReceiver mResultReceiver;


    private boolean mAddressRequested;
    private boolean mLocationPermissionGranted;
    private GoogleApiClient mGoogleApiClient;
    private static final int PERMISSIONS_REQUEST_ACCESS_FINE_LOCATION = 1;
    private static final String TAG = WaterRptSubmitActivity.class.getSimpleName();
    private String requestedAction = "";
    private TextView date;
    private TextView time;
    private TextView reportNum;
    private EditText name;
    private EditText strAddress1;
    private EditText strAddress2;
    private EditText city;
    private EditText state;
    private EditText country;
    private EditText zipCode;
    private EditText latitude;
    private EditText longitude;
    private Spinner waterType;
    private Spinner waterCondition;
    private Button submitButton;
    private Button cancelButton;
    private Button getLocationButton;
    private WaterReport waterReport;
    private static int reportNumber;
    private FirebaseUser fireBaseUser;
    private DatabaseReference waterReportsRef;
    private FirebaseAuth auth;
    private static Calendar currentCalendar = Calendar.getInstance(TimeZone.getTimeZone("EST"));

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_submitwaterreport);

        auth = FirebaseAuth.getInstance();
        fireBaseUser = auth.getCurrentUser();
        waterReportsRef = FirebaseDatabase.getInstance().getReference("waterReports");

        date = (TextView) findViewById(R.id.editDate);
        time = (TextView) findViewById(R.id.editTime);
        reportNum = (TextView) findViewById(R.id.editNum);
        name = (EditText) findViewById(R.id.editName);
        strAddress1 = (EditText) findViewById(R.id.editStrAddress1);
        strAddress2 = (EditText) findViewById(R.id.editStrAddress2);
        city = (EditText) findViewById(R.id.editCity);
        state = (EditText) findViewById(R.id.editState);
        country = (EditText) findViewById(R.id.editCountry);
        zipCode = (EditText) findViewById(R.id.editZip);
        latitude = (EditText) findViewById(R.id.editLatitude);
        longitude = (EditText) findViewById(R.id.editLongitude);
        waterType = (Spinner) findViewById(R.id.editType);
        waterCondition = (Spinner) findViewById(R.id.editCondition);


        Intent retrievedIntent = getIntent();
        String nameRetrieved = retrievedIntent.getStringExtra("nameRetrieval");
        name.setText(nameRetrieved);
        submitButton = (Button) findViewById(R.id.subButton);
        cancelButton = (Button) findViewById(R.id.canButton);
        getLocationButton = (Button) findViewById(R.id.btnGetCurrentLocation);

        waterType.setAdapter(new ArrayAdapter<WaterType>(this, R.layout.support_simple_spinner_dropdown_item, WaterType.values()));
        waterCondition.setAdapter(new ArrayAdapter<WaterCondition>(this, R.layout.support_simple_spinner_dropdown_item, WaterCondition.values()));

        date.setText(obtainDate());
        time.setText(obtainTime());

        waterReportsRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                reportNumber = (int) dataSnapshot.getChildrenCount();
                String longValue = "" + ++reportNumber;
                reportNum.setText(longValue);
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });


        submitButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                saveReport();
                finish();
            }
        });

        cancelButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
               // reportNumber--;
                finish();
            }
        });

        View.OnClickListener ButtonListener = new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Only start the service to fetch the address if GoogleApiClient is
                // connected.
                if (mGoogleApiClient.isConnected() && mLastLocation != null) {
                    Log.d(TAG, "Starting intent service");
                    requestedAction = FetchAddressConstants.ACTION_FETCH_ADDRESS;
                    startIntentService(requestedAction);
                    latitude.setText(String.format("%f", mLastLocation.getLatitude()));
                    longitude.setText(String.format("%f", mLastLocation.getLongitude()));
                }
                // If GoogleApiClient isn't connected, process the user's request by
                // setting mAddressRequested to true. Later, when GoogleApiClient connects,
                // launch the service to fetch the address. As far as the user is
                // concerned, pressing the Fetch Address button
                // immediately kicks off the process of getting the address.
                mAddressRequested = true;
            }
        };

        getLocationButton.setOnClickListener(ButtonListener);

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

    /**
     * gets the address data based on latitude and longitude of current location.
     * data returned in callback AddressResultReceiver.onReceiveResult.
     * @param action FetchAddressIntentService constant indicating the type of data to retrieve
     *               (Address or LatLong).
     */
    protected void startIntentService(String action) {
        Intent intent = new Intent(this, FetchAddressIntentService.class);
        intent.setAction(action);
        intent.putExtra(FetchAddressConstants.RECEIVER, mResultReceiver);
        intent.putExtra(FetchAddressConstants.LOCATION_DATA_EXTRA, mLastLocation);
        intent.putExtra(FetchAddressConstants.LOCATION_ADDRESS_EXTRA, "");
        startService(intent);

    }

    /*
  * Requests location permission, so that we can get the location of the device.
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
     *
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

    /**
     * Attempts to display the lines of the address information received in the
     * correct spaces on the Submit Report form.
     * @param addressParts array list of lines of the address.
     */
    private void displayAddressOutput(ArrayList<String> addressParts) {
        //try to guess where the parts go.
        if (addressParts.size() > 0) {
            this.strAddress1.setText(addressParts.get(0));
        }
        if (addressParts.size() > 2) {
            String[] cityStateZip = addressParts.get(1).split(",");

            this.city.setText(cityStateZip[0]);
            if (cityStateZip.length > 1) {
                String[] stateZip = cityStateZip[1].split(" ");

                if (stateZip.length > 1) {
                    state.setText(stateZip[1]);
                }
            }
            this.country.setText(addressParts.get(2));

        }
    }

    /**
     * Displays error message received as result of address lookup.
     * @param error string containing error message
     */
    private void displayAddressError(String error) {
        Toast.makeText(getApplicationContext(), error, Toast.LENGTH_LONG).show();
    }

    class AddressResultReceiver extends ResultReceiver {
        public AddressResultReceiver(Handler handler) {
            super(handler);
        }

        @Override
        protected void onReceiveResult(int resultCode, Bundle resultData) {
            if (resultCode == FetchAddressConstants.SUCCESS_RESULT) {
                // Display the address string
                // or an error message sent from the intent service.
                ArrayList<String> address = resultData.
                        getStringArrayList(FetchAddressConstants.LOCATION_RESULT_ADDRESS_AS_LIST);
                double latitude = resultData.getDouble(LOCATION_RESULT_LATITUDE);
                double longitude = resultData.getDouble(LOCATION_RESULT_LONGITUDE);

                displayAddressOutput(address);
            } else {
                displayAddressError(resultData.getString(RESULT_DATA_KEY));
            }


        }
    }

    /**
     * Gets current date as formatted string.
     * @return string containing date
     */
    private String obtainDate() {
        DateFormat df = new SimpleDateFormat("MM/dd/yyyy", Locale.getDefault());
        return df.format(new Date());
    }

    /**
     * Gets current time as formatted string
     * @return string containing time
     */
    private String obtainTime() {
//        DateFormat df = new SimpleDateFormat("HH:mm:ss", Locale.getDefault());
//        return df.format(new Date());
        currentCalendar.setTimeZone(TimeZone.getTimeZone("EST"));
        int hour = currentCalendar.get(Calendar.HOUR_OF_DAY);
        int minute = currentCalendar.get(Calendar.MINUTE);
        int seconds = currentCalendar.get(Calendar.SECOND);
        String hourFormatted = (hour < 10) ? "0" + hour : "" + hour;
        String minuteFormatted = (minute < 10) ? "0" + minute : "" + minute;
        String secondsFormatted = (seconds < 10) ? "0" + seconds : "" + seconds;
        String completeTime = hourFormatted + ":" + minuteFormatted + ":" + secondsFormatted;
        return completeTime;
    }


    /**
     * Saves WaterReport object to database
     */
    private void saveReport() {
        if (waterReport == null) {
            waterReport = new WaterReport();
        }
        waterReport.setDate(obtainDate());
        waterReport.setTime(obtainTime());
        waterReport.setReportNumber(reportNumber);
        waterReport.setName(name.getText().toString());
        Address inputAddress = new Address();
        Location myLocation = new Location(Double.parseDouble(latitude.getText().toString()),
                Double.parseDouble(longitude.getText().toString()));

        inputAddress.setStreetAddress1(strAddress1.getText().toString());
        inputAddress.setStreetAddress2(strAddress2.getText().toString());
        inputAddress.setCity(city.getText().toString());
        inputAddress.setStateOrProvince(state.getText().toString());
        inputAddress.setCountry(country.getText().toString());
        inputAddress.setZipCode(zipCode.getText().toString());
        waterReport.setLocation(myLocation);
        waterReport.setAddress(inputAddress);
        waterReport.setTypeWater((WaterType) waterType.getSelectedItem());
        waterReport.setConditionWater((WaterCondition) waterCondition.getSelectedItem());
        waterReport.setUserID(fireBaseUser.getUid());
        waterReportsRef.push().setValue(waterReport);
        Toast.makeText(this, "Report saved.", Toast.LENGTH_SHORT);
        Intent returnIntent = new Intent();
        returnIntent.putExtra("displayName", name.getText().toString());
        setResult(Activity.RESULT_OK,returnIntent);
        finish();
    }
}
