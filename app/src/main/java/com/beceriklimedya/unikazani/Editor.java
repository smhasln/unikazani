package com.beceriklimedya.unikazani;

import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;

import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.toolbox.Volley;
import com.beceriklimedya.unikazani.CustomAdapter.CheckListAdapter;
import com.beceriklimedya.unikazani.CustomAdapter.MainAdapter;
import com.beceriklimedya.unikazani.JSON.CheckJSON;
import com.beceriklimedya.unikazani.JSON.CheckListJSON;
import com.beceriklimedya.unikazani.JSON.MainJSON;
import com.kaopiz.kprogresshud.KProgressHUD;
import com.tapadoo.alerter.Alerter;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

public class Editor extends AppCompatActivity {

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

    private String userId;
    private ListView listView;

    private String shareId;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_editor);

        SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(getApplicationContext());
        userId = preferences.getString("userId", "N/A");

        listView = findViewById(R.id.editor_list);

        fill();

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                shareId = MainArrayId.get(position);

                AlertDialog.Builder builder1 = new AlertDialog.Builder(Editor.this);
                builder1.setTitle("Editör");
                builder1.setMessage("Paylaşım Ayarları");
                builder1.setCancelable(true);
                builder1.setPositiveButton("Yayına al",
                        new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int id) {
                                apply();
                            }
                        });
                builder1.setNegativeButton("Vazgeç",
                        new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int id) {


                            }
                        });
                AlertDialog alert11 = builder1.create();
                alert11.show();
            }
        });

    }

    private void apply()
    {
        final KProgressHUD hud = KProgressHUD.create(this)
                .setStyle(KProgressHUD.Style.SPIN_INDETERMINATE)
                .setCancellable(false)
                .setLabel("İşleniyor");

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

                        Alerter.create(Editor.this)
                                .setBackgroundColorRes(android.R.color.holo_blue_dark)
                                .setTitle("Bilgi")
                                .setText("Paylaşım yayında!")
                                .enableProgress(true)
                                .setProgressColorRes(android.R.color.white)
                                .show();

                        fill();
                    }
                }
                catch (JSONException e)
                {
                    e.printStackTrace();
                }
            }

        };

        CheckJSON loginrequest = new CheckJSON(userId,shareId,responselistener);
        RequestQueue queue = Volley.newRequestQueue(Editor.this);
        queue.add(loginrequest);

    }

    private void fill()
    {

        final KProgressHUD hud = KProgressHUD.create(this)
                .setStyle(KProgressHUD.Style.SPIN_INDETERMINATE)
                .setCancellable(false)
                .setLabel("Yükleniyor")
                .setDetailsLabel("Onay bekleyenler getiriliyor...");

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

                    listView.setAdapter(new CheckListAdapter(Editor.this,
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

        CheckListJSON loginrequest = new CheckListJSON(userId,responselistener);
        RequestQueue queue = Volley.newRequestQueue(Editor.this);
        queue.add(loginrequest);

    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        overridePendingTransition(R.anim.geri1,R.anim.geri2);
    }
}
