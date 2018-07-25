package com.beceriklimedya.unikazani;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import com.beceriklimedya.unikazani.CustomAdapter.ProfileListAdapter;

import java.util.ArrayList;

public class Profile extends AppCompatActivity {

    private ArrayList<String> MainArrayName = new ArrayList<>();
    private ArrayList<String> MainArrayCategory = new ArrayList<>();
    private ArrayList<String> MainArrayTime = new ArrayList<>();
    private ArrayList<String> MainArrayText = new ArrayList<>();
    private ArrayList<String> MainArrayImage = new ArrayList<>();
    private ArrayList<String> MainArrayProfile = new ArrayList<>();
    private ArrayList<String> MainArrayLike = new ArrayList<>();
    private ArrayList<String> MainArrayHashTag = new ArrayList<>();

    private ImageButton profileChat;
    private ImageView profilePhoto;
    private TextView profileShare;
    private TextView profileScore;
    private TextView profileAge;
    private TextView profileUni;
    private TextView profileName;
    private ImageButton profileSettings;
    private ImageButton profileBack;

    private ListView profileList;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);

        profileChat = findViewById(R.id.profile_chat);
        profilePhoto = findViewById(R.id.profile_photo);
        profileShare = findViewById(R.id.profile_shares);
        profileScore = findViewById(R.id.profile_score);
        profileAge = findViewById(R.id.profile_age);
        profileUni = findViewById(R.id.profile_university);
        profileName = findViewById(R.id.profile_name);
        profileSettings = findViewById(R.id.profile_settings);
        profileBack = findViewById(R.id.profile_back);
        profileList = findViewById(R.id.profile_list);

        profileBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Profile.super.onBackPressed();
                overridePendingTransition(R.anim.geri1,R.anim.geri2);
            }
        });

        fill();

    }

    private void fill()
    {
        MainArrayName.add("Semih Aslan");
        MainArrayName.add("Ümit Bekir Hepyakışıklı");
        MainArrayName.add("Doğan Topdaş");
        MainArrayName.add("Semih Aslan");

        MainArrayCategory.add("İtiraf");
        MainArrayCategory.add("Ev Arkadaşı");
        MainArrayCategory.add("Ders Notu");
        MainArrayCategory.add("Etkinlik");

        MainArrayHashTag.add("Ege Üniversitesi");
        MainArrayHashTag.add("Bilkent Üniversitesi");
        MainArrayHashTag.add("ODTÜ Üniversitesi");
        MainArrayHashTag.add("Ege Üniversitesi");

        MainArrayLike.add("14");
        MainArrayLike.add("14");
        MainArrayLike.add("14");
        MainArrayLike.add("14");

        MainArrayText.add("Selamlar nasılsınız?");
        MainArrayText.add("Selamlar nasılsınız?");
        MainArrayText.add("Selamlar nasılsınız?");
        MainArrayText.add("Selamlar nasılsınız?");

        MainArrayTime.add("14:40");
        MainArrayTime.add("14:40");
        MainArrayTime.add("14:40");
        MainArrayTime.add("14:40");

        profileList.setAdapter(new ProfileListAdapter(Profile.this,
                MainArrayName,
                MainArrayCategory,
                MainArrayTime,
                MainArrayText,
                MainArrayImage,
                MainArrayProfile,
                MainArrayLike,MainArrayHashTag));
    }


    @Override
    public void onBackPressed() {
        super.onBackPressed();
        overridePendingTransition(R.anim.geri1,R.anim.geri2);
    }
}
