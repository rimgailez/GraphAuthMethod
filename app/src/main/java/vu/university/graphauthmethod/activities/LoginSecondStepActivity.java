package vu.university.graphauthmethod.activities;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.provider.Settings;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;
import vu.university.graphauthmethod.models.Color;
import vu.university.graphauthmethod.constants.Colors;
import vu.university.graphauthmethod.helpers.AuthenticationHelper;
import vu.university.graphauthmethod.R;

import java.util.HashMap;
import java.util.Map;

public class LoginSecondStepActivity extends BaseActivity {

    private final Colors colors = new Colors();
    private android.graphics.Color color;

    private final AuthenticationHelper authenticationHelper = new AuthenticationHelper();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.login_second_step_activity);
        Color firstColor = (Color) getIntent().getSerializableExtra("first_color");

        Context context = getApplicationContext();

        Button finishLogin = findViewById(R.id.finishLogin);
        finishLogin.setOnClickListener(view -> {
            if(color == null){
                CharSequence text = "Nepasirinkote spalvoto kvadrato!";
                Toast.makeText(context, text, Toast.LENGTH_SHORT).show();
            } else {
                Color secondColor = new Color(color.red(), color.green(), color.blue());
                String deviceId = Settings.Secure.getString(getContentResolver(), Settings.Secure.ANDROID_ID);
                CharSequence text;
                if(authenticationHelper.login(firstColor, secondColor, deviceId)){
                    text = "Prisijungimas sėkmingas.";
                } else {
                    text = "Prisijungimas nesėkmingas. Prašome pabandyti dar kartą.";
                }
                Toast.makeText(context, text, Toast.LENGTH_SHORT).show();
                Intent intent = new Intent(LoginSecondStepActivity.this, MainActivity.class);
                startActivity(intent);
            }
        });

        Map<Integer, Button> buttonsMap = new HashMap<>();
        for(int i = 1; i <= 18; i++){
            String buttonId = "square4_" + i;
            int resId = getResources().getIdentifier(buttonId, "id", getPackageName());
            buttonsMap.put(resId, findViewById(resId));
        }

        View.OnClickListener listener = view -> color = android.graphics.Color.valueOf(buttonsMap.get(view.getId()).getHighlightColor());

        addColors(buttonsMap, colors.getAllColors(), listener);
    }
}
