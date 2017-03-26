package com.project.sustain.controllers;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.NavUtils;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.text.TextUtils;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.project.sustain.R;


public class LoginActivity extends AppCompatActivity {
    private EditText enteredUsername, enteredPassword;
    private Button btnLogin;
    private FirebaseAuth auth;

    @Override
    protected void onCreate (Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        // Get Firebase auth instance
        auth = FirebaseAuth.getInstance();

        // Checks if there is current user already.  Therefore, do not have to log in again.
//        if (auth.getCurrentUser() != null) {
//            startActivity(new Intent(LoginActivity.this, MainActivity.class));
//            finish();
//        }

        Toolbar toolbar = (Toolbar) findViewById(R.id.activity_login_toolbar);
        toolbar.setTitle("Login to account");
        this.setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);

        btnLogin = (Button) findViewById(R.id.buttonLogin);
        enteredUsername = (EditText) findViewById(R.id.editEmail);
        enteredPassword = (EditText) findViewById(R.id.editPassword);

        btnLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String username = enteredUsername.getText().toString();
                final String password = enteredPassword.getText().toString();

                if (TextUtils.isEmpty(username)) {
                    Toast.makeText(getApplicationContext(), "Please enter username", Toast.LENGTH_SHORT).show();
                    return;
                }
                if (TextUtils.isEmpty(password)) {
                    Toast.makeText(getApplicationContext(), "Please enter password", Toast.LENGTH_SHORT).show();
                    return;
                }

                // Create user
                auth.signInWithEmailAndPassword(username, password)
                        .addOnCompleteListener(LoginActivity.this, new OnCompleteListener<AuthResult>() {
                            @Override
                            public void onComplete(@NonNull Task<AuthResult> task) {
                                // Hide progress bar
                                // If sign in fails, display a toast message to the user.  If the sign in succeeds,
                                // the auth state listener will be notified and logic to handle the signed in
                                // user can be handled in the listener.  On success, user is taken to main page
                                // of application.
                                if (!task.isSuccessful()) {
                                    if (password.length() < 6) {
                                        enteredPassword.setError("Password too short");
                                    } else {
                                        Toast.makeText(LoginActivity.this, "Login failed", Toast.LENGTH_LONG).show();
                                    }
                                } else {
                                    startActivity(new Intent(LoginActivity.this, MainActivity.class));
                                    finish();
                                }
                            }
                        });
            }
        });
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                startActivity(new Intent(LoginActivity.this, WelcomeActivity.class));
                finish();
                return true;
            default:
                // If we got here, the user's action was not recognized.
                // Invoke the superclass to handle it.
                return super.onOptionsItemSelected(item);

        }
    }
}