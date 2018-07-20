package com.beceriklimedya.unikazani;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.Adapter;
import android.widget.ImageButton;
import android.widget.ListView;

import com.beceriklimedya.unikazani.CustomAdapter.MainList;
import com.beceriklimedya.unikazani.CustomAdapter.Notifications;
import com.beceriklimedya.unikazani.CustomAdapter.Profile;
import com.beceriklimedya.unikazani.CustomAdapter.SearchList;

import java.util.ArrayList;
import java.util.List;

public class MainScreen extends AppCompatActivity {


    // NOTIFICATIONS
    String NotificationArrayTitle[] = {"Semih Aslan paylaşımını beğendi","Müşerref İpek paylaşımını beğendi","Doğan Topdaş paylaşımını beğendi","Ümit Bekir H. paylaşımını beğendi"};
    String NotificationArrayTime[] = {"12 dk önce","14 dk önce","1 saat önce","5 saat önce"};

    // MY PROFILE
    String ProfileArrayName[] = {"Semih Aslan"};
    String ProfileArrayImage[] = {"","","",""};

    // SEARCH
    String SearchArrayName[] = {"Ege Üniversitesi","ODTÜ","Bilkent","Yeditepe Üniversitesi"};
    String SearchArrayImage[] = {"","","",""};

    // MAIN
    String MainArrayName[] = {"Semih Aslan","Müşerref İpek","Doğan Topdaş","Ümit Bekir H."};
    String MainArrayCategory[] = {"İtiraf","Ev Arkadaşı","Ders Notları","İtiraf"};
    String MainArrayTime[] = {"11:40","10:30","09:50","11:44"};
    String MainArrayText[] = {"Lorem Ipsum, adı bilinmeyen bir matbaacının bir hurufat numune kitabı oluşturmak üzere bir yazı galerisini alarak karıştırdığı 1500'lerden beri endüstri standardı sahte metinler olarak kullanılmıştır.","Lorem Ipsum, adı bilinmeyen bir matbaacının bir hurufat numune kitabı oluşturmak üzere bir yazı galerisini alarak karıştırdığı 1500'lerden beri endüstri standardı sahte metinler olarak kullanılmıştır.","Lorem Ipsum, adı bilinmeyen bir matbaacının bir hurufat numune kitabı oluşturmak üzere bir yazı galerisini alarak karıştırdığı 1500'lerden beri endüstri standardı sahte metinler olarak kullanılmıştır.","Lorem Ipsum, adı bilinmeyen bir matbaacının bir hurufat numune kitabı oluşturmak üzere bir yazı galerisini alarak karıştırdığı 1500'lerden beri endüstri standardı sahte metinler olarak kullanılmıştır."};
    String MainArrayImage[] = {"","","",""};
    String MainArrayProfile[] = {"","","",""};
    String MainArrayLike[] = {"11","0","1.432","3"};
    String MainArrayHashTag[] = {"#EgeÜniversitesi","EgeÜniversitesi","EgeÜniversitesi","EgeÜniversitesi"};

    private ListView main_list;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_screen);

        main_list = findViewById(R.id.main_list);

        ImageButton btnHome = findViewById(R.id.main_home);
        ImageButton btnSearch = findViewById(R.id.main_search);
        ImageButton btnCategory = findViewById(R.id.main_category);
        ImageButton btnNotification = findViewById(R.id.main_notification);
        ImageButton btnProfile = findViewById(R.id.main_profile);

        main_list.setAdapter(new MainList(MainScreen.this,
                MainArrayName,
                MainArrayCategory,
                MainArrayTime,
                MainArrayText,
                MainArrayImage,
                MainArrayProfile,
                MainArrayLike,MainArrayHashTag));

        btnHome.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                main_list.setAdapter(new MainList(MainScreen.this,
                        MainArrayName,
                        MainArrayCategory,
                        MainArrayTime,
                        MainArrayText,
                        MainArrayImage,
                        MainArrayProfile,
                        MainArrayLike,MainArrayHashTag));
            }
        });

        btnNotification.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                main_list.setAdapter(new Notifications(MainScreen.this, NotificationArrayTitle, NotificationArrayTime));
            }
        });

        btnSearch.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                main_list.setAdapter(new SearchList(MainScreen.this, SearchArrayName, SearchArrayImage));
            }
        });

        btnProfile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                main_list.setAdapter(new Profile(MainScreen.this, ProfileArrayName,ProfileArrayImage));
            }
        });

    }
}
