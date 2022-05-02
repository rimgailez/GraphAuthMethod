package vu.university.graphauthmethod.activities;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.provider.Settings;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;
import vu.university.graphauthmethod.Color;
import vu.university.graphauthmethod.Colors;
import vu.university.graphauthmethod.PasswordHelper;
import vu.university.graphauthmethod.R;

import java.util.HashMap;
import java.util.Map;

public class RegistrationSecondStep extends BaseActivity {

    private final Colors colors = new Colors();
    private android.graphics.Color color;

    private final PasswordHelper passwordHelper = new PasswordHelper();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.registration_second_step);
        Color firstColor = (Color) getIntent().getSerializableExtra("first_color");

        Button next = findViewById(R.id.tryAgain);
        next.setOnClickListener(view -> {
            Intent intent = new Intent(RegistrationSecondStep.this, RegistrationFirstStep.class);
            startActivity(intent);
        });

        Context context = getApplicationContext();

        Button finishRegistration = findViewById(R.id.finishRegistration);
        finishRegistration.setOnClickListener(view -> {
            if(color == null){
                CharSequence text = "Nepasirinkote spalvoto kvadrato!";
                Toast.makeText(context, text, Toast.LENGTH_SHORT).show();
            } else {
                Color secondColor = new Color(color.red(), color.green(), color.blue());
                if(passwordHelper.checkIfPasswordIsValid(firstColor, secondColor)){
                    String deviceId = Settings.Secure.getString(getContentResolver(), Settings.Secure.ANDROID_ID);
                    CharSequence text;
                    if(passwordHelper.register(firstColor, secondColor, deviceId)){
                        text = "Registracija sėkminga.";
                    } else {
                        text = "Registracija nesėkminga.";
                    }
                    Toast.makeText(context, text, Toast.LENGTH_SHORT).show();
                    Intent intent = new Intent(RegistrationSecondStep.this, MainActivity.class);
                    startActivity(intent);
                } else {
                    CharSequence text = "Pasirinktas slaptažodis nėra pakankamai saugus! Rekomenduojama rinktis skirtingų spalvų figūras.";
                    Toast.makeText(context, text, Toast.LENGTH_SHORT).show();
                }
            }
        });

        Map<Integer, Button> buttonsMap = new HashMap<>();
        for(int i = 1; i <= 18; i++){
            String buttonId = "square2_" + i;
            int resId = getResources().getIdentifier(buttonId, "id", getPackageName());
            buttonsMap.put(resId, findViewById(resId));
        }

        View.OnClickListener listener = view -> color = android.graphics.Color.valueOf(buttonsMap.get(view.getId()).getHighlightColor());

        addColors(buttonsMap, colors.getAllColors(), listener);
    }
}
