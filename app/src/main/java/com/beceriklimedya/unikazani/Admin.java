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
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Spinner;

import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.toolbox.Volley;
import com.beceriklimedya.unikazani.CustomAdapter.AdminListAdapter;
import com.beceriklimedya.unikazani.CustomAdapter.SearchAdapter;
import com.beceriklimedya.unikazani.CustomAdapter.Top10Adapter;
import com.beceriklimedya.unikazani.JSON.AdminJSON;
import com.beceriklimedya.unikazani.JSON.SearchJSON;
import com.beceriklimedya.unikazani.JSON.Top10JSON;
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

    private ArrayList<String> SearchUniName = new ArrayList<>();
    private ArrayList<String> SearchUniId = new ArrayList<>();
    private ArrayList<String> SearchUniStatus = new ArrayList<>();

    private String selectedUniId;
    private String selectedId;
    private FloatingActionButton apply;
    private ListView listView;

    private String userId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin);

        SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(getApplicationContext());
        userId = preferences.getString("userId", "N/A");

        listView = findViewById(R.id.admin_list);
        apply = findViewById(R.id.admin_apply);

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                selectedId = userIds.get(position);

                // Filtrele
                final AlertDialog.Builder builder = new AlertDialog.Builder(Admin.this);
                View mView = getLayoutInflater().inflate(R.layout.callspinner, null);
                builder.setTitle("Yetkilendirin");

                final Spinner spinner = mView.findViewById(R.id.spinner);
                ArrayAdapter<String> adapter = new ArrayAdapter<String>(Admin.this, android.R.layout.simple_spinner_dropdown_item, SearchUniName);
                adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                spinner.setAdapter(adapter);

                //Hangi opsiyon seçildiyse tespit edilir işlemler yapılır.
                builder.setPositiveButton("Seç", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        selectedUniId = SearchUniId.get(spinner.getSelectedItemPosition());


                        AlertDialog.Builder builder1 = new AlertDialog.Builder(Admin.this);
                        builder1.setTitle("Yetki tipi");
                        builder1.setCancelable(true);
                        builder1.setPositiveButton("Admin yap",
                                new DialogInterface.OnClickListener() {
                                    public void onClick(DialogInterface dialog, int id) {
                                    }
                                });
                        builder1.setNegativeButton("Editör yap",
                                new DialogInterface.OnClickListener() {
                                    public void onClick(DialogInterface dialog, int id) {

                                    }
                                });
                        builder1.setPositiveButton("Kullanıcı yap",
                                new DialogInterface.OnClickListener() {
                                    public void onClick(DialogInterface dialog, int id) {

                                    }
                                });
                        AlertDialog alert11 = builder1.create();
                        alert11.show();



                    }
                });
                builder.setNegativeButton("Vazgeç", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();
                    }
                });

                // Dialog penceresi oluşturulur.
                builder.setView(mView);
                AlertDialog dialog = builder.create();
                dialog.show();

            }
        });

        apply.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                adminApply();
            }
        });

        getUni();
        getUser();
    }


    private void getUser()
    {
        Response.Listener<String> responselistener = new Response.Listener<String>()
        {
            @Override
            public void onResponse(String response) {
                try {

                    JSONObject jsonresponse = new JSONObject(response);

                    JSONArray name = jsonresponse.getJSONArray("username");
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
        Response.Listener<String> responselistener = new Response.Listener<String>()
        {
            @Override
            public void onResponse(String response) {
                try {

                    JSONObject jsonresponse = new JSONObject(response);

                    Integer error = jsonresponse.getInt("error");

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

        SearchJSON loginrequest = new SearchJSON(selectedId,selectedUniId,responselistener);
        RequestQueue queue = Volley.newRequestQueue(Admin.this);
        queue.add(loginrequest);
    }

    private void getUni()
    {
        Response.Listener<String> responselistener = new Response.Listener<String>()
        {
            @Override
            public void onResponse(String response) {
                try {

                    JSONObject jsonresponse = new JSONObject(response);

                    JSONArray university_id = jsonresponse.getJSONArray("university_id");
                    JSONArray university_name = jsonresponse.getJSONArray("university_name");
                    JSONArray status = jsonresponse.getJSONArray("status");

                    for (int i = 0; i < university_id.length(); i++){
                        SearchUniId.add(i, university_id.get(i).toString());
                        SearchUniName.add(i, university_name.get(i).toString());
                        SearchUniStatus.add(i, status.get(i).toString());
                    }

                }
                catch (JSONException e)
                {
                    e.printStackTrace();
                }
            }

        };

        SearchJSON loginrequest = new SearchJSON(userId,"10",responselistener);
        RequestQueue queue = Volley.newRequestQueue(Admin.this);
        queue.add(loginrequest);
    }
}
