package com.project.sustain.model;

import com.google.firebase.database.IgnoreExtraProperties;

import java.io.Serializable;

/**
 * Model class for water source report.
 * @author Anish
 */
@IgnoreExtraProperties
public class WaterSourceReport extends Report implements Serializable {
    private WaterType mWaterType;
    private WaterCondition mWaterCondition;

    // Constructor
    public WaterSourceReport() {
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
        return super.toString()
                + "\nType: " + mWaterType
                + "\nCondition: " + mWaterCondition;
    }
}
