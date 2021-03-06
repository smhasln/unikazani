package com.beceriklimedya.unikazani;

import android.app.ProgressDialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.toolbox.Volley;
import com.beceriklimedya.unikazani.CustomAdapter.MainAdapter;
import com.beceriklimedya.unikazani.CustomAdapter.UniversityListAdapter;
import com.beceriklimedya.unikazani.JSON.MainJSON;
import com.beceriklimedya.unikazani.JSON.SearchActionJSON;
import com.beceriklimedya.unikazani.JSON.UniShareJSON;
import com.kaopiz.kprogresshud.KProgressHUD;
import com.tapadoo.alerter.Alerter;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

public class UniversityProfile extends AppCompatActivity {

    private ArrayList<String> MainArrayName = new ArrayList<>();
    private ArrayList<String> MainArrayCategory = new ArrayList<>();
    private ArrayList<String> MainArrayTime = new ArrayList<>();
    private ArrayList<String> MainArrayText = new ArrayList<>();
    private ArrayList<String> MainArrayImage = new ArrayList<>();
    private ArrayList<String> MainArrayProfile = new ArrayList<>();
    private ArrayList<String> MainArrayLike = new ArrayList<>();
    private ArrayList<String> MainArrayHashTag = new ArrayList<>();
    private ArrayList<String> MainArrayId = new ArrayList<>();
    private ArrayList<String> MainArrayUserId = new ArrayList<>();

    private FloatingActionButton followBtn;
    private ListView shareList;

    private String userId;
    private String uni_id;
    private TextView count;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_university_profile);

        SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(getApplicationContext());
        userId = preferences.getString("userId", "N/A");

        Intent follow = getIntent();
        uni_id = follow.getStringExtra("searchId");
        final String searchStatus = follow.getStringExtra("searchStatus");
        final String searchName = follow.getStringExtra("searchName");

        Log.i("yaz",uni_id);
        TextView title = findViewById(R.id.txtunititle);

        title.setText(searchName);

        shareList = findViewById(R.id.uni_share_list);
        followBtn = findViewById(R.id.uni_follow);
        count = findViewById(R.id.txtcount);

        ImageButton back = findViewById(R.id.uniprofileback);

        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                UniversityProfile.super.onBackPressed();
            }
        });

        if (searchStatus.equals("1"))
        {
            fill();
        }
        else if (searchStatus.equals("0"))
        {
            Alerter.create(UniversityProfile.this)
                    .setBackgroundColorRes(android.R.color.holo_blue_dark)
                    .setTitle("Bilgi")
                    .setText("Gönderileri görmek için takibe al!")
                    .enableProgress(true)
                    .setProgressColorRes(android.R.color.white)
                    .show();
        }

        followBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                action(uni_id, userId);
            }
        });
    }


    public void action (String uniId, String userId)
    {
        final KProgressHUD hud = KProgressHUD.create(this)
                .setStyle(KProgressHUD.Style.SPIN_INDETERMINATE)
                .setCancellable(false)
                .setLabel("Yükleniyor")
                .setDetailsLabel("İşlem yapılıyor...");

        hud.show();

        Response.Listener<String> responselistener = new Response.Listener<String>()
        {
            @Override
            public void onResponse(String response) {
                try {

                    JSONObject jsonresponse = new JSONObject(response);

                    Integer error = jsonresponse.getInt("error");

                    hud.dismiss();
                    if (error == 0)
                    {

                        shareList.setVisibility(View.VISIBLE);
                        fill();
                    }
                    else if (error == 1)
                    {
                        MainArrayName.clear();
                        MainArrayTime.clear();
                        MainArrayText.clear();
                        MainArrayImage.clear();
                        MainArrayProfile.clear();
                        MainArrayLike.clear();
                        MainArrayHashTag.clear();
                        MainArrayId.clear();
                        MainArrayUserId.clear();

                        shareList.setVisibility(View.GONE);

                        Alerter.create(UniversityProfile.this)
                                .setBackgroundColorRes(android.R.color.holo_blue_dark)
                                .setTitle("Bilgi")
                                .setText("Takipten çıkıldı!")
                                .enableProgress(true)
                                .setProgressColorRes(android.R.color.white)
                                .show();
                    }

                }
                catch (JSONException e)
                {
                    e.printStackTrace();
                }
            }

        };

        SearchActionJSON loginrequest = new SearchActionJSON(userId,uniId,responselistener);
        RequestQueue queue = Volley.newRequestQueue(UniversityProfile.this);
        queue.add(loginrequest);


    }


    private void fill()
    {
        final KProgressHUD hud = KProgressHUD.create(this)
                .setStyle(KProgressHUD.Style.SPIN_INDETERMINATE)
                .setCancellable(false)
                .setLabel("Yükleniyor")
                .setDetailsLabel("Bilgiler getiriliyor...");

        hud.show();

        MainArrayName.clear();
        MainArrayTime.clear();
        MainArrayText.clear();
        MainArrayImage.clear();
        MainArrayProfile.clear();
        MainArrayLike.clear();
        MainArrayHashTag.clear();
        MainArrayId.clear();
        MainArrayUserId.clear();


        Response.Listener<String> responselistener = new Response.Listener<String>()
        {
            @Override
            public void onResponse(String response) {
                try {

                    JSONObject jsonresponse = new JSONObject(response);


                    JSONArray name = jsonresponse.getJSONArray("name");
                    JSONArray category = jsonresponse.getJSONArray("category");
                    JSONArray time = jsonresponse.getJSONArray("time");
                    JSONArray text = jsonresponse.getJSONArray("text");
                    JSONArray image = jsonresponse.getJSONArray("image");
                    JSONArray profile = jsonresponse.getJSONArray("profile");
                    JSONArray likes = jsonresponse.getJSONArray("likes");
                    JSONArray university = jsonresponse.getJSONArray("university");
                    JSONArray id = jsonresponse.getJSONArray("id");
                    JSONArray userid = jsonresponse.getJSONArray("user_id");

                    for (int i = 0; i < name.length(); i++){
                        MainArrayName.add(i, name.get(i).toString());
                        MainArrayCategory.add(i, category.get(i).toString());
                        MainArrayTime.add(i, time.get(i).toString());
                        MainArrayText.add(i, text.get(i).toString());
                        MainArrayImage.add(i, image.get(i).toString());
                        MainArrayProfile.add(i, profile.get(i).toString());
                        MainArrayLike.add(i, likes.get(i).toString());
                        MainArrayHashTag.add(i, university.get(i).toString());
                        MainArrayId.add(i, id.get(i).toString());
                        MainArrayUserId.add(i,userid.get(i).toString());
                    }

                    count.setText(MainArrayId.size() + " PAYLAŞIM");
                    shareList.setAdapter(new UniversityListAdapter(UniversityProfile.this,
                            MainArrayName,
                            MainArrayCategory,
                            MainArrayTime,
                            MainArrayText,
                            MainArrayImage,
                            MainArrayProfile,
                            MainArrayLike,MainArrayHashTag));

                    hud.dismiss();
                }
                catch (JSONException e)
                {
                    e.printStackTrace();
                }
            }

        };

        UniShareJSON loginrequest = new UniShareJSON(uni_id,responselistener);
        RequestQueue queue = Volley.newRequestQueue(UniversityProfile.this);
        queue.add(loginrequest);

    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
    }
}
