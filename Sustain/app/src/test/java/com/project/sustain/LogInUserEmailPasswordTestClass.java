package com.project.sustain;

import com.project.sustain.model.UserManager;
import org.junit.Test;
import static org.junit.Assert.*;

/**
 * Created by georgiainstituteoftechnology on 4/5/17.
 */

public class LogInUserEmailPasswordTestClass {
    @Test (expected = Throwable.class)
    public void testLogInUserEmailPassword() {
        UserManager testUserManager = new UserManager();
        try {
            testUserManager.logInUserEmailPassword("aamin33@gatech.edu", "cs2340");
        }
        catch (Throwable thrownException){
            fail("Invalid Login. Boohoo");
        }
        testUserManager.logInUserEmailPassword("aamin33@gatech.edu", "UGA Sucks");
    }
}
