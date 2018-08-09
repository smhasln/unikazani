package com.beceriklimedya.unikazani;

import android.content.Intent;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;

import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.toolbox.Volley;
import com.beceriklimedya.unikazani.CustomAdapter.SearchAdapter;
import com.beceriklimedya.unikazani.CustomAdapter.Top10Adapter;
import com.beceriklimedya.unikazani.JSON.SearchJSON;
import com.beceriklimedya.unikazani.JSON.Top10JSON;
import com.kaopiz.kprogresshud.KProgressHUD;
import com.squareup.picasso.Picasso;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

import de.hdodenhof.circleimageview.CircleImageView;

public class Top10 extends AppCompatActivity {

    private ArrayList<String> names = new ArrayList<>();
    private ArrayList<String> profiles = new ArrayList<>();
    private ArrayList<String> ranks = new ArrayList<>();
    private ArrayList<String> scores = new ArrayList<>();

    private String userId;
    private ListView rankList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_top10);

        SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(getApplicationContext());
        userId = preferences.getString("userId", "N/A");

        rankList = findViewById(R.id.top10list);

        Button kadro = findViewById(R.id.btnkadro);

        kadro.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(Top10.this,PersonelList.class));
            }
        });

        fill();
    }

    private void fill()
    {
        final KProgressHUD hud = KProgressHUD.create(this)
                .setStyle(KProgressHUD.Style.SPIN_INDETERMINATE)
                .setCancellable(false)
                .setLabel("Yükleniyor")
                .setDetailsLabel("Liste getiriliyor...");

        hud.show();

        Response.Listener<String> responselistener = new Response.Listener<String>()
        {
            @Override
            public void onResponse(String response) {
                try {

                    JSONObject jsonresponse = new JSONObject(response);

                    JSONArray name = jsonresponse.getJSONArray("user_name");
                    JSONArray score = jsonresponse.getJSONArray("score");
                    JSONArray profile = jsonresponse.getJSONArray("profile");

                    for (int i = 0; i < name.length(); i++)
                    {
                        names.add(i, name.get(i).toString());
                        scores.add(i, score.get(i).toString());
                        ranks.add(i, String.valueOf(i + 1));
                        profiles.add(i, profile.get(i).toString());
                    }

                    rankList.setAdapter(new Top10Adapter(Top10.this, names,profiles,ranks,scores));

                    hud.dismiss();
                }
                catch (JSONException e)
                {
                    e.printStackTrace();
                }
            }

        };

        Top10JSON loginrequest = new Top10JSON(userId,responselistener);
        RequestQueue queue = Volley.newRequestQueue(Top10.this);
        queue.add(loginrequest);
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        overridePendingTransition(R.anim.geri1,R.anim.geri2);
    }
}
