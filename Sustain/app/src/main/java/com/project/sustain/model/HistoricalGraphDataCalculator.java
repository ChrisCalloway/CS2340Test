package com.project.sustain.model;

import java.io.Serializable;


/**
 * Model class used to store parameters in showing proper graphing information.
 * @author Chris
 */

public class HistoricalGraphDataCalculator implements Serializable {
    private String location;
    private String year;
    private String dataType;

    /**
     * Constructor that initializes the object.
     * @param location Name of location selected
     * @param year Year selected
     * @param dataType Type of data selected, either virus or contaminant
     */
    @SuppressWarnings("SameParameterValue")
    private HistoricalGraphDataCalculator(String location, String year, String dataType) {
        this.location = location;
        this.year = year;
        this.dataType = dataType;

    }

    /**
     *  Uses constructor chaining to invoke above constructor with empty strings.
     */
    public HistoricalGraphDataCalculator() {
        this("","","");
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public String getLocation() {
        return this.location;
    }

    public void setYear(String year) { this.year = year; }

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
