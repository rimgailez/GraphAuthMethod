package vu.university.graphauthmethod;

import java.io.Serializable;

public class Color implements Serializable {

    public float red;
    public float green;
    public float blue;

    public Color(float red, float green, float blue){
        this.red = red;
        this.green = green;
        this.blue = blue;
    }
}
