package com.project.sustain.model;

import com.project.sustain.controllers.DatabaseWrapper;
import com.project.sustain.controllers.FirebaseWrapper;
import com.project.sustain.controllers.QueryListResultListener;

import java.util.List;

/**
 * Created by Chris on 3/13/17.
 */

public class WaterReportManager {
    private List<Report> mReports;
    private DatabaseWrapper mDBWrapper = new FirebaseWrapper();
    private QueryListResultListener mListResultListener = null;
    private QueryListResultListener qrListener;
    /**
     * Constructor
     */
    public WaterReportManager() {

        //this listener passes the query results back
        //to the caller when the asynchronous query finishes.
        qrListener = new QueryListResultListener() {
            @Override
            public <T, K> void onComplete(T item, K key) {
                if (mListResultListener != null) {
                    mListResultListener.onComplete(item, key);
                }
            }

            @Override
            public void onError(Throwable error) {
                if (mListResultListener != null) {
                    mListResultListener.onError(error);
                }
            }
        };
    }

    /**
     * Asks database for a list of water source reports.
     */
    public void getWaterSourceReports() {
        mDBWrapper.setQueryListResultListener(qrListener);
        mDBWrapper.queryDatabaseForListAsync("sourceReports", new WaterSourceReport());

    }

    /**
     * Asks database for a list of water purity reports.
     */
    public void getWaterPurityReports() {
        mDBWrapper.setQueryListResultListener(qrListener);
        mDBWrapper.queryDatabaseForListAsync("purityReports", new WaterPurityReport());
    }

    /**
     * Saves a single WaterSourceReport to database.
     * @param report the WaterSourceReport to save.
     */
    public void saveSourceReport(WaterSourceReport report) {
        mDBWrapper.insertSingleRecord("sourceReports", report);
    }

    /**
     * Saves a single WaterPurityReport to database.
     * @param report the WaterPurityReport to save.
     */
    public void savePurityReport(WaterPurityReport report) {
        mDBWrapper.insertSingleRecord("purityReports", report);
    }


    public void setQueryListResultListener(QueryListResultListener listener) {
        mListResultListener = listener;
    }

    public void removeQueryListResultListener() {
        mListResultListener = null;
    }
}
