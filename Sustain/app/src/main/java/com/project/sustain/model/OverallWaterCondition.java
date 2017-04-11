package com.project.sustain.model;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Enum class for overall water condition.
 * @author Chris
 */

public enum OverallWaterCondition implements Parcelable {
    SAFE("Safe"),
    TREATABLE("Treatable"),
    UNSAFE("Unsafe");

    private final String overallWaterCondition;

    OverallWaterCondition (String overallWaterCondition) {
        this.overallWaterCondition = overallWaterCondition;
    }

    public String toString() {

        return this.overallWaterCondition;
    }

    public static final Parcelable.Creator<OverallWaterCondition> CREATOR = new Parcelable.Creator<OverallWaterCondition>() {
        public OverallWaterCondition createFromParcel(Parcel in) {
            return OverallWaterCondition.values()[in.readInt()];
        }
        public OverallWaterCondition[] newArray(int size) {
            return new OverallWaterCondition[size];
        }
    };

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel out, int flag) {
        out.writeInt(ordinal());
    }
}
