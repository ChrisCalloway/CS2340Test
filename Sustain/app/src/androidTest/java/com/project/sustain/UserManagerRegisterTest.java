package com.project.sustain;

import com.project.sustain.controllers.AuthResultListener;
import com.project.sustain.controllers.FirebaseWrapper;
import com.project.sustain.controllers.RegistrationResultListener;
import com.project.sustain.model.UserManager;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import static junit.framework.Assert.assertEquals;


/**
 * Does JUnit for logInUserEmailPassword
 * @author Chris
 */

public class UserManagerRegisterTest {
    private FirebaseWrapper mFirebaseWrapper;
    private UserManager mUserManager;
    private boolean ready = false;

    @Before
    public void setUp() throws Exception {
        mFirebaseWrapper = new FirebaseWrapper();
        mUserManager = new UserManager();
        ready = false;
        AuthResultListener listener = new AuthResultListener() {
            @Override
            public void onComplete(boolean success) {
                ready = true;
            }

            @Override
            public void onError(Throwable error) {

            }
        };
        RegistrationResultListener registrationResultListener = new RegistrationResultListener() {
            @Override
            public void onComplete(boolean success, String result) {
                ready = true;
            }

            @Override
            public void onError(Throwable error) {

            }
        };
        mUserManager.setRegistrationResultListener(registrationResultListener);
        mFirebaseWrapper.setAuthResultListener(listener);
    }

    @After
    public void tearDown() throws Exception {
        ready = false;
        mUserManager.removeRegistrationResultListener();
        mUserManager = null;

         mFirebaseWrapper.logOut();
        // mFirebaseWrapper.removeRegistrationResultListener();
         mFirebaseWrapper.removeAuthResultListener();
         mFirebaseWrapper = null;
    }

    @Test
    public void testRegisterWithEmailPasswordValid() throws Exception {
        // Need to login in order to be able to call get current user


        // Need to give proper inputs, email not yet used.
        String email = "chris_register_test@email.com";
        String password = "password";
//        mFirebaseWrapper.createAccountWithEmailPassword(email, password);
        mUserManager.registerWithEmailPassword(email, password);
        while (!ready) {
            Thread.sleep(5000);
        }


        // String userId = mFirebaseWrapper.getCurrentUserId();
        //assert userId is correct value
        // assertEquals("M04uLja9QPSz7IVhYw5yyv3AgLb2", userId);
        //assert user email is correct value
        assertEquals("chris_register_test@email.com", mFirebaseWrapper.getCurrentUserEmail());
        //assert isLoggedIn == true
        assertEquals(true, mFirebaseWrapper.isLoggedIn());

    }

    @Test
    public void testRegisterWithEmailPasswordNotValid() throws Exception {
        ready = false;
        String email = "chris_user@email.com";
        String password = "notcorrectpassword";
//        mFirebaseWrapper.createAccountWithEmailPassword(email, password);
        mUserManager.registerWithEmailPassword(email, password);
        while (!ready) {
            Thread.sleep(5000);
        }

        String userId = mFirebaseWrapper.getCurrentUserId();
        //assert userId is empty string
        assertEquals("", userId);
        //assert user email is empty string
        assertEquals("", mFirebaseWrapper.getCurrentUserEmail());
        //assert isLoggedIn == false
        assertEquals(false, mFirebaseWrapper.isLoggedIn());
    }

}
