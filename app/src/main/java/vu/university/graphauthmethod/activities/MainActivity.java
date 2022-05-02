package vu.university.graphauthmethod.activities;

import android.content.Context;
import android.content.Intent;
import android.provider.Settings;
import android.widget.Button;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import vu.university.graphauthmethod.PasswordHelper;
import vu.university.graphauthmethod.R;

public class MainActivity extends AppCompatActivity {

    private final PasswordHelper passwordHelper = new PasswordHelper();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Button register = findViewById(R.id.register);
        Button login = findViewById(R.id.login);

        Context context = getApplicationContext();
        String deviceId = Settings.Secure.getString(getContentResolver(), Settings.Secure.ANDROID_ID);

        register.setOnClickListener(view -> {
            if(passwordHelper.checkIfUserCanRegister(deviceId)){
                Intent intent = new Intent(MainActivity.this, RegistrationFirstStep.class);
                startActivity(intent);
            } else {
                CharSequence text = "Registracija jau buvo atlikta.";
                Toast.makeText(context, text, Toast.LENGTH_SHORT).show();
            }
        });

        login.setOnClickListener(view -> {
            if(passwordHelper.checkIfUserCanRegister(deviceId)){
                CharSequence text = "Registracija dar nebuvo atlikta.";
                Toast.makeText(context, text, Toast.LENGTH_SHORT).show();
            } else {
                Intent intent = new Intent(MainActivity.this, LoginFirstStep.class);
                startActivity(intent);
            }
        });
    }
}