package com.project.sustain.model;

/**
 * Enum for the possible user types in the application.
 * @author Marcia
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
