package com.project.sustain.model;
import java.util.ArrayList;

import com.google.firebase.auth.FirebaseAuth;
import com.project.sustain.model.UserManagementFacade;


/**
 * Created by Chris on 3/8/17.
 */

public class UserManager {

//    private final List<User> users = new ArrayList<>();

//    public ArrayList<User> getUsers () {
//
//    }
    UserManagementFacade umf = UserManagementFacade.getInstance();

    FirebaseAdapter firebaseInstance  = FirebaseAdapter.getInstance();

    FirebaseAuth authUser = FirebaseAdapter.getAuthUser();
    // Make call to FirebaseAdapter to get user
    public FirebaseAuth getCurrentAuthUser() {

    }

}

