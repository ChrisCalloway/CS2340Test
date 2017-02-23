package com.project.sustain;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;


public class LoginActivity extends AppCompatActivity {
    private EditText enteredUsername, enteredPassword;
    private Button btnLogin, btnCancelLogin;
    private FirebaseAuth auth;

    @Override
    protected void onCreate (Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        // Get Firebase auth instance
        auth = FirebaseAuth.getInstance();

        // Checks if there is current user already.  Therefore, do not have to log in again.
//        if (auth.getCurrentUser() != null) {
//            startActivity(new Intent(LoginActivity.this, MainActivity.class));
//            finish();
//        }

        setContentView(R.layout.activity_login);


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

        btnCancelLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(LoginActivity.this, WelcomeActivity.class));
                finish();
            }
        });
    }
}