package com.project.sustain.model;

import java.io.Serializable;

/**
 * Created by Marcia on 3/26/2017.
 */

public class UserPermissions implements Serializable {

    private final boolean ableToCreatePurityReports;
    private final boolean ableToViewPurityReports;
    private final boolean ableToViewHistoricalReports;
    private final boolean ableToDeleteReports;
    private final boolean ableToAdministerUsers;

    public UserPermissions() {
        this(false, false, false, false, false);
     }

    UserPermissions(boolean ableToCreatePurityReports,
                    boolean ableToViewPurityReports,
                    boolean ableToViewHistoricalReports,
                    boolean ableToDeleteReports,
                    boolean ableToAdministerUsers) {
        this.ableToCreatePurityReports = ableToCreatePurityReports;
        this.ableToViewPurityReports = ableToViewPurityReports;
        this.ableToViewHistoricalReports = ableToViewHistoricalReports;
        this.ableToDeleteReports = ableToDeleteReports;
        this.ableToAdministerUsers = ableToAdministerUsers;
    }
    
    public boolean isAbleToCreatePurityReports() {
        return this.ableToCreatePurityReports;
    }

    public boolean isAbleToViewHistoricalReports() {
        return this.ableToViewHistoricalReports;
    }

    public boolean isAbleToViewPurityReports() {
        return this.ableToViewPurityReports;
    }

// --Commented out by Inspection START (4/10/2017 21:15 PM):
//    public boolean isAbleToDeleteReports() {
//        return this.ableToDeleteReports;
//    }
// --Commented out by Inspection STOP (4/10/2017 21:15 PM)

// --Commented out by Inspection START (4/10/2017 21:15 PM):
//    public boolean isAbleToAdministerUsers() {
//        return this.ableToAdministerUsers;
//    }
// --Commented out by Inspection STOP (4/10/2017 21:15 PM)
}

