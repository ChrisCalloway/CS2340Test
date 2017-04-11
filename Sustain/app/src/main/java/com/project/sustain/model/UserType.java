package com.project.sustain.model;

/**
 * Created by Marcia on 2/27/2017.
 */

public enum UserType {
    USER("User"),
    WORKER("Worker"),
    MANAGER("Manager"),
    ADMIN("Administrator");

    private final String description;
    UserType(String userDescription) {
        this.description = userDescription;
    }

    @Override
    public String toString() {
        return this.description;
    }
}
