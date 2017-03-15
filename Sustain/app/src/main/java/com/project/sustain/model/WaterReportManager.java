package com.project.sustain.model;

import com.project.sustain.controllers.DatabaseWrapper;
import com.project.sustain.controllers.FirebaseWrapper;
import com.project.sustain.controllers.QueryResultListener;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Chris on 3/13/17.
 */

public class WaterReportManager {
    private List<WaterReport> waterReports;
    private DatabaseWrapper mDBWrapper = new FirebaseWrapper();
    private ReportListResultListener mListResultListener = null;

    /**
     * Constructor
     */
    public WaterReportManager() {
        waterReports = new ArrayList<>();
    }



    /**
     * Asks database for a list of water reports.
     */
    public void getWaterReports() {
        QueryResultListener qrListener = new QueryResultListener() {
            @Override
            public <T> void onComplete(List<T> list) {
                mListResultListener.onComplete(list);
            }

            @Override
            public void onError(Throwable error) {

            }
        };
        mDBWrapper.setQueryResultListener(qrListener);
        mDBWrapper.queryDatabaseAsync("waterReports", new WaterReport());

    }


    public void setReportListResultListener(ReportListResultListener listener) {
        mListResultListener = listener;
    }

    public void removeReportListResultListener() {
        mListResultListener = null;
    }
}
