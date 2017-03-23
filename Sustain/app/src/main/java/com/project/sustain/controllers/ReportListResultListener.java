package com.project.sustain.controllers;

import com.project.sustain.model.Report;

import java.util.EventListener;
import java.util.List;

/**
 * Created by Marcia on 3/14/2017.
 */

public interface ReportListResultListener extends EventListener {
     void onComplete(List<Report> list);
     void onError(Throwable error);
}
