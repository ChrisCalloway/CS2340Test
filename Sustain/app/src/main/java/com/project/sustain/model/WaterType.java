package com.project.sustain.model;

/**
 * Created by georgiainstituteoftechnology on 3/2/17.
 */

public enum WaterType {
    BOTTLED("Bottled"),
    WELL("Well"),
    STREAM("Stream"),
    LAKE("Lake"),
    SPRING("Spring"),
    OTHER("Other");

    private String typeDeclared;

    WaterType (String typePassed) {
        typeDeclared = typePassed;
    }

    public String toString() {
        return typeDeclared;
    }
}
