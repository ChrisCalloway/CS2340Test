package com.project.sustain.controllers;

import com.project.sustain.model.User;

import java.util.EventListener;

/**
 * Listener for getting a user from Firebase Authentication.
 * @author Marcia
 */

@SuppressWarnings("EmptyMethod")
public interface UserResultListener extends EventListener {
    void onComplete(User user);
    void onError(Throwable error);
}
