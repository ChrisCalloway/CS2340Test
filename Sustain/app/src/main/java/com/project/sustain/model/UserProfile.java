package com.project.sustain.model;

import com.google.firebase.database.IgnoreExtraProperties;

/**
 * Created by Marcia on 2/27/2017.
 */

@IgnoreExtraProperties
public class UserProfile {

    private String mUserID;
    private UserType mUserType;
    private String mEmailAddress;
    private Address mHomeAddress;
    private PhoneNumber mPhoneNumber1;
    private PhoneNumber mPhoneNumber2;
    private PhoneNumber mPhoneNumber3;

    public UserType getUserType() {
        return mUserType;
    }

    public void setUserType(UserType userType) {
        mUserType = userType;
    }

    public String getEmailAddress() {
        return mEmailAddress;
    }

    public void setEmailAddress(String emailAddress) {
        mEmailAddress = emailAddress;
    }

    public Address getHomeAddress() {
        return mHomeAddress;
    }

    public void setHomeAddress(Address homeAddress) {
        mHomeAddress = homeAddress;
    }

    public PhoneNumber getPhoneNumber1() {
        return mPhoneNumber1;
    }

    public void setPhoneNumber1(PhoneNumber phoneNumber1) {
        mPhoneNumber1 = phoneNumber1;
    }

    public PhoneNumber getPhoneNumber2() {
        return mPhoneNumber2;
    }

    public void setPhoneNumber2(PhoneNumber phoneNumber2) {
        mPhoneNumber2 = phoneNumber2;
    }

    public PhoneNumber getPhoneNumber3() {
        return mPhoneNumber3;
    }

    public void setPhoneNumber3(PhoneNumber phoneNumber3) {
        mPhoneNumber3 = phoneNumber3;
    }

    public String getUserID() {
        return mUserID;
    }

    public void setUserID(String userID) {
        mUserID = userID;
    }



}
