package com.project.sustain.controllers;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import com.project.sustain.R;
import com.project.sustain.model.Address;
import com.project.sustain.model.User;
import com.project.sustain.model.UserManager;
import com.project.sustain.model.UserType;

public class EditProfileActivity extends AppCompatActivity {
    private static final String TAG = "EditProfileActivity";
    private EditText mUserName;
    private Spinner mSpinnerUserType;
    private Button btnSave;
    private Button btnCancel;
    private EditText mStreet1;
    private EditText mStreet2;
    private EditText mCity;
    private EditText mState;
    private EditText mCountry;
    private EditText mZip;
    private UserManager mUserManager;
    private User mUserProfile;
    private String currentDisplayName = "";
    private Context appContext = this.getApplication();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_profile);

        //get refs to widgets on screen
        mUserName = (EditText) findViewById(R.id.editText_userName);
        mSpinnerUserType = (Spinner) findViewById(R.id.spinner_usertype);
        mStreet1 = (EditText) findViewById(R.id.editText_street1);
        mStreet2 = (EditText) findViewById(R.id.editText_street2);
        mCity = (EditText) findViewById(R.id.editText_city);
        mState = (EditText) findViewById(R.id.editText_state);
        mCountry = (EditText) findViewById(R.id.editText_country);
        mZip    = (EditText) findViewById(R.id.editText_zipcode);

        //UserManager will handle all database reads/writes for us
        mUserManager = new UserManager();

        btnSave = (Button) findViewById(R.id.buttonSaveProfile);
        btnCancel = (Button) findViewById(R.id.buttonCancelProfile);

        //fill drop-down boxes
        mSpinnerUserType.setAdapter(new ArrayAdapter<UserType>(this,
                R.layout.support_simple_spinner_dropdown_item,
                UserType.values()));

        //get this user's profile data.
        //was passed here from MainActivity.
        mUserProfile = (User) this.getIntent().getSerializableExtra("user");

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

    /**
     * Checks to see if the user has changed his/her display name.
     * @return true if display name has updated, false otherwise
     */
    private boolean userDisplayNameChanged() {
        String storedName = mUserManager.getCurrentUserDisplayName();
        if (storedName == null || storedName.length() == 0) {
            return mUserName.length() > 0;
        } else {
            return !(storedName.contentEquals(mUserName.getText()));
        }
    }

    /**
     * Updates the user's display name in the user store.
     */
    private void updateUserDisplayName() {
        currentDisplayName = mUserName.getText().toString();
        if (currentDisplayName.length() > 0) {
            mUserManager.updateCurrentUserDisplayName(currentDisplayName);
        }
    }

    /**
     * Loads the profile data into the widgets on screen.
     */
    private void loadProfileData() {
        Log.d(TAG, "LoadProfileData called");
        mUserName.setText(mUserProfile.getUserName());
        mSpinnerUserType.setSelection(mUserProfile.getUserType().ordinal());
        Address homeAddress = mUserProfile.getHomeAddress();
        mStreet1.setText(homeAddress.getStreetAddress1());
        mStreet2.setText(homeAddress.getStreetAddress2());
        mCity.setText(homeAddress.getCity());
        mState.setText(homeAddress.getStateOrProvince());
        mCountry.setText(homeAddress.getCountry());
        mZip.setText(homeAddress.getZipCode());

    }

    /**
     * Saves the user's input profile data to database
     * and auth user store.
     * Sends result back to calling Activity.
     */
    private void saveProfileData() {
        if (mUserProfile == null) {
            mUserProfile = new User();
        }
        mUserProfile.setEmailAddress(mUserManager.getCurrentUserEmail());
        mUserProfile.setUserType((UserType) mSpinnerUserType.getSelectedItem());
        mUserProfile.setUserName(mUserName.getText().toString());
        mUserProfile.setUserId(mUserManager.getCurrentUserId());
        Address address = new Address();
        address.setStreetAddress1(mStreet1.getText().toString());
        address.setStreetAddress2(mStreet2.getText().toString());
        address.setCity(mCity.getText().toString());
        address.setStateOrProvince(mState.getText().toString());
        address.setCountry(mCountry.getText().toString());
        address.setZipCode(mZip.getText().toString());
        mUserProfile.setHomeAddress(address);

        //ask UserManager to update the backend DB with our changes
        mUserManager.updateUser(mUserProfile);
        if (userDisplayNameChanged()) {
            updateUserDisplayName();
        }
        Toast.makeText(this, "Profile saved.", Toast.LENGTH_SHORT).show();

        //pass User changes back to calling Activity
        Intent returnIntent = new Intent();
        returnIntent.putExtra("user", mUserProfile);
        setResult(Activity.RESULT_OK,returnIntent);
        finish();

    }


}
