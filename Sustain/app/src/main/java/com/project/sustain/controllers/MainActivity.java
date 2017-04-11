// Clean slate version


package com.project.sustain.controllers;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
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


public class MainActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener {
    private User mUser;
    private UserManager mUserManager;
    // --Commented out by Inspection (4/10/2017 21:05 PM):private UserResultListener mUserResultListener;

    private static final int PROFILE_CHANGE_REQ = 1000;

    // --Commented out by Inspection (4/10/2017 21:05 PM):private Button subWtrRep;
    // --Commented out by Inspection (4/10/2017 21:05 PM):private Button viewWtrRep;

    private NavigationView navigationView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Button btnSubmitReport;
        Button btnViewReport;

        //try to get user from previous activity (Login or Register)
        mUser = (User) getIntent().getSerializableExtra("user");
        mUserManager = new UserManager();

        //add Toolbar as ActionBar with menu
        Toolbar mToolbar = (Toolbar) findViewById(R.id.main_toolbar);
        mToolbar.setTitle("Main Screen");
        this.setSupportActionBar(mToolbar);

        btnSubmitReport = (Button) findViewById(R.id.subRep);

        //clicking Submit Report takes user to SetAddressActivity.
        //from there, user will get to one of the water report submit screens.
        btnSubmitReport.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, SetAddressActivity.class);
                intent.putExtra("user", mUser);
                startActivity(intent);
/*
                String toPassIn = getToolbarTitle();
                Intent forWtrRptSubmit = new Intent(MainActivity.this, WaterRptSubmitActivity.class);
                forWtrRptSubmit.putExtra("nameRetrieval", toPassIn);
                startActivity(forWtrRptSubmit); */
            }
        });

        btnViewReport = (Button) findViewById(R.id.viewReportBut);

        btnViewReport.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
            Intent intent;
            if (!mUser.getUserPermissions().isAbleToViewPurityReports()) {
                intent = new Intent(MainActivity.this, ViewReportsActivity.class);
                intent.putExtra("user", mUser);
                intent.putExtra("reportType", "source"); //show list of source reports.
            } else {
                //ask what type of report to show
                intent = new Intent(MainActivity.this, ChooseReportActivity.class);
                intent.putExtra("user",mUser);
            }
            startActivity(intent);
            }
        });

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.main_activity_drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(this, drawer, mToolbar, 0, 0);
        drawer.setDrawerListener(toggle);
        toggle.syncState();
        navigationView = (NavigationView) findViewById(R.id.main_activity_nav_view);
        navigationView.setNavigationItemSelectedListener(this);
        navigationView.setCheckedItem(R.id.nav_main);

        /* hide or show menu options bases on user permissions
         * note: make User return the boolean itself so it doesn't violate law of demeter
         */
        if(mUser != null) {
            navigationView.getMenu().findItem(R.id.nav_hist_graph).
                    setVisible(mUser.getUserPermissions().isAbleToViewHistoricalReports());
            navigationView.getMenu().findItem(R.id.nav_water_purity).
                    setVisible(mUser.getUserPermissions().isAbleToViewPurityReports());
        }
    }

    //When returning to main screen make sure main screen is highlighted in nav view
    @Override
    protected void onStart(){
        super.onStart();
        navigationView.setCheckedItem(R.id.nav_main);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            /*case R.id.action_edit_profile:
                // User chose the "Edit Profile" action, show the user profile settings UI...
                Intent intent = new Intent(MainActivity.this, EditProfileActivity.class);
                intent.putExtra("user", mUser);
                startActivityForResult(intent, PROFILE_CHANGE_REQ);*/
            case R.id.action_settings:
                // User chose the "Settings" item, show the app settings UI...
                return true;

            default:
                // If we got here, the user's action was not recognized.
                // Invoke the superclass to handle it.
                return super.onOptionsItemSelected(item);
        }
    }

// --Commented out by Inspection START (4/10/2017 21:05 PM):
//    private void checkLoggedInStatus() {
//        //get the User object for the current logged-in user
//        //we will pass this on to the next activity
//        //call is asynchronous; result handled by mUserResultListener.onComplete()
//        mUserManager.setUserResultListener(mUserResultListener);
//        try {
//            mUserManager.getCurrentUser();
//        } catch (UserNotAuthenticatedException e) {
//            //exit this activity
//            finish();
//        }
//    }
// --Commented out by Inspection STOP (4/10/2017 21:05 PM)

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
            }
        }
    }
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
        int id = item.getItemId();

        if (id == R.id.nav_profile) {
            Intent intent = new Intent(MainActivity.this, EditProfileActivity.class);
            intent.putExtra("user", mUser);
            startActivity(intent);
        } else if (id == R.id.nav_map) {
            startActivity(new Intent(MainActivity.this, MapsMarkerActivity.class));
        } else if (id == R.id.nav_water_source) {
            Intent intent = new Intent(MainActivity.this, ViewReportsActivity.class);
            intent.putExtra("user", mUser);
            intent.putExtra("reportType", "source");
            startActivity(intent);
        } else if (id == R.id.nav_water_purity) {
            Intent intent = new Intent(MainActivity.this, ViewReportsActivity.class);
            intent.putExtra("user", mUser);
            intent.putExtra("reportType", "purity");
            startActivity(intent);
        } else if (id == R.id.nav_hist_graph) {
            startActivity(new Intent(MainActivity.this, SelectHistoricalData.class));
        } else if (id == R.id.nav_logout) {
            mUserManager.logOutUser();
            mUser = null;
            mUserManager.removeUserResultListener();

            startActivity(new Intent(MainActivity.this, LoginActivity.class));
            finish();
        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.main_activity_drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }
}
