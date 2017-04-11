package com.project.sustain;

import com.project.sustain.controllers.AuthResultListener;
import com.project.sustain.controllers.FirebaseWrapper;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import static junit.framework.Assert.assertEquals;

/**
 * Created by Marcia on 4/10/2017.
 */
public class FirebaseWrapperTest {
    FirebaseWrapper mFirebaseWrapper;
    AuthResultListener listener;
    boolean ready = false;

    @Before
    public void setUp() throws Exception {
        mFirebaseWrapper = new FirebaseWrapper();
        ready = false;
        listener = new AuthResultListener() {
            @Override
            public void onComplete(boolean success) {
                ready = true;
            }

            @Override
            public void onError(Throwable error) {

            }
        };
        mFirebaseWrapper.setAuthResultListener(listener);

    }

    @After
    public void tearDown() throws Exception {
        ready = false;
        mFirebaseWrapper.logOut();
        mFirebaseWrapper.removeAuthResultListener();
        mFirebaseWrapper = null;
    }

    @Test
    public void testGetCurrentUserValid() throws Exception {
        mFirebaseWrapper.loginWithEmail("testmanager@user.com", "Sustain");
        while (!ready) {
            Thread.sleep(1000);
        }
        String userId = mFirebaseWrapper.getCurrentUserId();
        //assert userId is correct value
        assertEquals("M04uLja9QPSz7IVhYw5yyv3AgLb2", userId);
        //assert user email is correct value
        assertEquals("testmanager@user.com", mFirebaseWrapper.getCurrentUserEmail());
        //assert isLoggedIn == true
        assertEquals(true, mFirebaseWrapper.isLoggedIn());

    }

    @Test
    public void testGetCurrentUserNotValid() throws Exception {
        ready = false;
        mFirebaseWrapper.loginWithEmail("notauser", "notapassword");
        while (!ready) {
            Thread.sleep(1000);
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