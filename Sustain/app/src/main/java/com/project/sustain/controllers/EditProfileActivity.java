package com.project.sustain.controllers;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.UserProfileChangeRequest;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.project.sustain.R;
import com.project.sustain.model.Address;
import com.project.sustain.model.UserProfile;
import com.project.sustain.model.UserType;

public class EditProfileActivity extends AppCompatActivity {
    private static final String TAG = "EditProfileActivity";
    private EditText mDisplayName;
    private Spinner mSpinnerUserType;
    private Button btnSave;
    private Button btnCancel;
    private EditText mStreet1;
    private EditText mStreet2;
    private EditText mCity;
    private EditText mState;
    private EditText mCountry;
    private EditText mZip;
    private FirebaseAuth mFirebaseAuth;
    private FirebaseUser mFirebaseUser;
    private FirebaseDatabase mDatabase;
    private DatabaseReference mProfiles;
    private UserProfile mUserProfile;
    private String currentDisplayName = "";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_profile);
        Log.d("OnCreate", "called");
        //get refs to widgets on screen
        mDisplayName = (EditText) findViewById(R.id.editText_display_name);
        mSpinnerUserType = (Spinner) findViewById(R.id.spinner_usertype);
        mStreet1 = (EditText) findViewById(R.id.editText_street1);
        mStreet2 = (EditText) findViewById(R.id.editText_street2);
        mCity = (EditText) findViewById(R.id.editText_city);
        mState = (EditText) findViewById(R.id.editText_state);
        mCountry = (EditText) findViewById(R.id.editText_country);
        mZip    = (EditText) findViewById(R.id.editText_zipcode);

        //get refs to Firebase user objects
        mFirebaseAuth = FirebaseAuth.getInstance();
        mFirebaseUser = mFirebaseAuth.getCurrentUser();
        mDatabase = FirebaseDatabase.getInstance();
        mProfiles = mDatabase.getReference().child("userProfiles");

        btnSave = (Button) findViewById(R.id.buttonSaveProfile);
        btnCancel = (Button) findViewById(R.id.buttonCancelProfile);

        //fill drop-down boxes
        mSpinnerUserType.setAdapter(new ArrayAdapter<UserType>(this, R.layout.support_simple_spinner_dropdown_item,
                UserType.values()));

        if (mFirebaseUser.getDisplayName() != null) {
            Log.d("OnCreate", "setting display name");
            mDisplayName.setText(mFirebaseUser.getDisplayName());
            currentDisplayName = mDisplayName.getText().toString();
        }

        //get this user's profile data
        mProfiles.child(mFirebaseUser.getUid()).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                mUserProfile = dataSnapshot.getValue(UserProfile.class);
                if (mUserProfile != null) {
                    loadProfileData();
                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });

        btnSave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                saveProfileData();
                finish();
            }
        });

        btnCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }

    private void loadProfileData() {
        Log.d("Edit", "LoadProfileData called");
        if (currentDisplayName.length() > 0) {
            mDisplayName.setText(currentDisplayName);
        } else if (mFirebaseUser.getDisplayName() != null) {
            mDisplayName.setText(mFirebaseUser.getDisplayName());
        }
        mSpinnerUserType.setSelection(mUserProfile.getUserType().ordinal());
        Address homeAddress = mUserProfile.getHomeAddress();
        mStreet1.setText(homeAddress.getStreetAddress1());
        mStreet2.setText(homeAddress.getStreetAddress2());
        mCity.setText(homeAddress.getCity());
        mState.setText(homeAddress.getStateOrProvince());
        mCountry.setText(homeAddress.getCountry());
        mZip.setText(homeAddress.getZipCode());

    }
    private boolean userDisplayNameChanged() {
        if (mFirebaseUser.getDisplayName() == null) {
            return mDisplayName.length() > 0;
        } else {
            return !(mFirebaseUser.getDisplayName().contentEquals(mDisplayName.getText()));
        }
    }

    private void updateUserDisplayName() {
        currentDisplayName = mDisplayName.getText().toString();
        UserProfileChangeRequest profileUpdates = new UserProfileChangeRequest.Builder()
         .setDisplayName(currentDisplayName)
         .build();

        mFirebaseUser.updateProfile(profileUpdates)
            .addOnCompleteListener(new OnCompleteListener<Void>() {
             @Override
             public void onComplete(@NonNull Task<Void> task) {
                 if (task.isSuccessful()) {
                     Log.d(TAG, "User display name updated.");
                 }
             }
        });
    }
    private void saveProfileData() {
        if (mUserProfile == null) {
            mUserProfile = new UserProfile();
        }
        mUserProfile.setEmailAddress(mFirebaseUser.getEmail());
        mUserProfile.setUserType((UserType) mSpinnerUserType.getSelectedItem());
        Address address = new Address();
        address.setStreetAddress1(mStreet1.getText().toString());
        address.setStreetAddress2(mStreet2.getText().toString());
        address.setCity(mCity.getText().toString());
        address.setStateOrProvince(mState.getText().toString());
        address.setCountry(mCountry.getText().toString());
        address.setZipCode(mZip.getText().toString());
        mUserProfile.setHomeAddress(address);

        //Firebase userID is the key for this record.
        mProfiles.child(mFirebaseUser.getUid()).setValue(mUserProfile);
        if (userDisplayNameChanged()) {
            updateUserDisplayName();
        }
        Toast.makeText(this, "Profile saved.", Toast.LENGTH_SHORT);
        Intent returnIntent = new Intent();
        returnIntent.putExtra("displayName", currentDisplayName);
        setResult(Activity.RESULT_OK,returnIntent);
        finish();

    }


}
