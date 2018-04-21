package com.lebapps.topgold.functionality;

import java.io.Serializable;

/**
 *
 */

public class Functionality implements Serializable {
    private int functionalityResource;
    private int imageResource;
    private String functionalityCode;

    public Functionality(int functionalityResource, int imageResource, String functionalityCode) {
        this.functionalityResource = functionalityResource;
        this.imageResource = imageResource;
        this.functionalityCode = functionalityCode;
    }

    public int getFunctionalityResource() {
        return functionalityResource;
    }

    public int getImageResource() {
        return imageResource;
    }

    public String getFunctionalityCode() {
        return functionalityCode;
    }
}
