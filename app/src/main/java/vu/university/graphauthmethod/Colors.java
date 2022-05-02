package vu.university.graphauthmethod;

import android.graphics.Color;

public class Colors {

    public int[] mainColors = new int[] {Color.rgb(255,0, 0), Color.rgb(255,128,0),
            Color.rgb(255,255,0), Color.rgb(128,255,0),
            Color.rgb(0,153,0), Color.rgb(0, 255, 255),
            Color.rgb(51, 153, 255), Color.rgb(0, 0, 255),
            Color.rgb(127,0,255), Color.rgb(255, 0, 255)};

    public int[] additionalColors = new int[] {Color.rgb(255,117,163), Color.rgb(215, 175, 250),
            Color.rgb(0, 153, 153), Color.rgb(190, 232, 106),
            Color.rgb(128,128,128), Color.rgb(255, 133, 108),
            Color.rgb(153,0,76), Color.rgb(155, 53, 2)};

    public int[] getAllColors(){
        int[] allColors = new int[mainColors.length + additionalColors.length];
        System.arraycopy(mainColors, 0, allColors, 0, mainColors.length);
        System.arraycopy(additionalColors, 0, allColors, mainColors.length, additionalColors.length);
        return allColors;
    }
}
