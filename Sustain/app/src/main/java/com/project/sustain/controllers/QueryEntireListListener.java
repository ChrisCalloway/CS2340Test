package com.project.sustain.controllers;
import java.util.EventListener;
import java.util.List;

/**
 * Listener for a list of entries in database.
 * @author Chris
 */

public interface QueryEntireListListener extends EventListener {
    <T> void onComplete(List<T> result);
    void onError(Throwable error);
}
