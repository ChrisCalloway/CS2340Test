package com.project.sustain.model;

/**
 * Sets application permissions for Manager type user.
 * @author Marcia
 */

class ManagerPermissions extends UserPermissions {

    public ManagerPermissions() {
        super(true, true, true, false, false);
    }
}
