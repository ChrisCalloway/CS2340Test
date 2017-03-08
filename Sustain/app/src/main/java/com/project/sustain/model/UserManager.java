package com.project.sustain.model;
import java.util.ArrayList;

/**
 * Created by Chris on 3/8/17.
 */

public class UserManager {

    private final List<User> users = new ArrayList<>();

    public ArrayList<User> getUsers () {

    }
    UserManagementFacade umf = UserManagementFacade.getInstance();

    FirebaseAdapter firebaseInstance  = FirebaseAdapter.getInstance();
    // Make call to FirebaseAdapter to get user
    public User getCurrentUser() {

    }

    public Boolean loginUser(String username, String password) {
        firebaseInstance.login(username,password).addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                            @Override
                            public void onComplete(@NonNull Task<AuthResult> task) {
                                // Hide progress bar
                                // If sign in fails, display a toast message to the user.  If the sign in succeeds,
                                // the auth state listener will be notified and logic to handle the signed in
                                // user can be handled in the listener.  On success, user is taken to main page
                                // of application.
                                if (!task.isSuccessful()) {
                                    if (password.length() < 6) {
                                        enteredPassword.setError("Password too short");
                                    } else {
                                        Toast.makeText(LoginActivity.this, "Login failed", Toast.LENGTH_LONG).show();
                                    }
                                } else {
                                    startActivity(new Intent(LoginActivity.this, MainActivity.class));
                                }
                            }
                        });;
    }
}

