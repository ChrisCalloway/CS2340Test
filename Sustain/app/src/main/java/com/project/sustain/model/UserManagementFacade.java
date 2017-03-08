package com.project.sustain.model;

/**
 * Created by Chris on 3/8/17.
 */

public class UserManagementFacade {

    /**
     * Singleton pattern
     */
    private static UserManagementFacade instance = new UserManagementFacade();

    /**
     * the facade maintains references to any required model classes
     */
    private UserManager userManager;


    /**
     * private constructor for facade pattern
     */
    private UserManagementFacade() {
        userManager = new UserManager();
    }

    /**
     * Singleton pattern accessor for instance
     *
     *
     * @return the one and only one instance of this facade
     */
    public static UserManagementFacade getInstance() { return instance; }

    public Boolean loginUser(String username, String password) {
        // Make call to userManager's loginUser
        return userManager.loginUser(username, password);
    }

    public List<Student> getStudentsAsList() {
        return sm.getStudents();
    }

}
