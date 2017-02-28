package com.project.sustain;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
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

public class RegistrationActivity extends AppCompatActivity {
    private EditText enteredEmail, enteredPassword, enteredUsername;
    private Button btnRegister, btnCancelRegistration;
    private ProgressBar progressBar;
    private FirebaseAuth auth;
    private DatabaseReference mFirebaseDatabase;
    private FirebaseDatabase mFirebaseInstance;


    private String userId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registration);

        // Get Firebase auth instance
        auth = FirebaseAuth.getInstance();

        // Get reference to database instance
        mFirebaseInstance = FirebaseDatabase.getInstance();

        // Get reference to "users" node of JSON database
        mFirebaseDatabase = mFirebaseInstance.getReference("users");

        btnRegister = (Button) findViewById(R.id.buttonRegister);
        btnCancelRegistration = (Button) findViewById(R.id.buttonCancelRegistration);
        enteredEmail = (EditText) findViewById(R.id.editEmail);
        enteredPassword = (EditText) findViewById(R.id.editPassword);
        enteredUsername = (EditText) findViewById(R.id.editUsername);
//        progressBar = (ProgressBar) findViewById(R.id.registerProgressBar);

        btnRegister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Need to set as final since being used in inner function
                final String email = enteredEmail.getText().toString().trim();
                final String username = enteredUsername.getText().toString().trim();
                String password = enteredPassword.getText().toString().trim();

                if (TextUtils.isEmpty(username)) {
                    Toast.makeText(getApplicationContext(), "Please enter a username", Toast.LENGTH_SHORT).show();
                    return;
                }
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

//                progressBar.setVisibility(View.VISIBLE);

                // Create user
                auth.createUserWithEmailAndPassword(email, password)
                        .addOnCompleteListener(RegistrationActivity.this, new OnCompleteListener<AuthResult>() {
                            @Override
                            public void onComplete(@NonNull Task<AuthResult> task) {
                                // Hide progress bar
//                                progressBar.setVisibility(View.GONE);

                                // If sign in fails, display a toast message to the user.  If the sign in succeeds,
                                // the auth state listener will be notified and logic to handle the signed in
                                // user can be handled in the listener.  On success, user is taken to main page
                                // of application.
                                if (!task.isSuccessful()) {
                                    Toast.makeText(RegistrationActivity.this, "Authentication failed: " + task.getException(), Toast.LENGTH_SHORT).show();
                                } else {
                                    Toast.makeText(RegistrationActivity.this, "Successfully registered.", Toast.LENGTH_SHORT).show();
                                    // Logic to save to database, get FirebaseUser for user just created
                                    FirebaseUser mUser = task.getResult().getUser();
                                    // user.getUid() is the id to use to save user?
                                    User user = new User(username, email);
                                    // So the .child(mUser.getUid()) should create a new node under 'users' that
                                    // has the mUser Uid as the key and the User object create as the value.
                                    mFirebaseDatabase.child(mUser.getUid()).setValue(user);
                                    startActivity(new Intent(RegistrationActivity.this, MainActivity.class));
                                    finish();
                                }
                            }
                        });
                // Will need to add logic to also save user in the Firebase Real Time Database

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

//    @Override
//    protected void onResume() {
//        super.onResume();
//        progressBar.setVisibility(View.GONE);
//    }
}
