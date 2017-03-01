package com.project.sustain.controllers;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
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

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.project.sustain.R;
import com.project.sustain.model.Address;
import com.project.sustain.model.UserProfile;
import com.project.sustain.model.UserType;

public class RegistrationActivity extends AppCompatActivity {
    private EditText enteredEmail, enteredPassword;
    private Button btnRegister, btnCancelRegistration;
    private ProgressBar progressBar;
    private FirebaseAuth auth;
    private Spinner selectedUserType;
    private FirebaseAuth mFirebaseAuth;
    private FirebaseUser mFirebaseUser;
    private FirebaseDatabase mDatabase;
    private DatabaseReference mProfiles;
    private UserProfile mUserProfile;
    public static final int PROFILE_CHANGE_REQ = 1000;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registration);

        // Get Firebase auth instance
        auth = FirebaseAuth.getInstance();
        btnRegister = (Button) findViewById(R.id.buttonRegister);
        btnCancelRegistration = (Button) findViewById(R.id.buttonCancelRegistration);
        enteredEmail = (EditText) findViewById(R.id.editEmail);
        enteredPassword = (EditText) findViewById(R.id.editPassword);
        progressBar = (ProgressBar) findViewById(R.id.registerProgressBar);
        selectedUserType = (Spinner) findViewById(R.id.spinnerUserType);

        //get refs to Firebase user objects
        mFirebaseAuth = FirebaseAuth.getInstance();
        mFirebaseUser = mFirebaseAuth.getCurrentUser();
        mDatabase = FirebaseDatabase.getInstance();
        mProfiles = mDatabase.getReference().child("userProfiles");

        //fill drop-down boxes
        selectedUserType.setAdapter(new ArrayAdapter<UserType>(this, R.layout.support_simple_spinner_dropdown_item,
                UserType.values()));

        btnRegister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String email = enteredEmail.getText().toString().trim();
                String password = enteredPassword.getText().toString().trim();

                final UserProfile profile = new UserProfile();
                final UserType userType = (UserType) selectedUserType.getSelectedItem();
                profile.setUserType(userType);
                profile.setEmailAddress(email);

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

                auth.createUserWithEmailAndPassword(email, password)
                        .addOnCompleteListener(RegistrationActivity.this, new OnCompleteListener<AuthResult>() {
                            @Override
                            public void onComplete(@NonNull Task<AuthResult> task) {
                                // Hide progress bar
                                progressBar.setVisibility(View.GONE);

                                // If sign in fails, display a toast message to the user.  If the sign in succeeds,
                                // the auth state listener will be notified and logic to handle the signed in
                                // user can be handled in the listener.  On success, user is taken to main page
                                // of application.
                                if (!task.isSuccessful()) {

                                    Toast.makeText(RegistrationActivity.this, "Authentication failed: " + task.getException().getMessage(),
                                            Toast.LENGTH_SHORT).show();
                                } else {
                                    Toast.makeText(RegistrationActivity.this, "Successfully registered and signed in.", Toast.LENGTH_SHORT).show();
                                    // Logic to save to database
                                    FirebaseUser mUser = task.getResult().getUser();
                                    mProfiles.child(mUser.getUid()).setValue(profile);
                                    startActivityForResult(new Intent(RegistrationActivity.this, EditProfileActivity.class), PROFILE_CHANGE_REQ);


                                }
                            }
                        });
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
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == PROFILE_CHANGE_REQ) {
            Log.d("EditResult", "Got result OK");
            startActivity(new Intent(RegistrationActivity.this, MainActivity.class));
            finish();

        }
    }
}
