package vu.university.graphauthmethod.models;

import java.io.Serializable;

public class Color implements Serializable {

    private float red;
    private float green;
    private float blue;

    public Color(float red, float green, float blue){
        this.red = red;
        this.green = green;
        this.blue = blue;
    }

    public float getRed() {
        return red;
    }

    public void setRed(float red) {
        this.red = red;
    }

    public float getGreen() {
        return green;
    }

    public void setGreen(float green) {
        this.green = green;
    }

    public float getBlue() {
        return blue;
    }

    public void setBlue(float blue) {
        this.blue = blue;
    }
}
