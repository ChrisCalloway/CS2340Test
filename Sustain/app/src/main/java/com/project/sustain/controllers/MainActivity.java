// Clean slate version


package com.project.sustain.controllers;

import android.content.Intent;
import android.os.Bundle;
import android.security.keystore.UserNotAuthenticatedException;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBar;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.project.sustain.R;
import com.project.sustain.model.User;
import com.project.sustain.model.UserManager;


public class MainActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener {
    private Toolbar mToolbar;
    private User mUser;
    private UserManager mUserManager;
    private UserResultListener mUserResultListener;
    public static final int PROFILE_CHANGE_REQ = 1000;

    private Button subWtrRep;
    private Button viewWtrRep;

    private NavigationView navigationView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Button btnSubmitReport;
        Button btnViewReport;
        Button btnViewMap;
        Button btnLogout;
        Toolbar toolbar;
        Button viewHistGraph;

        //try to get user from previous activity (Login or Register)
        mUser = (User) getIntent().getSerializableExtra("user");
        mUserManager = new UserManager();

        setContentView(R.layout.activity_main);

        //add Toolbar as ActionBar with menu
        mToolbar = (Toolbar) findViewById(R.id.main_toolbar);
        mToolbar.setTitle("Main Screen");
        this.setSupportActionBar(mToolbar);
        toolbar = (Toolbar) findViewById(R.id.main_toolbar);
        this.setSupportActionBar(toolbar);
        String userName = "";
        String userEMail = "";
        if (mUser == null) {
            userName = mUserManager.getCurrentUserDisplayName();
            userEMail = mUserManager.getCurrentUserEmail();
        } else {
            userName = mUser.getUserName();
            userEMail = mUser.getEmailAddress();
        }
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
            Intent intent;
            if (!mUser.getUserPermissions().isAbleToViewPurityReports()) {
                intent = new Intent(MainActivity.this, ViewReportsActivity.class);
                intent.putExtra("reportType", "source"); //show list of source reports.
            } else {
                //ask what type of report to show
                intent = new Intent(MainActivity.this, ChooseReportActivity.class);
            }
            startActivity(intent);
            }
        });

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.main_activity_drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(this, drawer, mToolbar, 0, 0);
        drawer.setDrawerListener(toggle);
        toggle.syncState();
        btnViewMap = (Button) findViewById(R.id.buttonViewMap);

        btnViewMap.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(MainActivity.this, MapsMarkerActivity.class));

            }
        });

        viewHistGraph = (Button) findViewById(R.id.hist_graph);
        if (mUser != null) {
            if (mUser.getUserPermissions().isAbleToViewHistoricalReports()) {
                viewHistGraph.setVisibility(View.VISIBLE);
            } else {
                viewHistGraph.setVisibility(View.GONE);
            }
        }

        viewHistGraph.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(MainActivity.this, SelectHistoricalData.class));
            }
        });
        navigationView = (NavigationView) findViewById(R.id.main_activity_nav_view);
        navigationView.setNavigationItemSelectedListener(this);
        navigationView.setCheckedItem(R.id.nav_main);
    }

    //When returning to main screen make sure main screen is highlighted in nav view
    @Override
    protected void onStart(){
        super.onStart();
        navigationView.setCheckedItem(R.id.nav_main);
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

    private void checkLoggedInStatus() {
        //get the User object for the current logged-in user
        //we will pass this on to the next activity
        //call is asynchronous; result handled by mUserResultListener.onComplete()
        mUserManager.setUserResultListener(mUserResultListener);
        try {
            mUserManager.getCurrentUser();
        } catch (UserNotAuthenticatedException e) {
            //exit this activity
            finish();
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
    public boolean onNavigationItemSelected(MenuItem item) {
        int id = item.getItemId();

        if (id == R.id.nav_profile) {
            startActivity(new Intent(MainActivity.this, EditProfileActivity.class));
        } else if (id == R.id.nav_map) {
            startActivity(new Intent(MainActivity.this, MapsMarkerActivity.class));
        } else if (id == R.id.nav_water_source) {

        } else if (id == R.id.nav_water_quality) {

        } else if (id == R.id.nav_logout) {
            auth.signOut();
            startActivity(new Intent(MainActivity.this, LoginActivity.class));
            finish();
        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.main_activity_drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }
}
