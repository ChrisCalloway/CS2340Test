package com.project.sustain.model;

/**
 * Created by Marcia on 3/26/2017.
 */

public class ManagerPermissions extends UserPermissions {

    public ManagerPermissions() {
        super(true, true, true, false, false);
    }
}
