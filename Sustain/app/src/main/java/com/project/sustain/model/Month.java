package com.project.sustain.model;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by Chris on 4/3/17.
 */

public enum Month implements Parcelable {
//    JANUARY("Jan"),
//    FEBRUARY("Feb"),
//    MARCH("Mar"),
//    APRIL("Apr"),
//    MAY("May"),
//    JUNE("Jun"),
//    JULY("Jul"),
//    AUGUST("Aug"),
//    SEPTEMBER("Sept"),
//    OCTOBER("Oct"),
//    NOVEMBER("Nov"),
//    DECEMBER("Dec");
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


// --Commented out by Inspection START (4/10/2017 21:06 PM):
//    public String getAbbrName() {
//        return abbrName;
//    }
// --Commented out by Inspection STOP (4/10/2017 21:06 PM)

}
