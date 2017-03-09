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
    private static FirebaseAuth auth;
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

    public static FirebaseAuth getAuthUser() { return auth; }

//    public DatabaseReference getFirebaseDatabaseSection() {
//
//    }




}
