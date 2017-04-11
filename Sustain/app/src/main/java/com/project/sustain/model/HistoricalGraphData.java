package com.project.sustain.model;

import java.io.Serializable;
import java.util.Map;
/**
 * Created by Chris on 4/4/17.
 */

class HistoricalGraphData implements Serializable {
    private Map<Month, Double> coordinatePointData;

    public HistoricalGraphData(Map<Month, Double> coordinatePointData) {
        this.coordinatePointData = coordinatePointData;
    }

    public Map<Month, Double> getCoordinatePointData() {
        return this.coordinatePointData;
    }

    public void setCoordinatePointData(Map<Month, Double> coordinatePointData) {
        this.coordinatePointData = coordinatePointData;
    }
}
