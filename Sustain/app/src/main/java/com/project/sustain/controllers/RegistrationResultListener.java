package com.project.sustain.controllers;

import java.util.EventListener;

/**
 * Created by Marcia on 3/22/2017.
 */

public interface RegistrationResultListener extends EventListener {
    void onComplete(boolean success);
    void onError(Throwable error);

}
