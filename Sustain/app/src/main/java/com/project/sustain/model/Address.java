package com.project.sustain.model;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.firebase.database.IgnoreExtraProperties;

/**
 * Created by Marcia on 2/27/2017.
 */

@IgnoreExtraProperties
public class Address implements Parcelable {
    private String streetAddress1;
    private String streetAddress2;
    private String city;
    private String stateOrProvince;
    private String country;
    private String zipCode;

    public Address() {
        streetAddress1 = "";
        streetAddress2 = "";
        city = "";
        stateOrProvince = "";
        country = "";
        zipCode = "";
    }

    public String getStreetAddress1() {
        return streetAddress1;
    }

    public void setStreetAddress1(String streetAddress1) {
        this.streetAddress1 = streetAddress1;
    }

    public String getStreetAddress2() { return streetAddress2; }

    public void setStreetAddress2(String streetAddress2) {
        this.streetAddress2 = streetAddress2;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getStateOrProvince() {
        return stateOrProvince;
    }

    public void setStateOrProvince(String stateOrProvince) {
        this.stateOrProvince = stateOrProvince;
    }

    public String getCountry() {
        return country;
    }

    public void setCountry(String country) {
        this.country = country;
    }

    public String getZipCode() {
        return zipCode;
    }

    public void setZipCode(String zipCode) {
        this.zipCode = zipCode;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(streetAddress1);
        dest.writeString(streetAddress2);
        dest.writeString(city);
        dest.writeString(stateOrProvince);
        dest.writeString(country);
        dest.writeString(zipCode);
    }

    public static final Parcelable.Creator
        CREATOR = new Parcelable.Creator() {
            public Address createFromParcel(Parcel in) {
                return new Address(in);
            }

            public Address[] newArray(int size) {
                return new Address[size];
            }
    };

    private Address(Parcel in) {
        streetAddress1 = in.readString();
        streetAddress2 = in.readString();
        city = in.readString();
        stateOrProvince = in.readString();
        country = in.readString();
        zipCode = in.readString();
    }
//    public void writeToParcel(Parcel dest, int flags) {
//        dest.writeString(streetAddress1);
//        dest.writeString(city);
//        dest.writeString(stateOrProvince);
//        dest.writeString(country);
//        dest.writeString(zipCode);
//    }

//    public Address (Parcel parcel) {
//        streetAddress1 = parcel.readString();
//        city = parcel.readString();
//        stateOrProvince = parcel.readString();
//        country = parcel.readString();
//        zipCode = parcel.readString();
//    }

//    public static final Parcelable.Creator<Address> CREATOR = new Parcelable.Creator<Address>() {
//        @Override
//        public Address createFromParcel(Parcel parcel) {
//            return new Address(parcel);
//        }
//        @Override
//        public Address[] newArray(int size) {
//            return new Address[size];
//        }
//    };

//    public int describeContents() {
//        return 0;
//    }
}
