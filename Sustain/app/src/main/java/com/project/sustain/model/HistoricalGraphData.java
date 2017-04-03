package com.project.sustain.model;
import android.os.Parcelable;
import android.os.Parcel;

/**
 * Created by Chris on 4/2/17.
 */

public class HistoricalGraphData implements Parcelable {
    private String location;
    private String year;
    private String dataType;

    public HistoricalGraphData(String location, String year, String dataType) {
        this.location = location;
        this.year = year;
        this.dataType = dataType;
    }

    public HistoricalGraphData() {
        this.location = "";
        this.year = "";
        this.dataType = "";
    }

    /**
     * Use when reconstructing HistoricalGraphData object from parcel
     * This will be used only by the 'CREATOR'
     * @param in a parcel to read this object
     */
    public HistoricalGraphData(Parcel in) {
        this.location = in.readString();
        this.year = in.readString();
        this.dataType = in.readString();
    }

    @Override
    public int describeContents() {
        return 0;
    }

    /**
     * Actual object serialization happens here, Write object content
     * to parcel one by one, reading should be done according to this write order
     * @param dest parcel
     * @param flags Additional flags about how the object should be written
     */
    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(this.location);
        dest.writeString(this.year);
        dest.writeString(this.dataType);
    }

    /**
     * This field is needed for Android to be able to
     * create new objects, individually or as arrays
     *
     * If you don’t do that, Android framework will through exception
     * Parcelable protocol requires a Parcelable.Creator object called CREATOR
     */
    public static final Parcelable.Creator<HistoricalGraphData> CREATOR =
            new Parcelable.Creator<HistoricalGraphData>() {

        public HistoricalGraphData createFromParcel(Parcel in) {
            return new HistoricalGraphData(in);
        }

        public HistoricalGraphData[] newArray(int size) {
            return new HistoricalGraphData[size];
        }
    };

    public void setLocation(String location) {
        this.location = location;
    }

    public String getLocation() {
        return this.location;
    }

    public void setYear(String year) {
        this.year = year;
    }

    public String getYear() {
        return this.year;
    }

    public void setDataType(String dataType) {
        this.dataType = dataType;
    }

    public String getDataType() {
        return this.dataType;
    }
}
