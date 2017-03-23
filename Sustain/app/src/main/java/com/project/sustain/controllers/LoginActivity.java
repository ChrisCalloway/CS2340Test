package com.project.sustain.controllers;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.project.sustain.R;
import com.project.sustain.model.User;
import com.project.sustain.model.UserManager;


public class LoginActivity extends AppCompatActivity {
    private EditText enteredUsername, enteredPassword;
    private Button btnLogin, btnCancelLogin;
    private UserManager mUserManager;
    private User mUser;
    private LoginResultListener mLoginResultListener;

    @Override
    protected void onCreate (Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        mUserManager = new UserManager();

        setContentView(R.layout.activity_login);

        // set Log In result listener
        mLoginResultListener = new LoginResultListener() {
            @Override
            public void onComplete(boolean success) {
                if (success) {
                    startActivity(new Intent(LoginActivity.this, MainActivity.class));
                    finish();
                } else {
                    Toast.makeText(getApplicationContext(), "Login failed.",
                            Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onError(Throwable error) {
                Toast.makeText(getApplicationContext(), "Error logging in: " +
                        error.getMessage(), Toast.LENGTH_SHORT).show();
            }
        };


        btnLogin = (Button) findViewById(R.id.buttonLogin);
        btnCancelLogin = (Button) findViewById(R.id.buttonCancelLogin);
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

                // log in asynchronously
                mUserManager.setLoginResultListener(mLoginResultListener);
                mUserManager.logInUserEmailPassword(username, password);


            }
        });

        btnCancelLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(LoginActivity.this, WelcomeActivity.class));
                finish();
            }
        });
    }

    @Override
    protected void onStart() {
        super.onStart();
        if (mLoginResultListener != null) {
            mUserManager.setLoginResultListener(mLoginResultListener);
        }
    }

    @Override
    protected void onStop() {
        super.onStop();
        if (mLoginResultListener != null) {
            mUserManager.removeLoginResultListener();
        }
    }
}