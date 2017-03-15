package com.project.sustain.controllers;

/**
 * Created by Marcia on 3/14/2017.
 */

public interface DatabaseWrapper {
    public void connect();

    public void disconnect();

    public void setRegistrationResultListener(RegistrationResultListener listener);

    public void removeRegistrationResultListener();

    public void createAccount(String email, String password);

    public <T> void queryDatabaseAsync(String query, T modelObject);

    public <T> void saveSingleRecord(String recordLocation, T modelObject);

    public void setQueryResultListener(QueryResultListener listener);

    public void removeQueryResultListener();
}
