package com.project.sustain.model;

/**
 * Created by georgiainstituteoftechnology on 3/2/17.
 */

public class WaterReport {
    private String date;
    private String time;
    private int reportNumber;
    private String name;
    private Address locationSub;
    private WaterType typeWater;
    private WaterCondition conditionWater;
    private String uid;

    public String getUid() {
        return uid;
    }

    public void setUid(String uid) {
        this.uid = uid;
    }

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
}
