package com.project.sustain.controllers;

/**
 * Created by Marcia on 3/14/2017.
 */

public interface DatabaseWrapper {
     void connect();

     void disconnect();

     void setRegistrationResultListener(RegistrationResultListener listener);

     void removeRegistrationResultListener();

     void createAccount(String email, String password);

     <T> void queryDatabaseAsync(String query, T modelObject);

     <T> void saveSingleRecord(String recordLocation, T modelObject);

     void setQueryResultListener(QueryResultListener listener);

     void removeQueryResultListener();
}
