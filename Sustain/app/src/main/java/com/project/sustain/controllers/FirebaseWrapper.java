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
    private RegistrationResultListener mRegistrationResultListener = null;
    private Object mModelObject = null;
    private User currentUser = null;

    private static String TAG = "FirebaseWrapper";

    public FirebaseWrapper() {
        mFirebaseAuth = FirebaseAuth.getInstance();
        mFirebaseDatabase = FirebaseDatabase.getInstance();

        // Note that this listens if there is a change to the FirebaseAuth instance.
        // So, when a user is created in the FirebaseAuth, the onAuthStateChanged function below
        // is invoked and so getCurrentUser() is called.  This is how userID is assigned
        // below for the mRegistrationResultListener
        mAuthListener = new FirebaseAuth.AuthStateListener() {
            @Override
            public void onAuthStateChanged(@NonNull FirebaseAuth firebaseAuth) {
                Log.d(TAG, "Auth state changed");
                getCurrentUser();
            }
        };
        mFirebaseAuth.addAuthStateListener(mAuthListener);
    }

    /**
     * Makes call to the mFirebaseAuth object's getCurrentUser(), which returns an object
     * with the display name, unique ID, and email of the user.  This method also sets
     * isLoggedIn, a flag field variable, to true if a user is returned.
     */
    private void getCurrentUser() {
        mUser = mFirebaseAuth.getCurrentUser();
        if (mUser != null) {
            isLoggedIn = true;
            userDisplayName = mUser.getDisplayName() + "";
            userId = mUser.getUid() + "";
            userEmail = mUser.getEmail() + "";


        } else {
            isLoggedIn = false;
            userDisplayName = "";
            userId = "";
            userEmail = "";
        }
    }

    public String getCurrentUserId() {
        if (mUser == null) {
            getCurrentUser();
        }

        return userId;
    }

    public String getCurrentUserDisplayName() {
        if (mUser == null) {
            getCurrentUser();
        }

        return userDisplayName;
    }

    /**
     * Updates the user's display name in the Firebase auth user store.
     * This uses the Firebase library's UserProfileChangeRequest object to pass in a new
     * display name to the FirebaseUser object's updateProfile method.
     * @param newName The new display name
     */
    public void updateCurrentUserDisplayName(String newName) {
        final String name = newName;
        UserProfileChangeRequest profileUpdates = new UserProfileChangeRequest.Builder()
                .setDisplayName(newName)
                .build();

        if (mUser == null) { getCurrentUser(); }
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
        if (mUser == null) {
            getCurrentUser();
        }
        return userEmail;
    }

    public boolean isLoggedIn() {
        if (mUser == null) {
            getCurrentUser();
        }
        return isLoggedIn;
    }

    /**
     * Similar to what is done in the class's constructor method, an AuthStateListener is
     * passed into the FirebaseAuth instance with its addAuthStateListener method.
     */
    public void connect() {
        mFirebaseAuth.addAuthStateListener(mAuthListener);
    }

    /**
     * Disconnects the app from the FirebaseAuth instance by removing the AuthStateListener from the
     * mFirebaseAuth instance.
     */
    public void disconnect() {
        if (mAuthListener != null) {
            mFirebaseAuth.removeAuthStateListener(mAuthListener);
        }

    }

    /**
     * Makes call to FirebaseAuth instance and creates a new user there with the
     * email and password provided.  Note that the mFirebaseAuth has an AuthStateListener
     * that invokes getCurrentUser() on state change (as in when a user is newly created).
     * It is through this mechanism that userID is updated and the value is passed up the
     * chain to the mUserManager object.
     * @param email Any valid email address a user has inputted.
     * @param password Any valid password a user has inputted.
     */
    public void createAccountWithEmailPassword (String email, String password) {
        mFirebaseAuth.createUserWithEmailAndPassword(email, password)
            .addOnCompleteListener(
                new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
   //                     AuthResult result = task.getResult();
                        if (task.isSuccessful()) {
//                            mUser = result.getUser();
//                            userId = mUser.getUid();
//                            userEmail = mUser.getEmail();
//                            userDisplayName = mUser.getDisplayName();
//                            isLoggedIn = true;
                            if (mRegistrationResultListener != null) {
                                // Where does userID get assigned the value of the userID in
                                // Firebase?
                                mRegistrationResultListener.onComplete(true, userId);
                            }
                        } else {
                            if (mRegistrationResultListener != null) {
                                mRegistrationResultListener.onComplete(false, "");
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
                            if (task.isSuccessful()) {
                                if (mAuthResultListener != null) {
                                    mAuthResultListener.onComplete(true);
                                }
                            } else {
                                if (mAuthResultListener != null) {
                                    mAuthResultListener.onComplete(false);
                                }
                            }
                    }
                });
    }

    public void logOut() {
        mFirebaseAuth.signOut();
    }



    @Override
    public <T> void queryDatabaseForListAsync(String query, final T modelObject) {
        mModelObject = modelObject;
        final List<T> resultList = new ArrayList<T>();
        mDatabaseReference = mFirebaseDatabase.getReference().child(query);
        if (mDatabaseReference != null) {
            mDatabaseReference.addValueEventListener(new ValueEventListener() {
                @Override
                public void onDataChange(DataSnapshot dataSnapshot) {
                    for (DataSnapshot snapshot : dataSnapshot.getChildren()) {
                        Object result = snapshot.getValue(mModelObject.getClass());
                        Object key = snapshot.getKey();
                        if (mQueryListResultListener != null) {
                            mQueryListResultListener.onComplete(result, key);
                        }
                    }
                }


                // Iterate through waterReportList to get each ones water report list

                @Override
                public void onCancelled(DatabaseError databaseError) {
                    if (databaseError != null) {
                        Throwable error = new Throwable(databaseError.getMessage(),
                                databaseError.toException());
                        if (mQueryListResultListener != null) {
                            mQueryListResultListener.onError(error);
                        }
                    }
                }
            });
        }
    }

    @Override
    public <T> void queryDatabaseForSingleAsync(String query, T modelObject) {
        mModelObject = modelObject;
        mDatabaseReference = mFirebaseDatabase.getReference().child(query);
        mDatabaseReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                    singleResult = dataSnapshot.getValue(mModelObject.getClass());
                if(mQuerySingleResultListener != null) {

                    mQuerySingleResultListener.onComplete((T) singleResult);
                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                if (databaseError != null) {
                    Throwable error = new Throwable(databaseError.getMessage(),
                            databaseError.toException());
                    if (mQuerySingleResultListener != null) {
                        mQuerySingleResultListener.onError(error);
                    }
                }
            }
        });
    }

    @Override
    public <T> void insertSingleRecord(String recordLocation, T modelObject) {
        mDatabaseReference = mFirebaseDatabase.getReference().child(recordLocation);
        if (mDatabaseReference != null) {
            mDatabaseReference.push().setValue(modelObject);

        }
    }

    @Override
    public <T> void updateSingleRecord(String recordLocation, T modelObject) {
        mDatabaseReference = mFirebaseDatabase.getReference().child(recordLocation);
        if (mDatabaseReference != null) {
            Log.d(TAG, "Setting value of user profile");
            mDatabaseReference.setValue(modelObject);
        } else {
            Log.d(TAG, "mDatabaseReference is null");
        }
    }

    public void setQueryListResultListener(QueryListResultListener listener) {
        this.mQueryListResultListener = listener;
    }

    @Override
    public void removeQueryListResultListener() {
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

    public void setRegistrationResultListener(RegistrationResultListener listener) {
        this.mRegistrationResultListener = listener;
    }

    public void removeRegistrationResultListener() {
        this.mRegistrationResultListener = null;
    }

}
