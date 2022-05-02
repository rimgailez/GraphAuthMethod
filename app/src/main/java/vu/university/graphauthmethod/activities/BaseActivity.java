package vu.university.graphauthmethod.activities;

import android.view.View;
import android.widget.Button;
import androidx.appcompat.app.AppCompatActivity;

import java.util.Map;
import java.util.Random;

public abstract class BaseActivity extends AppCompatActivity {

    protected void addColors(Map<Integer, Button> buttonsMap, int[] colors, View.OnClickListener listener){
        Random rand = new Random();
        for (Map.Entry<Integer, Button> entry: buttonsMap.entrySet()) {
            entry.getValue().setOnClickListener(listener);
            int index = rand.nextInt(colors.length);
            entry.getValue().setBackgroundColor(colors[index]);
            entry.getValue().setHighlightColor(colors[index]);
            colors = removeValue(index, colors);
        }
    }

    protected int[] removeValue(int index, int[] colors){
        int[] copy = new int[colors.length - 1];
        for (int i = 0, j = 0; i < colors.length; i++) {
            if (i != index) {
                copy[j++] = colors[i];
            }
        }
        return copy;
    }
}
