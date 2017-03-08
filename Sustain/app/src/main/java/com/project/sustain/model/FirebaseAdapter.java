package com.project.sustain.model;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.project.sustain.controllers.LoginActivity;
import com.project.sustain.controllers.MainActivity;
import com.google.android.gms.tasks.Task;


/**
 * Created by Chris on 3/8/17.
 */

public class FirebaseAdapter {
    private FirebaseUser firebaseUser;
    private FirebaseDatabase firebaseDatabase;
    private FirebaseAuth auth;
    private DatabaseReference firebaseDatabaseSection;

    private static FirebaseAdapter firebaseInstance = new FirebaseAdapter();


    /**
     * private constructor for facade pattern
     */
    private FirebaseAdapter() {
        firebaseUser = FirebaseAuth.getInstance().getCurrentUser();
        firebaseDatabase = FirebaseDatabase.getInstance();
        auth = FirebaseAuth.getInstance();
    }

    /**
     * private constructor for facade pattern, takes param string
     * @param dbSection
     */
    private FirebaseAdapter(String dbSection) {
        firebaseUser = FirebaseAuth.getInstance().getCurrentUser();
        firebaseDatabase = FirebaseDatabase.getInstance();
        auth = FirebaseAuth.getInstance();
        firebaseDatabaseSection = firebaseDatabase.getReference(dbSection);
    }

    public static FirebaseAdapter getInstance() { return firebaseInstance; }

    public DatabaseReference getFirebaseDatabaseSection() {

    }

    public String login(String username, String password) {
        String result;
        auth.signInWithEmailAndPassword(username, password)
                .addOnCompleteListener(FirebaseAdapter.this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        // Hide progress bar
                        // If sign in fails, display a toast message to the user.  If the sign in succeeds,
                        // the auth state listener will be notified and logic to handle the signed in
                        // user can be handled in the listener.  On success, user is taken to main page
                        // of application.
                        if (!task.isSuccessful()) {
                            if (password.length() < 6) {
                                return "Password too short";
                            } else {
                                Toast.makeText(LoginActivity.this, "Login failed", Toast.LENGTH_LONG).show();
                            }
                        } else {
                            result =  "Good to go";
                        }
                    }
                });
        return result;
    }



}
