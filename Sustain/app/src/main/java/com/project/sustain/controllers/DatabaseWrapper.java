package com.project.sustain.controllers;

/**
 * Created by Marcia on 3/14/2017.
 */

public interface DatabaseWrapper {
     void connect();

     void disconnect();

     String getCurrentUserId();

     String getCurrentUserDisplayName();

     void updateCurrentUserDisplayName(String newName);

     String getCurrentUserEmail();

     void setAuthResultListener(AuthResultListener listener);

     void removeAuthResultListener();

     void createAccountWithEmailPassword(String email, String password);

     void loginWithEmail(String email, String password);

     void logOut();

     <T> void queryDatabaseForListAsync(String query, T modelObject);

     <T> void queryDatabaseForSingleAsync(String query, T modelObject);

     <T> void insertSingleRecord(String recordLocation, T modelObject);

     <T> void updateSingleRecord(String recordLocation, T modelObject);

     void setQueryListResultListener(QueryListResultListener listener);

     void removeQueryListResultListener();

     void setQuerySingleResultListener(QuerySingleResultListener listener);

     void removeQuerySingleResultListener();

     void setRegistrationResultListener(RegistrationResultListener listener);

     void removeRegistrationResultListener();
}
