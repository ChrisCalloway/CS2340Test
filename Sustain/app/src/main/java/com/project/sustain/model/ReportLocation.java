package com.project.sustain.model;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by Chris on 3/10/17.
 */

public class ReportLocation implements Parcelable {
    private double latitude;
    private double longitude;

    public ReportLocation() {}

    public ReportLocation(double lat, double longit) {
        latitude = lat;
        longitude = longit;
    }

    public double getLatitude() { return latitude; }
    public double getLongitude() { return longitude; }

    // Methods to implement for Parcelable
    public ReportLocation(Parcel parcel) {
        latitude = parcel.readDouble();
        longitude = parcel.readDouble();
    }

    public int describeContents() {
        return 0;
    }

    public void writeToParcel(Parcel dest, int flag) {
        dest.writeDouble(latitude);
        dest.writeDouble(longitude);
    }

    public static final Parcelable.Creator<ReportLocation> CREATOR = new Parcelable.Creator<ReportLocation>() {
        @Override
        public ReportLocation createFromParcel(Parcel parcel) {
            return new ReportLocation(parcel);
        }
        @Override
        public ReportLocation[] newArray(int size) {
            return new ReportLocation[size];
        }
    };

}
