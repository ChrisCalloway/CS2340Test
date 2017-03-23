package com.project.sustain.model;

import com.project.sustain.controllers.DatabaseWrapper;
import com.project.sustain.controllers.FirebaseWrapper;
import com.project.sustain.controllers.QueryListResultListener;
import com.project.sustain.controllers.ReportListResultListener;

import java.util.List;

/**
 * Created by Chris on 3/13/17.
 */

public class WaterReportManager {
    private List<Report> mReports;
    private DatabaseWrapper mDBWrapper = new FirebaseWrapper();
    private ReportListResultListener mListResultListener = null;
    private QueryListResultListener qrListener;
    /**
     * Constructor
     */
    public WaterReportManager() {

        qrListener = new QueryListResultListener() {
            @Override
            public <T> void onComplete(List<T> list) {
                mReports = (List<Report>) list;
                mListResultListener.onComplete(mReports);
            }

            @Override
            public void onError(Throwable error) {

            }
        };
    }

    /**
     * Asks database for a list of water source reports.
     */
    public void getWaterSourceReports() {
        mDBWrapper.setQueryListResultListener(qrListener);
        mDBWrapper.queryDatabaseForListAsync("waterReports", new WaterSourceReport());

    }

    /**
     * Asks database for a list of water purity reports.
     */
    public void getWaterPurityReports() {
        mDBWrapper.setQueryListResultListener(qrListener);
        mDBWrapper.queryDatabaseForListAsync("purityReports", new WaterPurityReport());
    }


    public void setReportListResultListener(ReportListResultListener listener) {
        mListResultListener = listener;
    }

    public void removeReportListResultListener() {
        mListResultListener = null;
    }
}
