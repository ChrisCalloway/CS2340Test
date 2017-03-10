package com.project.sustain.controllers;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.icu.util.Calendar;
import android.os.Bundle;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.project.sustain.R;

import android.provider.ContactsContract;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.project.sustain.model.Address;
import com.project.sustain.model.Location;
import com.project.sustain.model.Month;
import com.project.sustain.model.UserProfile;
import com.project.sustain.model.WaterCondition;
import com.project.sustain.model.WaterReport;
import com.project.sustain.model.WaterType;

import java.util.Date;

/**
 * Created by georgiainstituteoftechnology on 3/2/17.
 */

public class WaterRptSubmitActivity extends AppCompatActivity{
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
    private FirebaseAuth fireBaseAuthentication;
    private FirebaseUser fireBaseUser;
    private FirebaseDatabase fireBaseDatabase;
    private DatabaseReference waterReportsRef;
    private DatabaseReference mProfiles;
    private UserProfile toUseForName;
    private WaterReport waterReport;
    private Calendar currentForApp;
    private static int reportNumber;
//    private static Calendar currentCalendar = Calendar.getInstance();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_submitwaterreport);

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

        fireBaseUser = FirebaseAuth.getInstance().getCurrentUser();
        fireBaseDatabase = FirebaseDatabase.getInstance();
        waterReportsRef = fireBaseDatabase.getReference().child("waterReports");
//        mProfiles = fireBaseDatabase.getReference().child("userProfiles");

        Intent retrievedIntent = getIntent();
        String nameRetrieved = retrievedIntent.getStringExtra("nameRetrieval");
        name.setText(nameRetrieved);
        submitButton = (Button) findViewById(R.id.subButton);
        cancelButton = (Button) findViewById(R.id.canButton);

        waterType.setAdapter(new ArrayAdapter<WaterType>(this, R.layout.support_simple_spinner_dropdown_item, WaterType.values()));
        waterCondition.setAdapter(new ArrayAdapter<WaterCondition>(this, R.layout.support_simple_spinner_dropdown_item, WaterCondition.values()));

        currentForApp = currentCalendar();
        String dateBeforeSub = obtainDate(currentForApp);
        date.setText(dateBeforeSub);

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


//        String reportNumberFormatted = "" + reportNumber;


        String timeBeforeSub = obtainTime(currentForApp);
        time.setText(timeBeforeSub);

//        String nameToPass = fireBaseUser.getDisplayName();
//        name.setText(nameToPass);

//        mProfiles.child(fireBaseUser.getUid()).addValueEventListener(new ValueEventListener() {
//            @Override
//            public void onDataChange(DataSnapshot dataSnapshot) {
//                toUseForName = dataSnapshot.getValue(UserProfile.class);
//                if (toUseForName != null) {
//                    loadName();
//                }
//            }
//
//            @Override
//            public void onCancelled(DatabaseError databaseError) {
//
//            }
//        });

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
                reportNumber--;
                finish();
            }
        });
    }

    private Calendar currentCalendar() {
        Calendar current = Calendar.getInstance();
        return current;
    }

    private String obtainDate(Calendar toPass1) {
        int month = toPass1.get(Calendar.MONTH);
        int day = toPass1.get(Calendar.DAY_OF_MONTH);
        int year = toPass1.get(Calendar.YEAR);
        Month[] months = Month.values();
        String monthString = months[month].toString();
        String dayFormatted = (day < 10) ? "0" + day : "" + day;
        String completeDate = monthString + " " + dayFormatted + ", " + year;
        return completeDate;
    }

    private String obtainTime(Calendar toPass2) {
        int hour = toPass2.get(Calendar.HOUR_OF_DAY);
        int minute = toPass2.get(Calendar.MINUTE);
        int seconds = toPass2.get(Calendar.SECOND);
        String hourFormatted = (hour < 10) ? "0" + hour : "" + hour;
        String minuteFormatted = (minute < 10) ? "0" + minute : "" + minute;
        String secondsFormatted = (seconds < 10) ? "0" + seconds : "" + seconds;
        String completeTime = hourFormatted + ":" + minuteFormatted + ":" + secondsFormatted;
        return completeTime;
    }

//    private void loadName() {
//        name.setText(toUseForName.getUserName());
//    }

    private void saveReport() {
        if (waterReport == null) {
            waterReport = new WaterReport();
        }
        Calendar uponSubmission = currentCalendar();
        String dateAgain = obtainDate(uponSubmission);
        String timeAgain = obtainTime(uponSubmission);
        waterReport.setDate(dateAgain);
        waterReport.setTime(timeAgain);
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
        Toast.makeText(this, "Profile saved.", Toast.LENGTH_SHORT);
        Intent returnIntent = new Intent();
        returnIntent.putExtra("displayName", name.getText().toString());
        setResult(Activity.RESULT_OK,returnIntent);
        finish();
    }
}
