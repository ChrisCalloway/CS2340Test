// Clean slate version


package com.project.sustain.controllers;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;

import com.project.sustain.R;
import com.project.sustain.model.User;
import com.project.sustain.model.UserManager;


public class MainActivity extends AppCompatActivity {
    private User mUser;
    private UserManager mUserManager;
    private UserResultListener mUserResultListener;
    public static final int PROFILE_CHANGE_REQ = 1000;
    public static String TAG = "MainActivity";


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Button btnSubmitReport;
        Button btnViewReport;
        Button btnViewMap;
        Button btnLogout;
        Toolbar toolbar;

        mUserManager = new UserManager();

        //result of asynchronous call to getCurrentUser()
        mUserResultListener = new UserResultListener() {
            @Override
            public void onComplete(User user) {
                Log.d(TAG, "UserResultListener onComplete");
                mUser = user;
                if (mUser == null) {
                    //user does not have a profile saved yet. Create one
                    mUser = new User();

                }
            }

            @Override
            public void onError(Throwable error) {
                if (error != null) {
                    Log.d(TAG, "UserResultListener onError: " + error.getMessage());
                }
            }
        };

        //get the User object for the current logged-in user
        //we will pass this on to the next activity
        //call is asynchronous; result handled by mUserResultListener.onComplete()
        mUserManager.setUserResultListener(mUserResultListener);
        mUserManager.getCurrentUser();

        setContentView(R.layout.activity_main);

        //add Toolbar as ActionBar with menu
        toolbar = (Toolbar) findViewById(R.id.main_toolbar);
        this.setSupportActionBar(toolbar);

        String userName = mUserManager.getCurrentUserDisplayName();
        String userEMail = mUserManager.getCurrentUserEmail();
        if (!userName.equals("") && !userName.equals("null")) {
            setToolbarTitle(userName);
        } else {
            setToolbarTitle(userEMail);
        }


        btnLogout = (Button) findViewById(R.id.buttonLogout);

        btnLogout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mUserManager.logOutUser();
                mUser = null;
                mUserManager.removeUserResultListener();

                startActivity(new Intent(MainActivity.this, LoginActivity.class));
                finish();
            }
        });

        btnSubmitReport = (Button) findViewById(R.id.subRep);

        //clicking Submit Report takes user to SetAddressActivity.
        //from there, user will get to one of the water report submit screens.
        btnSubmitReport.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, SetAddressActivity.class);
                intent.putExtra("user", mUser);
                startActivity(intent);
                finish();
               /* String toPassIn = getToolbarTitle();
                Intent forWtrRptSubmit = new Intent(MainActivity.this, WaterRptSubmitActivity.class);
                forWtrRptSubmit.putExtra("nameRetrieval", toPassIn);
                startActivity(forWtrRptSubmit); */
            }
        });

        btnViewReport = (Button) findViewById(R.id.viewReportBut);

        btnViewReport.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivityForResult(new Intent(MainActivity.this, ViewReportsActivity.class), 5000);
            }
        });

        btnViewMap = (Button) findViewById(R.id.buttonViewMap);

        btnViewMap.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(MainActivity.this, MapsMarkerActivity.class));
                finish();
            }
        });
    }

    /**
     * Sets the text in the toolbar to the text provided.,
     * @param name The name to put into the toolbar title.
     */
    private void setToolbarTitle(String name) {
        ActionBar actionBar = this.getSupportActionBar();
        if (actionBar !=null) { actionBar.setTitle(name + ""); }

    }

    private String getToolbarTitle() {
        ActionBar actionBar = this.getSupportActionBar();
        if (actionBar != null) {
            return actionBar.getTitle() + "";
        } else {
            return "";
        }
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.action_edit_profile:
                // User chose the "Edit Profile" action, show the user profile settings UI...
                Intent intent = new Intent(MainActivity.this, EditProfileActivity.class);
                intent.putExtra("user", mUser);
                startActivityForResult(intent, PROFILE_CHANGE_REQ);
            case R.id.action_settings:
                // User chose the "Settings" item, show the app settings UI...
                return true;

            default:
                // If we got here, the user's action was not recognized.
                // Invoke the superclass to handle it.
                return super.onOptionsItemSelected(item);

        }
    }

    // Menu icons are inflated just as they were with actionbar
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == PROFILE_CHANGE_REQ) {
            if (resultCode == RESULT_OK) {
                Log.d("EditResult", "Got result OK");
                mUser = (User) data.getSerializableExtra("user");
                setToolbarTitle(mUser.getUserName());
            }
        }
    }

    @Override
    protected void onStart() {
        super.onStart();
        if (mUserResultListener != null) {
            mUserManager.setUserResultListener(mUserResultListener);
        }
    }

    @Override
    protected void onStop() {
        super.onStop();
        if (mUserResultListener != null) {
            mUserManager.removeAllListeners();
        }
    }
}
