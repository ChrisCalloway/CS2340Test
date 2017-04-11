package com.project.sustain.controllers;

import java.util.EventListener;

/**
 * Listener for when new user registers with the application.
 * @author Marcia
 */

public interface RegistrationResultListener extends EventListener {
    void onComplete(boolean success, String result);
    void onError(Throwable error);

}
