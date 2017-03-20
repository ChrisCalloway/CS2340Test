package com.project.sustain.model;

import java.util.EventListener;
import java.util.List;

/**
 * Created by Marcia on 3/14/2017.
 */

public interface ReportListResultListener extends EventListener {
     <T> void onComplete(List<T> list);
     void onError(Throwable error);
}
