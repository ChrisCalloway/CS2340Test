package com.project.sustain.model;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by georgiainstituteoftechnology on 3/2/17.
 */

public class WaterReport implements Parcelable {
    private String date;
    private String time;
    private int reportNumber;
    private String name;
    private Address locationSub;
    private WaterType typeWater;
    private WaterCondition conditionWater;
    private String userID;

    public WaterReport() {
        locationSub = new Address();
    }

    public String getDate() {
        return date;
    }

    public void setDate(String datePassed) {
        date = datePassed;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String timePassed) {
        time = timePassed;
    }

    public int getReportNumber() {
        return reportNumber;
    }

    public void setReportNumber(int reportNumPassed) {
        reportNumber = reportNumPassed;
    }

    public String getName() {
        return name;
    }

    public void setName(String namePassed) {
        name = namePassed;
    }

    public Address getAddress() {
        return locationSub;
    }

    public void setAddress(Address addressPassed) {
        locationSub = addressPassed;
    }

    public WaterType getTypeWater() {
        return typeWater;
    }

    public void setTypeWater(WaterType typePassed) {
        typeWater = typePassed;
    }

    public WaterCondition getConditionWater() {
        return conditionWater;
    }

    public void setConditionWater(WaterCondition conditionPassed) {
        conditionWater = conditionPassed;
    }

    public String getUserID() {
        return userID;
    }

    public void setUserID(String userID) {
        this.userID = userID;
    }

    public void writeToParcel(Parcel dest, int flag) {
        dest.writeString(date);
        dest.writeString(time);
        dest.writeInt(reportNumber);
        dest.writeString(name);
        dest.writeParcelable(locationSub, flag);
        dest.writeParcelable(typeWater, flag);
        dest.writeParcelable(conditionWater, flag);
        dest.writeString(userID);
//        dest.writeString(typeWater.toString());
//        dest.writeValue(typeWater);
//        dest.writeValue(conditionWater);
//        dest.writeValue(locationSub);
    }

    public WaterReport(Parcel parcel) {
        date = parcel.readString();
        time = parcel.readString();
        reportNumber = parcel.readInt();
        name = parcel.readString();
        locationSub = (Address) parcel.readParcelable(Address.class.getClassLoader());
        typeWater = parcel.readParcelable(WaterType.class.getClassLoader());
        conditionWater = parcel.readParcelable(WaterCondition.class.getClassLoader());
        userID = parcel.readString();
    }

    public static final Parcelable.Creator<WaterReport> CREATOR = new Parcelable.Creator<WaterReport>() {
        @Override
        public WaterReport createFromParcel(Parcel parcel) {
            return new WaterReport(parcel);
        }
        @Override
        public WaterReport[] newArray(int size) {
            return new WaterReport[size];
        }
    };

    @Override
    public int describeContents() {
        return 0;
    }
}
