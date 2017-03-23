package com.project.sustain.controllers;

import java.util.EventListener;
import java.util.List;

/**
 * For receiving a list of results (such as WaterSourceReports)
 * from an ansynchronous database query.
 * Created by Marcia on 3/14/2017.
 */

public interface QueryListResultListener extends EventListener {
     <T> void onComplete(List<T> result);
     void onError(Throwable error);
}
