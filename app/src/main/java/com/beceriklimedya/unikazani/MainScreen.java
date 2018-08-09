package com.beceriklimedya.unikazani;

import android.app.Dialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.os.Handler;
import android.preference.PreferenceManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.toolbox.Volley;
import com.beceriklimedya.unikazani.CustomAdapter.MainAdapter;
import com.beceriklimedya.unikazani.JSON.LikeJSON;
import com.beceriklimedya.unikazani.JSON.LoginJSON;
import com.beceriklimedya.unikazani.JSON.MainJSON;
import com.kaopiz.kprogresshud.KProgressHUD;
import com.varunest.sparkbutton.SparkButton;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;


public class MainScreen extends AppCompatActivity {

    MainAdapter mainAdapter;

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

    private ImageButton btnAll;
    private ImageButton btnAdd;
    private ImageButton btnSearch;
    private ImageButton btnNotification;
    private ImageButton btnProfile;
    private ImageButton btnTop10;

    private ListView main_list;

    private Dialog myDialog;

    private Boolean remember;
    private String username;
    private String password;
    private String userId;

    private String token;
    private String profil;

    private String shareId;
    private String data = "99";

    private String feedId;
    int counter = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_screen);

        SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(getApplicationContext());
        String remember = preferences.getString("remember", "0");

        SharedPreferences token_at = PreferenceManager.getDefaultSharedPreferences(getApplicationContext());
        token = token_at.getString("token", "N/A");

        if (remember.equals("1"))
        {
            userId = preferences.getString("userId", "N/A");
            username = preferences.getString("username", "N/A");
            password = preferences.getString("password", "N/A");

            login();
            fill();
        }
        else
        {
            startActivity(new Intent(MainScreen.this, SplashScreen.class));
        }

        myDialog = new Dialog(this);
        main_list = findViewById(R.id.mainlist);

        btnAdd = findViewById(R.id.main_add);
        btnSearch = findViewById(R.id.main_search);
        btnNotification = findViewById(R.id.main_notification);
        btnProfile = findViewById(R.id.main_profile);
        btnTop10 = findViewById(R.id.maintop10);

        final TextView btn_filter1 = findViewById(R.id.filter1);
        final TextView btn_filter2 = findViewById(R.id.filter2);
        final TextView btn_filter3 = findViewById(R.id.filter3);
        final TextView btn_filter4 = findViewById(R.id.filter4);
        final ImageView btnAll = findViewById(R.id.main_all);

        btnAll.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                data = "99";

                btn_filter1.setTextColor(Color.LTGRAY);
                btn_filter2.setTextColor(Color.LTGRAY);
                btn_filter3.setTextColor(Color.LTGRAY);
                btn_filter4.setTextColor(Color.LTGRAY);

                fill();
            }
        });

        btn_filter1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                data = "İtiraf";

                btn_filter1.setTextColor(Color.WHITE);

                btn_filter2.setTextColor(Color.LTGRAY);
                btn_filter3.setTextColor(Color.LTGRAY);
                btn_filter4.setTextColor(Color.LTGRAY);

                fill();
            }
        });
        btn_filter2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                data = "Ev Arkadaşı";

                btn_filter2.setTextColor(Color.WHITE);

                btn_filter1.setTextColor(Color.LTGRAY);
                btn_filter3.setTextColor(Color.LTGRAY);
                btn_filter4.setTextColor(Color.LTGRAY);

                fill();
            }
        });
        btn_filter3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                data = "Ders Notları";

                btn_filter3.setTextColor(Color.WHITE);

                btn_filter1.setTextColor(Color.LTGRAY);
                btn_filter2.setTextColor(Color.LTGRAY);
                btn_filter4.setTextColor(Color.LTGRAY);

                fill();
            }
        });
        btn_filter4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                data = "Etkinlik";

                btn_filter4.setTextColor(Color.WHITE);

                btn_filter1.setTextColor(Color.LTGRAY);
                btn_filter2.setTextColor(Color.LTGRAY);
                btn_filter3.setTextColor(Color.LTGRAY);

                fill();
            }
        });

        main_list.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, final
            int position, long id) {

                Intent git = new Intent(MainScreen.this,Comment.class);
                git.putExtra("feedId",MainArrayId.get(position));
                git.putExtra("feedPhoto",MainArrayProfile.get(position));
                git.putExtra("feedName",MainArrayName.get(position));
                git.putExtra("feedText",MainArrayText.get(position));
                git.putExtra("feedUserId",MainArrayUserId.get(position));
                startActivity(git);
            }
        });

       main_list.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
           @Override
           public boolean onItemLongClick(AdapterView<?> parent, View view, int position, long id) {


               feedId = MainArrayId.get(position);
               like_apply(position);

               return true;
           };
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
                Intent go = new Intent(MainScreen.this,Profile.class);
                go.putExtra("feedUserId", userId);
                startActivity(go);
                overridePendingTransition(R.anim.ileri1,R.anim.ileri2);

            }
        });

        btnTop10.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(MainScreen.this,Top10.class));
                overridePendingTransition(R.anim.ileri1,R.anim.ileri2);
            }
        });


    }

    private void like_apply(final Integer row)
    {

        final KProgressHUD hud = KProgressHUD.create(this)
                .setStyle(KProgressHUD.Style.SPIN_INDETERMINATE)
                .setCancellable(false)
                .setLabel("Beğendin!");

        hud.show();

        Response.Listener<String> responselistener = new Response.Listener<String>()
        {
            @Override
            public void onResponse(String response) {
                try {
                    hud.dismiss();
                    JSONObject jsonresponse = new JSONObject(response);


                }
                catch (JSONException e)
                {
                    e.printStackTrace();
                }
            }

        };

        LikeJSON loginrequest = new LikeJSON(userId,feedId,responselistener);
        RequestQueue queue = Volley.newRequestQueue(MainScreen.this);
        queue.add(loginrequest);
    }


    private void login()
    {

        Response.Listener<String> responselistener = new Response.Listener<String>()
        {
            @Override
            public void onResponse(String response) {
                try {

                    JSONObject jsonresponse = new JSONObject(response);
                    Integer error = jsonresponse.getInt("error");

                    if (error == 0) {

                        String userId = jsonresponse.getString("id");
                        String auth = jsonresponse.getString("auth");
                        String profile = jsonresponse.getString("profile");

                        SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(getApplicationContext());
                        SharedPreferences.Editor editor = preferences.edit();
                        editor.putString("auth", auth);
                        editor.putString("profile", profile);

                        editor.commit();


                    }

                }
                catch (JSONException e)
                {
                    e.printStackTrace();
                }
            }

        };

        LoginJSON loginrequest = new LoginJSON(username,password,token,responselistener);
        RequestQueue queue = Volley.newRequestQueue(MainScreen.this);
        queue.add(loginrequest);
    }

    private void fill()
    {

        final KProgressHUD hud = KProgressHUD.create(this)
                .setStyle(KProgressHUD.Style.SPIN_INDETERMINATE)
                .setCancellable(false)
                .setLabel("Yükleniyor")
                .setDetailsLabel("Paylaşımlar getiriliyor");

        hud.show();
        MainArrayName.clear();
        MainArrayCategory.clear();
        MainArrayTime.clear();
        MainArrayText.clear();;
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

                    mainAdapter = new MainAdapter(MainScreen.this,
                            MainArrayName,
                            MainArrayCategory,
                            MainArrayTime,
                            MainArrayText,
                            MainArrayImage,
                            MainArrayProfile,
                            MainArrayLike,MainArrayHashTag);

                    main_list.setAdapter(mainAdapter);

                    hud.dismiss();
                }
                catch (JSONException e)
                {
                    e.printStackTrace();
                }
            }

        };

        MainJSON loginrequest = new MainJSON(userId,data,responselistener);
        RequestQueue queue = Volley.newRequestQueue(MainScreen.this);
        queue.add(loginrequest);

    }
}
