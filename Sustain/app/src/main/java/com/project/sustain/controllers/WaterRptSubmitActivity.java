package com.project.sustain.controllers;

import android.app.Activity;
import android.content.Intent;
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
    private EditText strAddress;
    private EditText city;
    private EditText state;
    private EditText country;
    private EditText zipCode;
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
    private static Calendar currentCalendar = Calendar.getInstance();
    private static int reportNumber = 0;

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_submitwaterreport);

        date = (TextView) findViewById(R.id.editDate);
        time = (TextView) findViewById(R.id.editTime);
        reportNum = (TextView) findViewById(R.id.editNum);
        name = (EditText) findViewById(R.id.editName);
        strAddress = (EditText) findViewById(R.id.editStrAddress);
        city = (EditText) findViewById(R.id.editCity);
        state = (EditText) findViewById(R.id.editState);
        country = (EditText) findViewById(R.id.editCountry);
        zipCode = (EditText) findViewById(R.id.editZip);
        waterType = (Spinner) findViewById(R.id.editType);
        waterCondition = (Spinner) findViewById(R.id.editCondition);

        fireBaseUser = FirebaseAuth.getInstance().getCurrentUser();
        fireBaseDatabase = FirebaseDatabase.getInstance();
        waterReportsRef = fireBaseDatabase.getReference().child("waterReports");
//        mProfiles = fireBaseDatabase.getReference().child("userProfiles");

        submitButton = (Button) findViewById(R.id.subButton);
        cancelButton = (Button) findViewById(R.id.canButton);

        waterType.setAdapter(new ArrayAdapter<WaterType>(this, R.layout.support_simple_spinner_dropdown_item, WaterType.values()));
        waterCondition.setAdapter(new ArrayAdapter<WaterCondition>(this, R.layout.support_simple_spinner_dropdown_item, WaterCondition.values()));

        String dateBeforeSub = obtainDate();
        date.setText(dateBeforeSub);

        reportNumber++;
        String reportNumberFormatted = "" + reportNumber;
        reportNum.setText(reportNumberFormatted);

        String timeBeforeSub = obtainTime();
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

    private String obtainDate() {
        int month = currentCalendar.get(Calendar.MONTH);
        int day = currentCalendar.get(Calendar.DAY_OF_MONTH);
        int year = currentCalendar.get(Calendar.YEAR);
        Month[] months = Month.values();
        String monthString = months[month].toString();
        String dayFormatted = (day < 10) ? "0" + day : "" + day;
        String completeDate = monthString + " " + dayFormatted + ", " + year;
        return completeDate;
    }

    private String obtainTime() {
        int hour = currentCalendar.get(Calendar.HOUR_OF_DAY);
        int minute = currentCalendar.get(Calendar.MINUTE);
        int seconds = currentCalendar.get(Calendar.SECOND);
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
        String dateAgain = obtainDate();
        String timeAgain = obtainTime();
        waterReport.setDate(dateAgain);
        waterReport.setTime(timeAgain);
        waterReport.setReportNumber(reportNumber);
        waterReport.setName(name.getText().toString());
        Address toSet = new Address();
        toSet.setStreetAddress1(strAddress.getText().toString());
        toSet.setCity(city.getText().toString());
        toSet.setStateOrProvince(state.getText().toString());
        toSet.setCountry(country.getText().toString());
        toSet.setZipCode(zipCode.getText().toString());
        waterReport.setAddress(toSet);
        waterReportsRef.push().setValue(waterReport);
        Toast.makeText(this, "Profile saved.", Toast.LENGTH_SHORT);
        Intent returnIntent = new Intent();
        returnIntent.putExtra("displayName", name.getText().toString());
        setResult(Activity.RESULT_OK,returnIntent);
        finish();
    }
}
