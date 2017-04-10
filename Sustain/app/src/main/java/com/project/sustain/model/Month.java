package com.project.sustain.model;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Enum class for months of the year.
 * @author Chris
 */

public enum Month implements Parcelable {
    JAN("Jan"),
    FEB("Feb"),
    MAR("Mar"),
    APR("Apr"),
    MAY("May"),
    JUN("Jun"),
    JUL("Jul"),
    AUG("Aug"),
    SEPT("Sept"),
    OCT("Oct"),
    NOV("Nov"),
    DEC("Dec");

    private final String abbrName;

    Month(String abbr) {
        this.abbrName = abbr;
    }

    public static final Parcelable.Creator<Month> CREATOR = new Parcelable.Creator<Month>() {
        public Month createFromParcel(Parcel in) {
            return Month.values()[in.readInt()];
        }

        public Month[] newArray(int size) {
            return new Month[size];
        }
    };

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel out, int flags) {
        out.writeInt(ordinal());
    }


    public String getAbbrName() {
        return abbrName;
    }

}
