package com.project.sustain.model;

import java.io.Serializable;

/**
 * Parent class that sets permissions for basic user in application.
 * @author Marcia
 */

public class UserPermissions implements Serializable {

    private boolean ableToCreatePurityReports;
    private boolean ableToViewPurityReports;
    private boolean ableToViewHistoricalReports;
    private boolean ableToDeleteReports;
    private boolean ableToAdministerUsers;

    public UserPermissions() {
        this(false, false, false, false, false);
     }

    protected UserPermissions(boolean ableToCreatePurityReports,
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

    public boolean isAbleToDeleteReports() {
        return this.ableToDeleteReports;
    }

    public boolean isAbleToAdministerUsers() {
        return this.ableToAdministerUsers;
    }
}

