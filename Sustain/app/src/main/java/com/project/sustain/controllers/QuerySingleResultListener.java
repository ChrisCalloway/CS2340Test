package com.project.sustain.controllers;

import java.util.EventListener;

/**
 * For receiving a single result (such as a User object)
 * from an asynchronous database query.
 * Created by Marcia on 3/22/2017.
 */

@SuppressWarnings("JavaDoc")
public interface QuerySingleResultListener extends EventListener {
    <T> void onComplete(T result);
    void onError(Throwable error);
}
