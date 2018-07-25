package com.beceriklimedya.unikazani;

import android.app.Notification;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;
import android.widget.ListView;

import com.beceriklimedya.unikazani.CustomAdapter.NotificationAdapter;

import java.util.ArrayList;

public class Notifications extends AppCompatActivity {

    private ArrayList<String> NotificationArrayTitle = new ArrayList<>();
    private ArrayList<String> NotificationArrayTime = new ArrayList<>();

    private ImageButton notificationBack;
    private ListView notificationList;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_notifications);

        notificationBack = findViewById(R.id.notification_back);
        notificationList = findViewById(R.id.notification_list);

        fill();

        notificationBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Notifications.super.onBackPressed();
                overridePendingTransition(R.anim.geri1,R.anim.geri2);
            }
        });
    }

    private void fill()
    {
        NotificationArrayTime.add("10 dakika önce");
        NotificationArrayTime.add("10 dakika önce");
        NotificationArrayTime.add("10 dakika önce");
        NotificationArrayTime.add("10 dakika önce");

        NotificationArrayTitle.add("Ahmet bir yorum bıraktı!");
        NotificationArrayTitle.add("Mehmet paylaşımını beğendi!");
        NotificationArrayTitle.add("Ahmet bir yorum bıraktı!");
        NotificationArrayTitle.add("Mehmet paylaşımını beğendi!");

        notificationList.setAdapter(new NotificationAdapter(Notifications.this, NotificationArrayTitle, NotificationArrayTime));
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        overridePendingTransition(R.anim.geri1,R.anim.geri2);
    }
}
