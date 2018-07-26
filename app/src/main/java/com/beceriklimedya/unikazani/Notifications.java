package com.beceriklimedya.unikazani;

import android.app.Notification;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.ImageButton;
import android.widget.ListView;

import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.toolbox.Volley;
import com.beceriklimedya.unikazani.CustomAdapter.NotificationAdapter;
import com.beceriklimedya.unikazani.JSON.NotificationJSON;
import com.beceriklimedya.unikazani.JSON.SearchJSON;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

public class Notifications extends AppCompatActivity {

    private ArrayList<String> NotificationArrayTitle = new ArrayList<>();
    private ArrayList<String> NotificationArrayTime = new ArrayList<>();

    private ImageButton notificationBack;
    private ListView notificationList;

    private String userId;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_notifications);

        SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(getApplicationContext());
        userId = preferences.getString("userId", "N/A");

        notificationBack = findViewById(R.id.notification_back);
        notificationList = findViewById(R.id.notification_list);

        getNoti();

        notificationBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Notifications.super.onBackPressed();
                overridePendingTransition(R.anim.geri1,R.anim.geri2);
            }
        });
    }

    private void getNoti()
    {
        Response.Listener<String> responselistener = new Response.Listener<String>()
        {
            @Override
            public void onResponse(String response) {
                try {

                    JSONObject jsonresponse = new JSONObject(response);

                    JSONArray title = jsonresponse.getJSONArray("noti_title");
                    JSONArray time = jsonresponse.getJSONArray("noti_date");

                    for (int i = 0; i < title.length(); i++){
                        NotificationArrayTime.add(i, time.get(i).toString());
                        NotificationArrayTitle.add(i, title.get(i).toString());
                    }

                    notificationList.setAdapter(new NotificationAdapter(Notifications.this, NotificationArrayTitle, NotificationArrayTime));

                }
                catch (JSONException e)
                {
                    e.printStackTrace();
                }
            }

        };

        NotificationJSON loginrequest = new NotificationJSON(userId,responselistener);
        RequestQueue queue = Volley.newRequestQueue(Notifications.this);
        queue.add(loginrequest);
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        overridePendingTransition(R.anim.geri1,R.anim.geri2);
    }
}
