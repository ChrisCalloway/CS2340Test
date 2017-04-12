package com.project.sustain;

import android.content.Intent;
import android.security.keystore.UserNotAuthenticatedException;

import com.project.sustain.controllers.AuthResultListener;
import com.project.sustain.controllers.FirebaseWrapper;
import com.project.sustain.controllers.LoginActivity;
import com.project.sustain.controllers.LoginResultListener;
import com.project.sustain.controllers.MainActivity;
import com.project.sustain.controllers.QuerySingleResultListener;
import com.project.sustain.controllers.RegistrationResultListener;
import com.project.sustain.controllers.UserResultListener;
import com.project.sustain.model.Address;
import com.project.sustain.model.User;
import com.project.sustain.model.UserManager;
import com.project.sustain.model.UserType;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import java.security.KeyStore;

import static junit.framework.Assert.assertEquals;
import static junit.framework.Assert.assertFalse;
import static junit.framework.Assert.assertNotNull;
import static junit.framework.Assert.assertTrue;

/**
 * Does JUnit for getCurrentUser
 * @author derek
 */

public class UserManagerGetCurrentUserTest {
    private User mUser;
    private UserManager mUserManager;
    private FirebaseWrapper mFirebaseWrapper;
    private boolean ready = false;

    @Before
    public void setUp() throws Exception {
        mUserManager = new UserManager();
        mFirebaseWrapper = new FirebaseWrapper();
        ready = false;
        AuthResultListener authResultListener = new AuthResultListener() {
            @Override
            public void onComplete(boolean success) {
            }

            @Override
            public void onError(Throwable error) {

            }
        };
        LoginResultListener loginResultListener = new LoginResultListener() {
            @Override
            public void onComplete(boolean success) {
                ready = true;
            }

            @Override
            public void onError(Throwable error) {

            }
        };
        mUserManager.setLoginResultListener(loginResultListener);
        mFirebaseWrapper.setAuthResultListener(authResultListener);
    }

    @After
    public void tearDown() throws Exception {
        ready = false;
        mUserManager.removeLoginResultListener();
        mFirebaseWrapper.removeAuthResultListener();
        mFirebaseWrapper.logOut();
        mUserManager = null;
        mFirebaseWrapper = null;
    }

    @Test
    public void testGetCurrentUserValid() throws Exception {
        mUserManager.logInUserEmailPassword("derekwlr@gmail.com","cs2340");
        while (!ready) {
            Thread.sleep(5000);
        }
        mUserManager.getCurrentUser();
        assertEquals("Display Names are the same",
                "Bob", mFirebaseWrapper.getCurrentUserDisplayName());
        assertEquals("Id's are the same",
                "iRWdEZ9wJOhgA2TIdonimckz5FG3", mFirebaseWrapper.getCurrentUserId());
        assertEquals("Emails are the same",
                "derekwlr@gmail.com", mFirebaseWrapper.getCurrentUserEmail());
    }

    @Test
    public void testGetCurrentUserNotValid() throws Exception {
        mUserManager.logInUserEmailPassword("derekwlr@gmail.com","cs2340");
        while (!ready) {
            Thread.sleep(5000);
        }
        mUserManager.getCurrentUser();
        assertFalse("User Display Name not the same",
                "Who".equals(mUserManager.getCurrentUserDisplayName()));
        assertFalse("User Id not the same",
                "n21rdfnofn1".equals(mUserManager.getCurrentUserDisplayName()));
        assertFalse("User email not the same",
                "fake@email.com".equals(mUserManager.getCurrentUserDisplayName()));
    }

    @Test
    public void testGetCurrentUserDoesntExist() throws Exception {
        Exception exception = null;
        try {
            mUserManager.getCurrentUser();
            while (!ready) {
                Thread.sleep(5000);
            }
        } catch (Exception e) {
            exception = e;
        }
        assertNotNull(exception);
    }
}
