package com.project.sustain.services;

import android.app.IntentService;
import android.content.Intent;
import android.location.Address;
import android.location.Geocoder;
import android.location.Location;
import android.os.Bundle;
import android.os.ResultReceiver;
import android.text.TextUtils;
import android.util.Log;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

import static android.content.ContentValues.TAG;
import static com.project.sustain.services.FetchAddressConstants.ACTION_FETCH_ADDRESS;
import static com.project.sustain.services.FetchAddressConstants.ACTION_FETCH_LAT_LONG;
import static com.project.sustain.services.FetchAddressConstants.LOCATION_ADDRESS_EXTRA;
import static com.project.sustain.services.FetchAddressConstants.LOCATION_DATA_EXTRA;
import static com.project.sustain.services.FetchAddressConstants.RECEIVER;

/**
 * An {@link IntentService} subclass for handling asynchronous task requests in
 * a service on a separate handler thread.
 * <p>
 * TODO: Customize class - update intent actions, extra parameters and static
 * helper methods.
 */
public class FetchAddressIntentService extends IntentService {
    protected ResultReceiver mReceiver;

    public FetchAddressIntentService() {
        super("FetchAddressIntentService");
    }


    @Override
    protected void onHandleIntent(Intent intent) {

        if (intent != null) {

            final String action = intent.getAction();
            Location location = intent.getParcelableExtra(LOCATION_DATA_EXTRA);
            mReceiver = intent.getParcelableExtra(RECEIVER);
            if (ACTION_FETCH_ADDRESS.equals(action)) {
                handleActionFetchAddress(location.getLatitude(), location.getLongitude());
            } else if (ACTION_FETCH_LAT_LONG.equals(action)) {
                String address = intent.getStringExtra(LOCATION_ADDRESS_EXTRA);
                handleActionFetchLatLong(address);
            }
        }
    }

    private void handleActionFetchLatLong(String addressInfo) {
        // given latitude and longitude, find the location address.
        String errorMessage = "";
        Geocoder geocoder = new Geocoder(this, Locale.getDefault());

        List<Address> addresses = null;

        try {
            addresses = geocoder.getFromLocationName(addressInfo, 1);
        } catch (IOException ioException) {
            // Catch network or other I/O problems.
            errorMessage = "Geo service not available.";
            Log.e(TAG, errorMessage, ioException);
        } catch (IllegalArgumentException illegalArgumentException) {
            // Catch invalid latitude or longitude values.
            errorMessage = "Illegal argument in address Info.";
            Log.e(TAG, errorMessage + ". " +
                    "AddressInfo = " + addressInfo, illegalArgumentException);
        }
        // Handle case where no address was found.
        if (addresses == null || addresses.size()  == 0) {
            if (errorMessage.isEmpty()) {
                errorMessage = "No address found.";
                Log.e(TAG, errorMessage);
            }
            deliverErrorToReceiver(FetchAddressConstants.FAILURE_RESULT, errorMessage);
        } else {
            Address address = addresses.get(0);
            Log.i(TAG, "LatLong found.");
            deliverLatLongToReceiver(FetchAddressConstants.SUCCESS_RESULT, address.getLatitude(),
                   address.getLongitude());
        }
    }
    /**
     * Handle action in the provided background thread with the provided
     * parameters.
     */
    private void handleActionFetchAddress(double latitude, double longitude) {
        // given latitude and longitude, find the location address.
        String errorMessage = "";
        Geocoder geocoder = new Geocoder(this, Locale.getDefault());

        List<Address> addresses = null;

        try {
            addresses = geocoder.getFromLocation(
                                    latitude,
                                    longitude,
                                    // In this sample, get just a single address.
                                    1);
        } catch (IOException ioException) {
            // Catch network or other I/O problems.
            errorMessage = "Geo service not available.";
            Log.e(TAG, errorMessage, ioException);
        } catch (IllegalArgumentException illegalArgumentException) {
            // Catch invalid latitude or longitude values.
            errorMessage = "Invalid lat long used.";
            Log.e(TAG, errorMessage + ". " +
                                        "Latitude = " + latitude +
                                        ", Longitude = " +
                                        longitude, illegalArgumentException);
        }
            // Handle case where no address was found.
        if (addresses == null || addresses.size()  == 0) {
            if (errorMessage.isEmpty()) {
                errorMessage = "No address found.";
                Log.e(TAG, errorMessage);
            }
            deliverErrorToReceiver(FetchAddressConstants.FAILURE_RESULT, errorMessage);
        } else {
            Address address = addresses.get(0);
            List<String> addressFragments = new ArrayList<>();

            // Fetch the address lines using getAddressLine,
            // join them, and send them to the thread.
            for(int i = 0; i <= address.getMaxAddressLineIndex(); i++) {
                addressFragments.add(address.getAddressLine(i));
            }
            Log.i(TAG, "Address found.");
            deliverAddressToReceiver(FetchAddressConstants.SUCCESS_RESULT,
                                        TextUtils.join(System.getProperty("line.separator"),
                                                        addressFragments),
                                        addressFragments);
        }
    }
    

    private void deliverErrorToReceiver(int resultCode, String errorMessage) {
        Bundle bundle = new Bundle();
        bundle.putString(FetchAddressConstants.RESULT_DATA_KEY, errorMessage);
        mReceiver.send(resultCode, bundle);
    }

    private void deliverAddressToReceiver(int resultCode, String message, List<String> addressFragments) {
        Bundle bundle = new Bundle();
        bundle.putString(FetchAddressConstants.RESULT_DATA_KEY, message);
        bundle.putString(FetchAddressConstants.LOCATION_RESULT_ADDRESS, message);
        bundle.putStringArrayList(FetchAddressConstants.LOCATION_RESULT_ADDRESS_AS_LIST,
                (ArrayList<String>) addressFragments);

        mReceiver.send(resultCode, bundle);
    }

    private void deliverLatLongToReceiver(int resultCode, double latitude, double longitude) {
        Bundle bundle = new Bundle();
        bundle.putString(FetchAddressConstants.RESULT_DATA_KEY, "LatLong found");
        bundle.putDouble(FetchAddressConstants.LOCATION_RESULT_LATITUDE, latitude);
        bundle.putDouble(FetchAddressConstants.LOCATION_RESULT_LONGITUDE, longitude);

        mReceiver.send(resultCode, bundle);
    }

}
