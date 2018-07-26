package com.beceriklimedya.unikazani;

import android.app.Dialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.preference.PreferenceManager;
import android.support.v4.content.res.ResourcesCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.Toast;

import com.beceriklimedya.unikazani.CustomAdapter.MainAdapter;

import java.util.ArrayList;

public class MainScreen extends AppCompatActivity {

    private ArrayList<String> MainArrayName = new ArrayList<>();
    private ArrayList<String> MainArrayCategory = new ArrayList<>();
    private ArrayList<String> MainArrayTime = new ArrayList<>();
    private ArrayList<String> MainArrayText = new ArrayList<>();
    private ArrayList<String> MainArrayImage = new ArrayList<>();
    private ArrayList<String> MainArrayProfile = new ArrayList<>();
    private ArrayList<String> MainArrayLike = new ArrayList<>();
    private ArrayList<String> MainArrayHashTag = new ArrayList<>();

    private ImageButton btnHome;
    private ImageButton btnAdd;
    private ImageButton btnSearch;
    private ImageButton btnNotification;
    private ImageButton btnProfile;

    private ListView main_list;

    private Dialog myDialog;
    private String userId;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_screen);

        SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(getApplicationContext());
        userId = preferences.getString("userId", "N/A");

        myDialog = new Dialog(this);
        main_list = findViewById(R.id.mainlist);

        btnHome = findViewById(R.id.main_home);
        btnAdd = findViewById(R.id.main_add);
        btnSearch = findViewById(R.id.main_search);
        btnNotification = findViewById(R.id.main_notification);
        btnProfile = findViewById(R.id.main_profile);

        fill();

        main_list.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                startActivity(new Intent(MainScreen.this,Comment.class));
            }
        });

        btnAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(MainScreen.this,Add.class));
                overridePendingTransition(R.anim.ileri1,R.anim.ileri2);
            }
        });

        btnNotification.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(MainScreen.this,Notifications.class));
                overridePendingTransition(R.anim.ileri1,R.anim.ileri2);
            }
        });

        btnSearch.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(MainScreen.this,Search.class));
                overridePendingTransition(R.anim.ileri1,R.anim.ileri2);
            }
        });

        btnProfile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(MainScreen.this,Profile.class));
                overridePendingTransition(R.anim.ileri1,R.anim.ileri2);

            }
        });

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


        main_list.setAdapter(new MainAdapter(MainScreen.this,
                MainArrayName,
                MainArrayCategory,
                MainArrayTime,
                MainArrayText,
                MainArrayImage,
                MainArrayProfile,
                MainArrayLike,MainArrayHashTag));
    }
}
