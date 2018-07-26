package com.beceriklimedya.unikazani;

import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.RadioButton;
import android.widget.Spinner;

import de.hdodenhof.circleimageview.CircleImageView;

public class ProfileSettings extends AppCompatActivity {

    private FloatingActionButton settingsApply;
    private Spinner settingsUni;
    private RadioButton settingsMale;
    private RadioButton settingsFemale;
    private EditText settingsBirthday;
    private Button settingsChange;
    private CircleImageView settingsPhoto;

    private ImageButton settingsBack;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile_settings);

        settingsApply = findViewById(R.id.settingsApply);
        settingsUni = findViewById(R.id.settingsUni);
        settingsMale = findViewById(R.id.settingsMale);
        settingsFemale = findViewById(R.id.settingsFemale);
        settingsBirthday = findViewById(R.id.settingsBirthday);
        settingsChange = findViewById(R.id.settingsChange);
        settingsPhoto = findViewById(R.id.settingsPhoto);

        settingsBack = findViewById(R.id.settingsBack);
        settingsBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ProfileSettings.super.onBackPressed();
                overridePendingTransition(R.anim.geri1,R.anim.geri2);
            }
        });

    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        overridePendingTransition(R.anim.geri1,R.anim.geri2);
    }
}
