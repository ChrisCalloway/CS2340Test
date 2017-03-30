package com.project.sustain.model;

import android.os.Parcel;
import android.os.Parcelable;


/**
 * Created by georgiainstituteoftechnology on 3/2/17.
 */

public enum WaterType implements Parcelable{
    BOTTLED("Bottled"),
    WELL("Well"),
    STREAM("Stream"),
    LAKE("Lake"),
    SPRING("Spring"),
    OTHER("Other");

    public static final Parcelable.Creator<WaterType> CREATOR = new Parcelable.Creator<WaterType>() {
        public WaterType createFromParcel(Parcel in) {
            return WaterType.values()[in.readInt()];
        }

        public WaterType[] newArray(int size) {
            return new WaterType[size];
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

    private String typeDeclared;

    WaterType (String typePassed) {
        typeDeclared = typePassed;
    }

    public String toString() {
        return typeDeclared;
    }
}
