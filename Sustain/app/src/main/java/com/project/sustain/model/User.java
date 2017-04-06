package com.project.sustain.model;

import com.google.firebase.database.IgnoreExtraProperties;

import java.io.Serializable;

/**
 * Created by Marcia on 2/27/2017.
 */

@IgnoreExtraProperties
public class User implements Serializable {
    private UserType userType;
    private UserPermissions userPermissions;
    private String userName;
    private String userId;
    private String emailAddress;
    private Address homeAddress;

    public User() {
        this(UserType.USER, "", "", "", new Address());
    }

    public User(UserType userType, String userName, String userId,
                String emailAddress, Address homeAddress) {
        this.userType = userType;
        this.userName = userName;
        this.userId = userId;
        this.emailAddress = emailAddress;
        this.homeAddress = homeAddress;
    }

    public String getUserId() {  return userId;  }

    public void setUserId(String userId) { this.userId = userId;  }

    public UserType getUserType() {
        return userType;
    }

    public void setUserType(UserType userType) {
        this.userType = userType;
        switch (userType) {
            case USER:
                this.userPermissions = new UserPermissions();
                break;
            case WORKER:
                this.userPermissions = new WorkerPermissions();
                break;
            case MANAGER:
                this.userPermissions = new ManagerPermissions();
                break;
            case ADMIN:
                this.userPermissions = new AdminPermissions();
                break;
            default:
                this.userPermissions = new UserPermissions();
        }
    }

    public UserPermissions getUserPermissions() { return this.userPermissions; }

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

    public  String getUserName() { return  userName; }

    public void setUserName(String userName) { this.userName = userName; }
}
