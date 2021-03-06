package com.project.sustain.controllers;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.text.TextUtils;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Spinner;
import android.widget.Toast;

import com.project.sustain.R;
import com.project.sustain.model.User;
import com.project.sustain.model.UserManager;
import com.project.sustain.model.UserType;

/**
 * Activity that handles the registration of the user.
 */
public class RegistrationActivity extends AppCompatActivity {
    private EditText enteredEmail;
    private EditText enteredPassword;
    // --Commented out by Inspection (4/10/2017 21:06 PM):private Button btnCancelRegistration;
    private ProgressBar progressBar;
    private Spinner selectedUserType;
    private User mUserProfile;
    private UserManager mUserManager;
    private static final int PROFILE_CHANGE_REQ = 1000;
    private static final String TAG = "RegistrationActivity";


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registration);

        //Toolbar with title and back button
        Toolbar toolbar = (Toolbar) findViewById(R.id.activity_registration_toolbar);
        toolbar.setTitle("Register a new account");
        this.setSupportActionBar(toolbar);
        ActionBar support = getSupportActionBar();
        if (support != null) {
            support.setDisplayHomeAsUpEnabled(true);
            support.setDisplayShowHomeEnabled(true);
        }

        // Get UserManager instance
        mUserManager = new UserManager();
        Button btnRegister = (Button) findViewById(R.id.buttonRegister);
        enteredEmail = (EditText) findViewById(R.id.editEmail);
        enteredPassword = (EditText) findViewById(R.id.editPassword);
        progressBar = (ProgressBar) findViewById(R.id.registerProgressBar);
        selectedUserType = (Spinner) findViewById(R.id.spinnerUserType);


        //fill drop-down boxes
        selectedUserType.setAdapter(
                new ArrayAdapter<>(this, R.layout.support_simple_spinner_dropdown_item,
                UserType.values()));

        btnRegister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final String email = enteredEmail.getText().toString().trim();
                String password = enteredPassword.getText().toString().trim();

                if (TextUtils.isEmpty(email)) {
                    Toast.makeText(getApplicationContext(),
                            "Please enter email address", Toast.LENGTH_SHORT).show();
                    return;
                }
                if (TextUtils.isEmpty(password)) {
                    Toast.makeText(getApplicationContext(),
                            "Please enter password", Toast.LENGTH_SHORT).show();
                    return;
                }
                if (password.length() < 6) {
                    Toast.makeText(getApplicationContext(),
                            "Password too short, please enter minimum 6 characters",
                            Toast.LENGTH_SHORT).show();
                    return;
                }

                progressBar.setVisibility(View.VISIBLE);

                // Create user
                RegistrationResultListener resultListener = new RegistrationResultListener() {
                    @Override
                    public void onComplete(boolean success, String userId) {
                        progressBar.setVisibility(View.GONE);
                        if (success) {
                            Toast.makeText(RegistrationActivity.this, "Registration successful.",
                                    Toast.LENGTH_SHORT).show();
                            //create User object for this new user
                            mUserProfile = new User();
                            mUserProfile.setUserId(userId);
                            mUserProfile.setEmailAddress(email);
                            mUserProfile.setUserType((UserType) selectedUserType.getSelectedItem());
                            Log.d(TAG, "adding user profile to database");
                            mUserManager.addUser(mUserProfile);
                            //open profile editor
                            //EditProfileActivity expects to be passed a User object
                            Intent intent = new Intent(RegistrationActivity.this,
                                    EditProfileActivity.class);
                            intent.putExtra("user", mUserProfile);
                            startActivityForResult(intent, PROFILE_CHANGE_REQ);
                        } else {
                            //userId may or may not contain an error message.
                            Toast.makeText(RegistrationActivity.this, "Registration failed",
                                    Toast.LENGTH_SHORT).show();
                        }
                    }

                    @Override
                    public void onError(Throwable error) {
                        progressBar.setVisibility(View.GONE);
                        if (error != null) {
                            Toast.makeText(RegistrationActivity.this, "Error registering: " +
                            error.getMessage(), Toast.LENGTH_SHORT).show();
                        }
                    }
                };
                mUserManager.setRegistrationResultListener(resultListener);
                mUserManager.registerWithEmailPassword(email, password);

            }
        });
    }

    @Override
    protected void onResume() {
        super.onResume();
        progressBar.setVisibility(View.GONE);
    }

    @Override
    protected void onStop() {
        if (mUserManager != null) {
            mUserManager.removeAllListeners();
        }
        super.onStop();
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == PROFILE_CHANGE_REQ) {
            Log.d("EditResult", "Got result OK");
            startActivity(new Intent(RegistrationActivity.this, MainActivity.class)
                .putExtra("user", mUserProfile));
            finish();
        }
    }
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                finish();
                return true;
            default:
                // If we got here, the user's action was not recognized.
                // Invoke the superclass to handle it.
                return super.onOptionsItemSelected(item);

        }
    }
}
