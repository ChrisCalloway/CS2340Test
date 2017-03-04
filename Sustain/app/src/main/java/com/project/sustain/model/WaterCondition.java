package com.project.sustain.model;

/**
 * Created by georgiainstituteoftechnology on 3/2/17.
 */

public enum WaterCondition {
    WASTE("Waste"),
    TREATABLECLEAR("Treatable-Clear"),
    TREATABLEMUDDY("Treatable-Muddy"),
    POTABLE("Potable");

    private String conditionDeclared;

    WaterCondition (String conditionPassed) {
        conditionDeclared = conditionPassed;
    }

    public String toString() {
        return conditionDeclared;
    }
}
