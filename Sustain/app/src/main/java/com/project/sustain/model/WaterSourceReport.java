package com.project.sustain.model;

import com.google.firebase.database.IgnoreExtraProperties;

import java.io.Serializable;

/**
 * Created by georgiainstituteoftechnology on 3/2/17.
 */
@IgnoreExtraProperties
public class WaterSourceReport extends Report implements Serializable {
    private WaterType mWaterType;
    private WaterCondition mWaterCondition;

    // Constructor
    public WaterSourceReport() {
    }

    public WaterSourceReport(Address address, String reporterName, String reportUserId,
                             String dateOfReport, String timeOfReport, int reportNumber,
                             String placeName, WaterType waterType, WaterCondition waterCondition) {
        super(address, reporterName, reportUserId, dateOfReport, timeOfReport, reportNumber,
                placeName);
        this.mWaterType = waterType;
        this.mWaterCondition = waterCondition;
    }

    public WaterSourceReport(Address address) {
        this.setAddress(address);
    }

    public WaterType getWaterType() {
        return mWaterType;
    }

    public void setWaterType(WaterType typePassed) {
        mWaterType = typePassed;
    }

    public WaterCondition getWaterCondition() {
        return mWaterCondition;
    }

    public void setWaterCondition(WaterCondition conditionPassed) {
        mWaterCondition = conditionPassed;
    }

    @Override
    public String toString() {
        return super.toString() + "\nType: " + mWaterType +
                "\nCondition: " + mWaterCondition;
    }
}
