package com.beceriklimedya.unikazani;

import android.content.Intent;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.RadioButton;
import android.widget.Spinner;

import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.toolbox.Volley;
import com.beceriklimedya.unikazani.JSON.SearchJSON;
import com.squareup.picasso.Picasso;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

import de.hdodenhof.circleimageview.CircleImageView;

public class ProfileSettings extends AppCompatActivity {

    private ArrayList<String> SearchUniName = new ArrayList<>();
    private ArrayList<String> SearchUniId = new ArrayList<>();

    private FloatingActionButton settingsApply;
    private Spinner settingsUni;
    private RadioButton settingsMale;
    private RadioButton settingsFemale;
    private EditText settingsBirthday;
    private Button settingsChange;
    private CircleImageView settingsPhoto;

    private ImageButton settingsBack;

    private String sex;

    private String userId;
    private String profile;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile_settings);

        SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(getApplicationContext());
        userId = preferences.getString("userId", "N/A");
        profile = preferences.getString("profile", "N/A");

        Button logout = findViewById(R.id.logout);
        settingsApply = findViewById(R.id.settingsApply);
        settingsUni = findViewById(R.id.settingsUni);
        settingsMale = findViewById(R.id.settingsMale);
        settingsFemale = findViewById(R.id.settingsFemale);
        settingsBirthday = findViewById(R.id.settingsBirthday);
        settingsChange = findViewById(R.id.settingsChange);
        settingsPhoto = findViewById(R.id.settingsPhoto);
        settingsBack = findViewById(R.id.settingsBack);

        getUni();

        Picasso.get()
                .load("http://www.unikazani.com/json/upload/" + profile + ".jpg")
                .into(settingsPhoto);

        settingsMale.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked)
                {
                    sex = "1";
                    settingsFemale.setChecked(false);
                }
            }
        });
        settingsFemale.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked)
                {
                    sex = "2";
                    settingsMale.setChecked(false);
                }
            }
        });
        settingsBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ProfileSettings.super.onBackPressed();
                overridePendingTransition(R.anim.geri1,R.anim.geri2);
            }
        });

        logout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(getApplicationContext());
                SharedPreferences.Editor editor = preferences.edit();
                editor.putString("userId", "");
                editor.putString("remember", "0");
                editor.putString("auth", "");
                editor.putString("username", "");
                editor.putString("password", "");
                editor.commit();

                startActivity(new Intent(ProfileSettings.this,MainScreen.class));
                overridePendingTransition(R.anim.ileri1,R.anim.ileri2);

            }
        });
    }

    private void getUni()
    {
        Response.Listener<String> responselistener = new Response.Listener<String>()
        {
            @Override
            public void onResponse(String response) {
                try {

                    JSONObject jsonresponse = new JSONObject(response);

                    JSONArray university_id = jsonresponse.getJSONArray("university_id");
                    JSONArray university_name = jsonresponse.getJSONArray("university_name");

                    for (int i = 0; i < university_id.length(); i++){
                        SearchUniId.add(i, university_id.get(i).toString());
                        SearchUniName.add(i, university_name.get(i).toString());
                    }

                    ArrayAdapter<String> adapter = new ArrayAdapter<String>(ProfileSettings.this, android.R.layout.simple_spinner_dropdown_item, SearchUniName);
                    adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                    settingsUni.setAdapter(adapter);

                }
                catch (JSONException e)
                {
                    e.printStackTrace();
                }
            }

        };

        SearchJSON loginrequest = new SearchJSON(userId,"10",responselistener);
        RequestQueue queue = Volley.newRequestQueue(ProfileSettings.this);
        queue.add(loginrequest);
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        overridePendingTransition(R.anim.geri1,R.anim.geri2);
    }
}
