package com.project.sustain.model;

import com.project.sustain.controllers.AuthResultListener;
import com.project.sustain.controllers.DatabaseWrapper;
import com.project.sustain.controllers.FirebaseWrapper;
import com.project.sustain.controllers.LoginResultListener;
import com.project.sustain.controllers.QuerySingleResultListener;
import com.project.sustain.controllers.UserResultListener;

/**
 * Created by Marcia on 3/22/2017.
 */

public class UserManager {
    private User currentUser;
    private DatabaseWrapper DBWrapper = new FirebaseWrapper();
    private UserResultListener mUserResultListener = null;
    private AuthResultListener mAuthResultListener = null;
    private LoginResultListener mLoginResultListener = null;

    public String getCurrentUserId() {
        return DBWrapper.getCurrentUserId();
    }

    public String getCurrentUserDisplayName() {
        return DBWrapper.getCurrentUserDisplayName();
    }

    public void updateCurrentUserDisplayName(String newName) {
        DBWrapper.updateCurrentUserDisplayName(newName);
    }

    public String getCurrentUserEmail() {
        return DBWrapper.getCurrentUserEmail();
    }

    public void getCurrentUser() {
        String userId = DBWrapper.getCurrentUserId();
        if (userId.length() > 0) {
            DBWrapper.setQuerySingleResultListener(new QuerySingleResultListener() {
                @Override
                public <T> void onComplete(T result) {
                    currentUser = (User) result;
                    mUserResultListener.onComplete(currentUser);
                }

                @Override
                public void onError(Throwable error) {
                    mUserResultListener.onError(error);
                }
            });

            DBWrapper.queryDatabaseForSingleAsync("/userProfiles/" + userId, new User());
        }
    }

    public void addUser(User user) {
        this.updateUser(user);
    }

    public void updateUser(User user) {
        DBWrapper.updateSingleRecord("/userProfiles/" + user.getUserId(), user);
    }

    public void logInUserEmailPassword(String email, String password) {
        mAuthResultListener = new AuthResultListener() {
            @Override
            public void onComplete(boolean success) {
                mLoginResultListener.onComplete(success);
            }

            @Override
            public void onError(Throwable error) {
                mLoginResultListener.onError(error);
            }
        };
        DBWrapper.setAuthResultListener(mAuthResultListener);
        DBWrapper.loginWithEmail(email, password);
    }

    public void logOutUser() {
        DBWrapper.logOut();
    }


    public void setUserResultListener(UserResultListener listener) {
        this.mUserResultListener = listener;
    }

    public void removeUserResultListener() {
        this.mUserResultListener = null;
    }

    public void setLoginResultListener(LoginResultListener listener) {
        this.mLoginResultListener = listener;
    }

    public void removeLoginResultListener() { this.mLoginResultListener = null; }
}
