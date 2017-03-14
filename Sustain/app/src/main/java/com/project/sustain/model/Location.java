package com.project.sustain.model;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by Chris on 3/10/17.
 * This is the model class for Location.  Location has a latitude and
 * longitude value.  In our app, we use the Model as the structuring of
 * storing the data in our Firebase database.  Location is used for
 * the abstract Report, which we subclass into WaterSourceReport
 * and WaterQualityReport
 */


public class Location implements Parcelable {
    private double latitude;
    private double longitude;

    public Location(double latitude, double longitude) {
        this.latitude = latitude;
        this.longitude = longitude;
    }

    public double getLatitude() {
        return this.latitude;
    }
    public double getLongitude() {
        return this.longitude;
    }

    // Methods to implement for Parcelable
    public Location(Parcel parcel) {
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
