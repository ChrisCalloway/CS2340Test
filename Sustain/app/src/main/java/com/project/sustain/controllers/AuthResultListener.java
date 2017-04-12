package com.project.sustain.controllers;

import java.util.EventListener;

/**
 * Listener for result of getting Auth object from Firebase.
 * @author Marcia
 */

@SuppressWarnings("JavaDoc")
public interface AuthResultListener extends EventListener {
    void onComplete(boolean success);
    @SuppressWarnings("unused")
    void onError(Throwable error);
}
