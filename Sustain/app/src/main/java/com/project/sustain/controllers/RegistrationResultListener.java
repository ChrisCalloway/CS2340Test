package com.project.sustain.controllers;

import java.util.EventListener;

/**
 * Created by Marcia on 3/14/2017.
 */

public interface RegistrationResultListener extends EventListener {
    void onComplete(String item);
    void onError(Throwable error);
}
