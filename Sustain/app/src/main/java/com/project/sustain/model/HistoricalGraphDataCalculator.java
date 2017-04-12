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

    /**
     * Method that will set the location
     * @param location The location to be set
     */
    public void setLocation(String location) {
        this.location = location;
    }

    /**
     * Method that will get the location
     * @return the location
     */
    public String getLocation() {
        return this.location;
    }

    /**
     * Method that will set the year.
     * @param year The year to be set
     */
    public void setYear(String year) { this.year = year; }

    /**
     * Method that will get the year
     * @return the year
     */
    public String getYear() {
        return this.year;
    }

    /**
     * Method that will set the dataType
     * @param dataType The dataTypes: virus or contaminant
     */
    public void setDataType(String dataType) {
        this.dataType = dataType;
    }

    /**
     * Method that will get the dataType
     * @return the dataType (virus or contaminant)
     */
    public String getDataType() {
        return this.dataType;
    }
}
