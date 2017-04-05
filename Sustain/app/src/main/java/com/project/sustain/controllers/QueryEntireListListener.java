package com.project.sustain.controllers;
import java.util.EventListener;
import java.util.List;

/**
 * Created by Chris on 4/3/17.
 */

public interface QueryEntireListListener extends EventListener {
    <T> void onComplete(List<T> result);
    void onError(Throwable error);
}
