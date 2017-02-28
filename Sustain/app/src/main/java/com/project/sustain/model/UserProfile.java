package com.project.sustain.model;

import com.google.firebase.database.IgnoreExtraProperties;

/**
 * Created by Marcia on 2/27/2017.
 */

@IgnoreExtraProperties
public class UserProfile {
    private UserType userType;
    private String emailAddress;
    private Address homeAddress;
    private PhoneNumber phoneNumber1;
    private PhoneNumber phoneNumber2;
    private PhoneNumber phoneNumber3;

    public UserType getUserType() {
        return userType;
    }

    public void setUserType(UserType userType) {
        this.userType = userType;
    }

    public String getEmailAddress() {
        return emailAddress;
    }

    public void setEmailAddress(String emailAddress) {
        this.emailAddress = emailAddress;
    }

    public Address getHomeAddress() {
        return homeAddress;
    }

    public void setHomeAddress(Address homeAddress) {
        this.homeAddress = homeAddress;
    }

    public PhoneNumber getPhoneNumber1() {
        return phoneNumber1;
    }

    public void setPhoneNumber1(PhoneNumber phoneNumber1) {
        this.phoneNumber1 = phoneNumber1;
    }

    public PhoneNumber getPhoneNumber2() {
        return phoneNumber2;
    }

    public void setPhoneNumber2(PhoneNumber phoneNumber2) {
        this.phoneNumber2 = phoneNumber2;
    }

    public PhoneNumber getPhoneNumber3() {
        return phoneNumber3;
    }

    public void setPhoneNumber3(PhoneNumber phoneNumber3) {
        this.phoneNumber3 = phoneNumber3;
    }


}
