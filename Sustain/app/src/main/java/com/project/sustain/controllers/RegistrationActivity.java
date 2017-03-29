package com.project.sustain.controllers;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.util.Log;
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

public class RegistrationActivity extends AppCompatActivity {
    private EditText enteredEmail, enteredPassword;
    private Button btnRegister, btnCancelRegistration;
    private ProgressBar progressBar;
    private Spinner selectedUserType;
    private User mUserProfile;
    private UserManager mUserManager;
    public static final int PROFILE_CHANGE_REQ = 1000;
    public static final String TAG = "RegistrationActivity";


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registration);

        // Get UserManager instance
        mUserManager = new UserManager();
        btnRegister = (Button) findViewById(R.id.buttonRegister);
        btnCancelRegistration = (Button) findViewById(R.id.buttonCancelRegistration);
        enteredEmail = (EditText) findViewById(R.id.editEmail);
        enteredPassword = (EditText) findViewById(R.id.editPassword);
        progressBar = (ProgressBar) findViewById(R.id.registerProgressBar);
        selectedUserType = (Spinner) findViewById(R.id.spinnerUserType);


        //fill drop-down boxes
        selectedUserType.setAdapter(new ArrayAdapter<UserType>(this, R.layout.support_simple_spinner_dropdown_item,
                UserType.values()));

        // On register, a couple of checks occur to make sure the password and email meet basic
        // tests
        btnRegister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final String email = enteredEmail.getText().toString().trim();
                String password = enteredPassword.getText().toString().trim();

                if (TextUtils.isEmpty(email)) {
                    Toast.makeText(getApplicationContext(), "Please enter email address", Toast.LENGTH_SHORT).show();
                    return;
                }
                if (TextUtils.isEmpty(password)) {
                    Toast.makeText(getApplicationContext(), "Please enter password", Toast.LENGTH_SHORT).show();
                    return;
                }
                if (password.length() < 6) {
                    Toast.makeText(getApplicationContext(), "Password too short, please enter minimum 6 characters", Toast.LENGTH_SHORT).show();
                    return;
                }

                progressBar.setVisibility(View.VISIBLE);

                // Create user
                //
                RegistrationResultListener resultListener = new RegistrationResultListener() {
                    @Override
                    // The RegistrationResultListener has two methods, onComplete and
                    // onError.
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
                            Intent intent = new Intent(RegistrationActivity.this, EditProfileActivity.class);
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
                // Note that mUserManager is a model class with a RegistrationResultLister
                // field variable.  This field variable is set to the resultListener
                // that was just instantiated above with the onComplete and onError methods defined.
                mUserManager.setRegistrationResultListener(resultListener);

                // This is a method of the UserManager class that uses its very own
                // RegistrationResultListener.  Within this method, an invocation on the
                // mUserManager's field variable DatabaseWrapper DBWrapper's
                // setRegistrationResultListener is made with the resultListener instantiated
                // there.
                mUserManager.registerWithEmailPassword(email, password);
            }
        });

        btnCancelRegistration.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(RegistrationActivity.this, WelcomeActivity.class));
                finish();
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
            startActivity(new Intent(RegistrationActivity.this, MainActivity.class));
            finish();

        }
    }
}
