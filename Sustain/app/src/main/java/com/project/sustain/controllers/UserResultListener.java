package com.project.sustain.controllers;

import com.project.sustain.model.User;

import java.util.EventListener;

/**
 * Created by Marcia on 3/22/2017.
 */

public interface UserResultListener extends EventListener {
    void onComplete(User user);
    void onError(Throwable error);
}
