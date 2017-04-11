package com.project.sustain.model;

/**
 * Model class that sets permissions for worker.
 * @author Marcia
 */

class WorkerPermissions extends UserPermissions {

    public WorkerPermissions() {
        super(true, false, false, false, false);
    }
}
