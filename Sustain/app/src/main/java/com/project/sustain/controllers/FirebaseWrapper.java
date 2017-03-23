package com.project.sustain.controllers;


import android.support.annotation.NonNull;
import android.util.Log;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.UserProfileChangeRequest;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.project.sustain.model.User;

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
    private Object singleResult;
    private String userDisplayName = "";
    private boolean isLoggedIn = false;
    private String userId = "";
    private String userEmail = "";
    private AuthResultListener mAuthResultListener = null;
    private QueryListResultListener mQueryListResultListener = null;
    private QuerySingleResultListener mQuerySingleResultListener = null;
    private Object mModelObject = null;
    private User currentUser = null;

    private static String TAG = "FirebaseWrapper";

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
                    userEmail = mUser.getEmail();


                } else {
                    isLoggedIn = false;
                    userDisplayName = "";
                    userId = "";
                    userEmail = "";
                }

            }
        };

    }

    public String getCurrentUserId() {
        return userId;
    }

    public String getCurrentUserDisplayName() {
        return userDisplayName;
    }

    /**
     * Updates the user's display name in the Firebase auth user store.
     */
    public void updateCurrentUserDisplayName(String newName) {
        final String name = newName;
        UserProfileChangeRequest profileUpdates = new UserProfileChangeRequest.Builder()
                .setDisplayName(newName)
                .build();

        mUser.updateProfile(profileUpdates)
                .addOnCompleteListener(new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {
                        if (task.isSuccessful()) {
                            Log.d(TAG, "User display name updated.");
                            userDisplayName = name;
                        }
                    }
                });
    }

    public String getCurrentUserEmail() {
        return userEmail;
    }

    public boolean isLoggedIn() {
        return isLoggedIn;
    }

    public void connect() {
        mFirebaseAuth.addAuthStateListener(mAuthListener);
    }

    public void disconnect() {
        if (mAuthListener != null) {
            mFirebaseAuth.removeAuthStateListener(mAuthListener);
        }
    }

    public void createAccount (String email, String password) {
        mFirebaseAuth.createUserWithEmailAndPassword(email, password)
                .addOnCompleteListener(
                new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            if (mAuthResultListener != null) {
                                mAuthResultListener.onComplete("Registration successful.");
                            }
                        } else {
                            if (mAuthResultListener != null) {
                                mAuthResultListener.onComplete("Registration failed.");
                            }
                        }
                    }
                });
    }

    public void loginWithEmail(String email, String password) {
        mFirebaseAuth.signInWithEmailAndPassword(email, password)
                .addOnCompleteListener(
                        new OnCompleteListener<AuthResult>() {
                            @Override
                            public void onComplete(@NonNull Task<AuthResult> task) {
                            if (!task.isSuccessful()) {
                                if (mAuthResultListener != null) {
                                    mAuthResultListener.onComplete("Login successful.");
                                }
                            } else {
                                if (mAuthResultListener != null) {
                                    mAuthResultListener.onComplete("Login failed.");
                                }
                            }
                    }
                });
    }

    public void logOut() {
        mFirebaseAuth.signOut();
    }



    @Override
    public <T> void queryDatabaseForListAsync(String query, T modelObject) {
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
                mQueryListResultListener.onComplete(resultList);
            }

            // Iterate through waterReportList to get each ones water report list

            @Override
            public void onCancelled(DatabaseError databaseError) {
                Throwable error = new Throwable(databaseError.getMessage(),
                        databaseError.toException());
                mQueryListResultListener.onError(error);

            }
        });
    }

    @Override
    public <T> void queryDatabaseForSingleAsync(String query, T modelObject) {
        mModelObject = modelObject;
        mDatabaseReference = mFirebaseDatabase.getReference().child(query);
        mDatabaseReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                    singleResult = dataSnapshot.getValue(mModelObject.getClass());
                    mQuerySingleResultListener.onComplete((T) singleResult);
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                Throwable error = new Throwable(databaseError.getMessage(),
                        databaseError.toException());
                mQuerySingleResultListener.onError(error);

            }
        });
    }

    @Override
    public <T> void insertSingleRecord(String recordLocation, T modelObject) {
        mDatabaseReference = mFirebaseDatabase.getReference().child(recordLocation);
        mDatabaseReference.push().setValue(modelObject);
    }

    @Override
    public <T> void updateSingleRecord(String recordLocation, T modelObject) {
        mDatabaseReference = mFirebaseDatabase.getReference().child(recordLocation);
        mDatabaseReference.setValue(modelObject);
    }

    public void setQueryListResultListener(QueryListResultListener listener) {
        this.mQueryListResultListener = listener;
    }

    @Override
    public void removeQueryResultListener() {
        this.mQueryListResultListener = null;
    }

    @Override
    public void setQuerySingleResultListener(QuerySingleResultListener listener) {
        this.mQuerySingleResultListener = listener;
    }

    @Override
    public void removeQuerySingleResultListener() {
        this.mQuerySingleResultListener = null;
    }


    public void setAuthResultListener(AuthResultListener listener) {
        this.mAuthResultListener = listener;
    }

    public void removeAuthResultListener() {
        this.mAuthResultListener = null;
    }

}
