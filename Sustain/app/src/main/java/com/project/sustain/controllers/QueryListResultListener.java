package com.project.sustain.controllers;

import java.util.EventListener;

/**
 * For receiving a list of results (such as WaterSourceReports)
 * from an asynchronous database query.
 * Created by Marcia on 3/14/2017.
 */

@SuppressWarnings("JavaDoc")
public interface QueryListResultListener extends EventListener {
     <T, K> void onComplete(T item, K key);
     void onError(Throwable error);
}
