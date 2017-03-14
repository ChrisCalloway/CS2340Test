package com.project.sustain.model;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by Chris on 3/10/17.
 */

public class Location implements Parcelable {
    private double _latitude;
    private double _longitude;

    public Location() {

    }

    public Location(double lat, double longit) {
        _latitude = lat;
        _longitude = longit;
    }

    public double getLatitude() { return _latitude; }
    public double getLongitude() { return _longitude; }

    // Methods to implement for Parcelable
    public Location(Parcel parcel) {
        _latitude = parcel.readDouble();
        _longitude = parcel.readDouble();
    }

    public int describeContents() {
        return 0;
    }

    public void writeToParcel(Parcel dest, int flag) {
        dest.writeDouble(_latitude);
        dest.writeDouble(_longitude);
    }

    public static final Parcelable.Creator<Location> CREATOR = new Parcelable.Creator<Location>() {
        @Override
        public Location createFromParcel(Parcel parcel) {
            return new Location(parcel);
        }
        @Override
        public Location[] newArray(int size) {
            return new Location[size];
        }
    };

}
