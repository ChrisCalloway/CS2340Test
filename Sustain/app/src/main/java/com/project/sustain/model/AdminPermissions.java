package com.project.sustain.model;

/**
 * Sets permissions for Admin user.
 * @author Marcia
 */

class AdminPermissions extends UserPermissions {

    public AdminPermissions() {
        super(true, true, true, true, true);
    }
}

