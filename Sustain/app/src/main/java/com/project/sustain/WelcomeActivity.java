package com.project.sustain;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.telecom.Connection;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.google.android.gms.auth.api.Auth;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.auth.api.signin.GoogleSignInResult;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.SignInButton;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.common.api.ResultCallback;
import com.google.android.gms.common.api.Status;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;


public class WelcomeActivity extends AppCompatActivity implements
        GoogleApiClient.OnConnectionFailedListener,
        View.OnClickListener {
    private Button btnRegister, btnLogin;
    private FirebaseAuth mAuth;
    private FirebaseAuth.AuthStateListener mAuthListener;
    private SignInButton btnGoogleSignIn;
    private static final String TAG = "SignInActivity";
    private static final int RC_SIGN_IN = 9001;
    private static final int EMAIL_SIGN_IN = 9002;
    private static final int RC_SIGN_OUT = 9003;
    private static final int EMAIL_SIGN_OUT = 9004;
    private static final String WEB_CLIENT_ID = "2016825544-7brg16j9chfn41v40acmpf5vmtopb5j6.apps.googleusercontent.com";

    private GoogleApiClient mGoogleApiClient;
    private ProgressDialog mProgressDialog;

    @Override
    protected void onCreate (Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_welcome);

        mAuth = FirebaseAuth.getInstance();

        btnLogin = (Button) findViewById(R.id.buttonWelcomeLogin);
        btnRegister = (Button) findViewById(R.id.buttonWelcomeRegister);
        btnGoogleSignIn = (SignInButton) findViewById(R.id.buttonGoogleSignIn);

        btnLogin.setOnClickListener(this);
        btnRegister.setOnClickListener(this);
        btnGoogleSignIn.setOnClickListener(this);

        InitializeGoogleApi();


        mAuthListener = new FirebaseAuth.AuthStateListener() {
            @Override
            public void onAuthStateChanged(@NonNull FirebaseAuth firebaseAuth) {
                FirebaseUser user = firebaseAuth.getCurrentUser();
                if (user != null) {
                    // User is signed in
                    Log.d(TAG, "onAuthStateChanged:signed_in:" + user.getUid());
                } else {
                    // User is signed out
                    Log.d(TAG, "onAuthStateChanged:signed_out");
                }
            }
        };
    }

    private void InitializeGoogleApi() {
        // Configure sign-in to request the user's ID, email address, and basic
        // profile. ID and basic profile are included in DEFAULT_SIGN_IN.
        GoogleSignInOptions gso = new GoogleSignInOptions.Builder(GoogleSignInOptions
                .DEFAULT_SIGN_IN).requestIdToken(WEB_CLIENT_ID)
                .requestEmail().build();

        // Build a GoogleApiClient with access to the Google Sign-In API and the
        // options specified by gso.
        mGoogleApiClient = new GoogleApiClient.Builder(this)
                .enableAutoManage(this /* FragmentActivity */,
                this /* OnConnectionFailedListener */)
                .addApi(Auth.GOOGLE_SIGN_IN_API, gso)
                .build();
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        // Result returned from launching the Intent from GoogleSignInApi.getSignInIntent(...);
        switch (requestCode) {
            case RC_SIGN_IN:
                GoogleSignInResult result = Auth.GoogleSignInApi.getSignInResultFromIntent(data);
                handleGoogleSignInResult(result);
                break;
            case EMAIL_SIGN_IN:
                handleEmailSignInResult(resultCode, data);
                break;
            case RC_SIGN_OUT:
                Log.d(TAG, "Received response OK from RC_SIGN_OUT");
                signOutOfGoogle();
                break;
            case EMAIL_SIGN_OUT:
                signOutOfEmail();
                break;
        }
    }

    private void handleGoogleSignInResult(GoogleSignInResult result) {
        Log.d(TAG, "handleGoogleSignInResult:" + result.isSuccess());
        if (result.isSuccess()) {
            // Signed in successfully, show authenticated UI.
            GoogleSignInAccount acct = result.getSignInAccount();
            startActivityForResult(new Intent(WelcomeActivity.this, MainActivity.class),
                    RC_SIGN_OUT);

        } else {
            // Signed out, show unauthenticated UI.
            Toast.makeText(getApplicationContext(), "Login unsuccessful.",
                    Toast.LENGTH_SHORT).show();
        }
    }

    private void handleEmailSignInResult(int resultCode, Intent data) {
        if (resultCode == Activity.RESULT_OK) {
            startActivityForResult(new Intent(WelcomeActivity.this, MainActivity.class),
                    EMAIL_SIGN_OUT);
        } else {
            //login failed message is displayed on email login activity screen.
        }
    }

    private void signInWithGoogle() {
        Intent signInIntent = Auth.GoogleSignInApi.getSignInIntent(mGoogleApiClient);
        startActivityForResult(signInIntent, RC_SIGN_IN);
    }

    private void signOutOfGoogle() {
        boolean ready = mGoogleApiClient.isConnected();
        if (!ready) {
            }
            mGoogleApiClient.connect();
            ready = mGoogleApiClient.isConnected();
        }
        if (ready) {
            Auth.GoogleSignInApi.signOut(mGoogleApiClient).setResultCallback(
                    new ResultCallback<Status>() {
                        @Override
                        public void onResult(Status status) {
                            // [START_EXCLUDE]
                            Log.d(TAG, "Google signout status: " + status.getStatusMessage());
                            // [END_EXCLUDE]
                        }
                    });
        }
    }

    private void signOutOfEmail() {
        mAuth.signOut();
    }

    @Override
    public void onConnectionFailed(ConnectionResult connectionResult) {
        // An unresolvable error has occurred and Google APIs (including Sign-In) will not
        // be available.
        Log.d(TAG, "onConnectionFailed:" + connectionResult);
    }

    private void showProgressDialog() {
        if (mProgressDialog == null) {
            mProgressDialog = new ProgressDialog(this);
            mProgressDialog.setMessage(getString(R.string.loading));
            mProgressDialog.setIndeterminate(true);
        }

        mProgressDialog.show();
    }

    private void hideProgressDialog() {
        if (mProgressDialog != null && mProgressDialog.isShowing()) {
            mProgressDialog.hide();
        }
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.buttonGoogleSignIn:
                signInWithGoogle();
                break;
            case R.id.buttonWelcomeLogin:
                //sign in with email
                startActivityForResult(new Intent(WelcomeActivity.this, LoginActivity.class),
                        EMAIL_SIGN_IN);
                break;
            case R.id.buttonWelcomeRegister:
                startActivity(new Intent(WelcomeActivity.this, RegistrationActivity.class));
                break;
     /*       case R.id.disconnect_button:
                revokeAccess();
                break; */
        }
    }
    @Override
    public void onStart() {
        super.onStart();
        mAuth.addAuthStateListener(mAuthListener);
    }

    @Override
    public void onStop() {
        super.onStop();
        if (mAuthListener != null) {
            mAuth.removeAuthStateListener(mAuthListener);
        }
    }
}
