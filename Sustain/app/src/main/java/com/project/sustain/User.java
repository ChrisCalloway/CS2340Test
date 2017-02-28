package com.project.sustain;
import com.google.firebase.database.IgnoreExtraProperties;

/**
 * Created by Chris on 2/28/17.
 */

public class User {
    public String username;
    public String email;
    public String usertype;

    // Default Constructor required for calls to DataSnapshot.getValue(User.class)
    public User() {
    }

    public User(String username, String email) {
        this.username = username;
        this.email = email;
    }
}

