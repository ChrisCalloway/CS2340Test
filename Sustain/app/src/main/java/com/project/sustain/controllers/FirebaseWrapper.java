package com.project.sustain.controllers;


import android.support.annotation.NonNull;

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

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Marcia on 3/14/2017.
 */

public class FirebaseWrapper implements DatabaseWrapper {

    //Firebase objects
    private FirebaseAuth mFirebaseAuth;
    private FirebaseAuth.AuthStateListener mAuthListener;
    private FirebaseUser mUser;
    private FirebaseDatabase mFirebaseDatabase;
    private DatabaseReference mDatabaseReference;

    private String userDisplayName = "";
    private boolean isLoggedIn = false;
    private String userId = "";
    private RegistrationResultListener mRegistrationResultListener = null;
    private QueryResultListener mQueryResultListener = null;
    private Object mModelObject = null;

    public FirebaseWrapper() {
        mFirebaseAuth = FirebaseAuth.getInstance();
        mFirebaseDatabase = FirebaseDatabase.getInstance();

        mAuthListener = new FirebaseAuth.AuthStateListener() {
            @Override
            public void onAuthStateChanged(@NonNull FirebaseAuth firebaseAuth) {
                mUser = mFirebaseAuth.getCurrentUser();
                if (mUser != null) {
                    isLoggedIn = true;
                    userDisplayName = mUser.getDisplayName();
                    userId = mUser.getUid();


                } else {
                    isLoggedIn = false;
                    userDisplayName = "";
                }

            }
        };

    }

    public void connect() {
        mFirebaseAuth.addAuthStateListener(mAuthListener);
    }

    public void disconnect() {
        if (mAuthListener != null) {
            mFirebaseAuth.removeAuthStateListener(mAuthListener);
        }
    }

    public void setRegistrationResultListener(RegistrationResultListener listener) {
        this.mRegistrationResultListener = listener;
    }

    public void removeRegistrationResultListener() {
        this.mRegistrationResultListener = null;
    }

    public void createAccount (String email, String password) {
        mFirebaseAuth.createUserWithEmailAndPassword(email, password)
                .addOnCompleteListener(
                new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            if (mRegistrationResultListener != null) {
                                mRegistrationResultListener.onComplete("Registration successful.");
                            }
                        } else {
                            if (mRegistrationResultListener != null) {
                                mRegistrationResultListener.onComplete("Registration failed.");
                            }
                        }
                    }
                });
    }

    @Override
    public <T> void queryDatabaseAsync(String query, T modelObject) {
        mModelObject = modelObject;
        final List<T> resultList = new ArrayList<T>();
        mDatabaseReference = mFirebaseDatabase.getReference().child(query);
        mDatabaseReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                for (DataSnapshot snapshot: dataSnapshot.getChildren()) {
                    Object result = snapshot.getValue(mModelObject.getClass());
                    resultList.add((T) result);
                }
                mQueryResultListener.onComplete(resultList);
            }

            // Iterate through waterReportList to get each ones water report list

            @Override
            public void onCancelled(DatabaseError databaseError) {
                Throwable error = new Throwable(databaseError.getMessage(),
                        databaseError.toException());
                mQueryResultListener.onError(error);

            }
        });
    }

    @Override
    public void setQueryResultListener(QueryResultListener listener) {
        this.mQueryResultListener = listener;
    }

    @Override
    public void removeQueryResultListener() {
        this.mQueryResultListener = null;
    }

    @Override
    public <T> void saveSingleRecord(String recordLocation, T modelObject) {
        mDatabaseReference = mFirebaseDatabase.getReference().child(recordLocation);
        mDatabaseReference.push().setValue(modelObject);
    }

}
