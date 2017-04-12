package com.project.sustain.controllers;

import java.util.EventListener;

/**
 * Listener called for result of login.
 * @author Marcia
 */

@SuppressWarnings("JavaDoc")
public interface LoginResultListener extends EventListener {
    void onComplete(boolean success);
    void onError(Throwable error);

}
