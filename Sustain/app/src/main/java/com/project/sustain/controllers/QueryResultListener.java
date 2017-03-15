package com.project.sustain.controllers;

import java.util.EventListener;
import java.util.List;

/**
 * Created by Marcia on 3/14/2017.
 */

public interface QueryResultListener extends EventListener {
    public <T> void onComplete(List<T> result);
    public void onError(Throwable error);
}
