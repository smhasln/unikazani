package com.beceriklimedya.unikazani;

import android.content.Intent;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.toolbox.Volley;
import com.beceriklimedya.unikazani.CustomAdapter.MainAdapter;
import com.beceriklimedya.unikazani.CustomAdapter.ProfileListAdapter;
import com.beceriklimedya.unikazani.JSON.MainJSON;
import com.beceriklimedya.unikazani.JSON.ProfileJSON;
import com.squareup.picasso.Picasso;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

import de.hdodenhof.circleimageview.CircleImageView;

public class Profile extends AppCompatActivity {

    private ArrayList<String> MainArrayName = new ArrayList<>();
    private ArrayList<String> MainArrayCategory = new ArrayList<>();
    private ArrayList<String> MainArrayTime = new ArrayList<>();
    private ArrayList<String> MainArrayText = new ArrayList<>();
    private ArrayList<String> MainArrayImage = new ArrayList<>();
    private ArrayList<String> MainArrayProfile = new ArrayList<>();
    private ArrayList<String> MainArrayLike = new ArrayList<>();
    private ArrayList<String> MainArrayHashTag = new ArrayList<>();
    private ArrayList<String> MainArrayId = new ArrayList<>();

    private ImageButton profileChat;
    private CircleImageView profilePhoto;
    private TextView profileShare;
    private TextView profileScore;
    private TextView profileAge;
    private TextView profileUni;
    private TextView profileName;
    private ImageButton profileSettings;
    private ImageButton profileBack;
    private ImageButton profileAdmin;

    private String comId;
    private String userId;
    private ListView profileList;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);

        SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(getApplicationContext());
        userId = preferences.getString("userId", "N/A");

        Intent go = getIntent();
        comId = go.getStringExtra("feedUserId");

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
        profileAdmin = findViewById(R.id.profile_admin);

        profileSettings.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(Profile.this,ProfileSettings.class));
                overridePendingTransition(R.anim.ileri1,R.anim.ileri2);
            }
        });

        profileBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Profile.super.onBackPressed();
                overridePendingTransition(R.anim.geri1,R.anim.geri2);
            }
        });

        profileAdmin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(Profile.this,Admin.class));
            }
        });

        profileChat.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(Profile.this,Chat.class));
            }
        });

        fill();

    }

    private void fill()
    {

        Response.Listener<String> responselistener = new Response.Listener<String>()
        {
            @Override
            public void onResponse(String response) {
                try {

                    JSONObject jsonresponse = new JSONObject(response);

                    Log.i("yaz",response);
                    String warning = jsonresponse.getString("warning");

                    // BAŞKASI
                    if (warning.equals("1"))
                    {
                        profileSettings.setVisibility(View.GONE);
                        profileChat.setVisibility(View.VISIBLE);
                    }
                    else
                    {
                        profileSettings.setVisibility(View.VISIBLE);
                        profileChat.setVisibility(View.GONE);
                    }

                    JSONArray name = jsonresponse.getJSONArray("name");
                    JSONArray category = jsonresponse.getJSONArray("category");
                    JSONArray time = jsonresponse.getJSONArray("time");
                    JSONArray text = jsonresponse.getJSONArray("text");
                    JSONArray image = jsonresponse.getJSONArray("image");
                    JSONArray profile = jsonresponse.getJSONArray("profile");
                    JSONArray likes = jsonresponse.getJSONArray("likes");
                    //JSONArray university = jsonresponse.getJSONArray("university");
                    //JSONArray id = jsonresponse.getJSONArray("id");

                    String user_age = jsonresponse.getString("birthday");
                    String user_name = jsonresponse.getString("user_name");
                    String user_uni = jsonresponse.getString("user_university");
                    String user_score = jsonresponse.getString("score");
                    String user_share = jsonresponse.getString("share_rows");
                    String user_photo = jsonresponse.getString("photo");

                    profileAge.setText(user_age);
                    profileName.setText(user_name);
                    profileUni.setText(user_uni);
                    profileScore.setText(user_score + " puan");
                    profileShare.setText(user_share + " paylaşım");

                    Picasso.get()
                            .load("http://www.unikazani.com/json/upload/" + user_photo + ".jpg")
                            .into(profilePhoto);

                    for (int i = 0; i < profile.length(); i++){
                        MainArrayName.add(i, name.get(i).toString());
                        MainArrayCategory.add(i, category.get(i).toString());
                        MainArrayTime.add(i, time.get(i).toString());
                        MainArrayText.add(i, text.get(i).toString());
                        MainArrayImage.add(i, image.get(i).toString());
                        MainArrayProfile.add(i, profile.get(i).toString());
                        MainArrayLike.add(i, likes.get(i).toString());
                        MainArrayHashTag.add(i, "ERROR");
                        MainArrayId.add(i, "ERROR");
                    }

                    profileList.setAdapter(new ProfileListAdapter(Profile.this,
                            MainArrayName,
                            MainArrayCategory,
                            MainArrayTime,
                            MainArrayText,
                            MainArrayImage,
                            MainArrayProfile,
                            MainArrayLike,MainArrayHashTag));
                }
                catch (JSONException e)
                {
                    e.printStackTrace();
                }
            }

        };

        ProfileJSON loginrequest = new ProfileJSON(userId,comId,responselistener);
        RequestQueue queue = Volley.newRequestQueue(Profile.this);
        queue.add(loginrequest);
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        overridePendingTransition(R.anim.geri1,R.anim.geri2);
    }
}
