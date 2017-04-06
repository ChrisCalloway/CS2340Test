package com.project.sustain.controllers;

import java.util.EventListener;

/**
 * Created by Marcia on 3/14/2017.
 */

public interface AuthResultListener extends EventListener {
    void onComplete(boolean success);
    void onError(Throwable error);
}
