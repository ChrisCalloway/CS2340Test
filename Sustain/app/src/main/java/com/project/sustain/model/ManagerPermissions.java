package com.project.sustain.model;

/**
 * Sets application permissions for Manager type user.
 * @author Marcia
 */

public class ManagerPermissions extends UserPermissions {

    public ManagerPermissions() {
        super(true, true, true, false, false);
    }
}
