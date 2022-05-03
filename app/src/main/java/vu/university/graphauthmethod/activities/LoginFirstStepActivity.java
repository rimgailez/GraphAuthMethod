package vu.university.graphauthmethod.activities;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;
import vu.university.graphauthmethod.models.Color;
import vu.university.graphauthmethod.constants.Colors;
import vu.university.graphauthmethod.R;

import java.util.HashMap;
import java.util.Map;

public class LoginFirstStepActivity extends BaseActivity {

    private final Colors colors = new Colors();
    private android.graphics.Color color;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.login_first_step_activity);

        Button next = findViewById(R.id.next1);
        next.setOnClickListener(view -> {
            if(color == null){
                Context context = getApplicationContext();
                CharSequence text = "Nepasirinkote spalvoto kvadrato!";
                Toast.makeText(context, text, Toast.LENGTH_SHORT).show();
            } else {
                Intent intent = new Intent(LoginFirstStepActivity.this, LoginSecondStepActivity.class);
                Color firstChoice = new Color(color.red(), color.green(), color.blue());
                intent.putExtra("first_color", firstChoice);
                startActivity(intent);
            }
        });

        Map<Integer, Button> buttonsMap = new HashMap<>();
        for(int i = 1; i <= 10; i++){
            String buttonId = "square3_" + i;
            int resId = getResources().getIdentifier(buttonId, "id", getPackageName());
            buttonsMap.put(resId, findViewById(resId));
        }

        View.OnClickListener listener = view -> color = android.graphics.Color.valueOf(buttonsMap.get(view.getId()).getHighlightColor());

        addColors(buttonsMap, colors.mainColors, listener);
    }
}
