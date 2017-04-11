package com.project.sustain.model;

import com.project.sustain.controllers.DatabaseWrapper;
import com.project.sustain.controllers.FirebaseWrapper;
import com.project.sustain.controllers.QueryEntireListListener;
import com.project.sustain.controllers.QueryListResultListener;

import java.io.Serializable;
import java.util.List;

/**
 * Created by Chris on 3/13/17.
 */

public class WaterReportManager implements Serializable{
    // --Commented out by Inspection (4/10/2017 21:16 PM):private List<Report> mReports;
    private final DatabaseWrapper mDBWrapper = new FirebaseWrapper();
    private QueryListResultListener mListResultListener = null;
    private final QueryListResultListener qrListener;
    private QueryEntireListListener mEntireListResultListener = null;
    private final QueryEntireListListener qeListener;
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

        //this listener passes the query results back
        //to the caller when the asynchronous query finishes.
        qeListener = new QueryEntireListListener() {
            @Override
            public <T> void onComplete(List<T> list) {
                if (mEntireListResultListener != null) {
                    mEntireListResultListener.onComplete(list);
                }
            }

            @Override
            public void onError(Throwable error) {
                if (mEntireListResultListener != null) {
                    mEntireListResultListener.onError(error);
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
     * Queries database for an entire list of water purity reports.
     */
    public void getEntireWaterPurityReportList() {
        mDBWrapper.setQueryEntireListListener(qeListener);
        mDBWrapper.queryDatabaseForEntireListAsync("purityReports", new WaterPurityReport());
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


    public void setQueryEntireListListener(QueryEntireListListener listener) {
        mEntireListResultListener = listener;
    }

// --Commented out by Inspection START (4/10/2017 21:16 PM):
//    public void removeQueryEntireListListener() {
//        mEntireListResultListener = null;
//    }
// --Commented out by Inspection STOP (4/10/2017 21:16 PM)
}
