package com.project.sustain.controllers;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.location.Location;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.location.LocationServices;


import com.project.sustain.R;
import com.project.sustain.model.UserProfile;


public class MainActivity extends AppCompatActivity {
    private Button btnLogout;
    private FirebaseAuth auth;
    private FirebaseUser mUser;
    private FirebaseDatabase mDatabase;
    private Toolbar mToolbar;
    private UserProfile mUserProfile;
    private DatabaseReference mProfiles;
    public static final int PROFILE_CHANGE_REQ = 1000;

    private Button subWtrRep;
    private Button viewWtrRep;
    private Button viewMap;

    // Provides the entry point to Google Play services
    private GoogleApiClient mGoogleApiClient;

    // Represents a geographical location
    private Location mLastLocation;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        // Get Firebase auth instance
        auth = FirebaseAuth.getInstance();
        mUser = auth.getCurrentUser();

        // Get Widget listeners
        btnLogout = (Button) findViewById(R.id.buttonLogout);
        subWtrRep = (Button) findViewById(R.id.subRep);
        viewWtrRep = (Button) findViewById(R.id.viewReportBut);
        viewMap = (Button) findViewById(R.id.buttonViewMap);

        // Create an instance of GoogleAPIClient for location functionality
        buildGoogleApiClient();

        //add Toolbar as ActionBar with menu
        mToolbar = (Toolbar) findViewById(R.id.main_toolbar);
        this.setSupportActionBar(mToolbar);

        if (mUser != null) {
            mDatabase = FirebaseDatabase.getInstance();
            mProfiles = mDatabase.getReference().child("userProfiles");
            mProfiles.child(mUser.getUid()).addValueEventListener(new ValueEventListener() {
                @Override
                public void onDataChange(DataSnapshot dataSnapshot) {
                    mUserProfile = dataSnapshot.getValue(UserProfile.class);
                    if (mUserProfile != null) {
                        if (mUserProfile.getUserName().equals("") == false) {
                            setToolbarTitle(mUserProfile.getUserName());
                        } else {
                            setToolbarTitle(mUser.getEmail());
                        }
                    }
                }

                @Override
                public void onCancelled(DatabaseError databaseError) {

                }
            });
        }

        // Functions to invoke upon button presses

        // On clicking the logout button
        btnLogout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                auth.signOut();
                startActivity(new Intent(MainActivity.this, LoginActivity.class));
                finish();
            }
        });

        // On clicking
        subWtrRep.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String toPassIn = getToolbarTitle();
                Intent forWtrRptSubmit = new Intent(MainActivity.this, WaterRptSubmitActivity.class);
                forWtrRptSubmit.putExtra("nameRetrieval", toPassIn);
                startActivity(forWtrRptSubmit);
            }
        });


        viewWtrRep.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivityForResult(new Intent(MainActivity.this, ViewReportsActivity.class), 5000);
            }
        });


        viewMap.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(MainActivity.this, MapsMarkerActivity.class));
            }
        });
    }

    /**
     * Sets the text in the toolbar to the text provided.,
     * @param name The name to put into the toolbar title.
     */
    private void setToolbarTitle(String name) {
        ActionBar actionBar = this.getSupportActionBar();
        actionBar.setTitle(name);

    }

    private String getToolbarTitle() {
        ActionBar actionBar = this.getSupportActionBar();
        return (String) actionBar.getTitle();
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.action_edit_profile:
                // User chose the "Edit Profile" action, show the user profile settings UI...
                startActivityForResult(new Intent(MainActivity.this, EditProfileActivity.class),
                        PROFILE_CHANGE_REQ);
            case R.id.action_settings:
                // User chose the "Settings" item, show the app settings UI...
                return true;

            default:
                // If we got here, the user's action was not recognized.
                // Invoke the superclass to handle it.
                return super.onOptionsItemSelected(item);

        }
    }

    @Override
    protected void onStart() {
        // Connect to the GoogleApiClient
        mGoogleApiClient.connect();
        super.onStart();
    }

    @Override
    protected void onStop() {
        // Disconnect from the GoogleApiClient
        mGoogleApiClient.disconnect();
        super.onStop();
    }

    /**
     * Builds a GoogleApiClient. Uses the addApi() method to request the LocationServices API.
     */
    protected synchronized void buildGoogleApiClient() {
        mGoogleApiClient = new GoogleApiClient.Builder(this)
                .addApi(LocationServices.API)
                .build();
    }

    // Menu icons are inflated just as they were with actionbar
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == PROFILE_CHANGE_REQ) {
            if (resultCode == RESULT_OK) {
                Log.d("EditResult", "Got result OK");
                setToolbarTitle(data.getStringExtra("displayName"));
            }
        }
    }
}
