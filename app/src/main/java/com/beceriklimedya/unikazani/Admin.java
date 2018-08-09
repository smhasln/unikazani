package com.beceriklimedya.unikazani;

import android.content.DialogInterface;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.preference.PreferenceManager;
import android.support.annotation.NonNull;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.Toast;

import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.toolbox.Volley;
import com.beceriklimedya.unikazani.CustomAdapter.AdminListAdapter;
import com.beceriklimedya.unikazani.CustomAdapter.SearchAdapter;
import com.beceriklimedya.unikazani.CustomAdapter.Top10Adapter;
import com.beceriklimedya.unikazani.JSON.AdminApplyJSON;
import com.beceriklimedya.unikazani.JSON.AdminJSON;
import com.beceriklimedya.unikazani.JSON.SearchJSON;
import com.beceriklimedya.unikazani.JSON.Top10JSON;
import com.kaopiz.kprogresshud.KProgressHUD;
import com.mlsdev.rximagepicker.RxImagePicker;
import com.mlsdev.rximagepicker.Sources;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.InputStream;
import java.util.ArrayList;

import io.reactivex.functions.Consumer;

public class Admin extends AppCompatActivity {

    private ArrayList<String> userName = new ArrayList<>();
    private ArrayList<String> userIds = new ArrayList<>();
    private ArrayList<String> userImage = new ArrayList<>();
    private ArrayList<String> userAuth = new ArrayList<>();

    private String selectedId;
    private FloatingActionButton apply;
    private ListView listView;

    private String userId;

    private String send;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin);

        SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(getApplicationContext());
        userId = preferences.getString("userId", "N/A");

        listView = findViewById(R.id.admin_list);

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                selectedId = userIds.get(position);

                AlertDialog.Builder builder1 = new AlertDialog.Builder(Admin.this);
                builder1.setTitle("Yetki tipi");
                builder1.setCancelable(true);
                builder1.setNegativeButton("Editör yap",
                        new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int id) {
                                send = "1";
                                adminApply();
                            }
                        });
                builder1.setPositiveButton("Kullanıcı yap",
                        new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int id) {
                                send = "0";
                                adminApply();
                            }
                        });
                AlertDialog alert11 = builder1.create();
                alert11.show();
            }
        });

        getUser();
    }


    private void getUser()
    {
        final KProgressHUD hud = KProgressHUD.create(this)
                .setStyle(KProgressHUD.Style.SPIN_INDETERMINATE)
                .setCancellable(false)
                .setLabel("Yükleniyor")
                .setDetailsLabel("Kişiler getiriliyor...");

        hud.show();

        userName.clear();
        userIds.clear();
        userImage.clear();
        userAuth.clear();

        Response.Listener<String> responselistener = new Response.Listener<String>()
        {
            @Override
            public void onResponse(String response) {
                try {

                    JSONObject jsonresponse = new JSONObject(response);

                    JSONArray name = jsonresponse.getJSONArray("name");
                    JSONArray id = jsonresponse.getJSONArray("id");
                    JSONArray profile = jsonresponse.getJSONArray("profile");
                    JSONArray auth = jsonresponse.getJSONArray("auth");

                    for (int i = 0; i < name.length(); i++)
                    {
                        userName.add(i, name.get(i).toString());
                        userIds.add(i, id.get(i).toString());
                        userImage.add(i, profile.get(i).toString());
                        userAuth.add(i, auth.get(i).toString());
                    }

                    listView.setAdapter(new AdminListAdapter(Admin.this, userName,userImage,userAuth));

                    hud.dismiss();
                }
                catch (JSONException e)
                {
                    e.printStackTrace();
                }
            }

        };

        AdminJSON loginrequest = new AdminJSON(responselistener);
        RequestQueue queue = Volley.newRequestQueue(Admin.this);
        queue.add(loginrequest);
    }

    private void adminApply()
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
                        getUser();
                    }

                }
                catch (JSONException e)
                {
                    e.printStackTrace();
                }
            }

        };

        AdminApplyJSON loginrequest = new AdminApplyJSON(selectedId,send,responselistener);
        RequestQueue queue = Volley.newRequestQueue(Admin.this);
        queue.add(loginrequest);
    }

}
