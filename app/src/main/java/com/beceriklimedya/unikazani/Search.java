package com.beceriklimedya.unikazani;

import android.app.ProgressDialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.Toast;

import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.toolbox.Volley;
import com.beceriklimedya.unikazani.CustomAdapter.SearchAdapter;
import com.beceriklimedya.unikazani.JSON.LoginJSON;
import com.beceriklimedya.unikazani.JSON.SearchActionJSON;
import com.beceriklimedya.unikazani.JSON.SearchJSON;
import com.tapadoo.alerter.Alerter;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

public class Search extends AppCompatActivity {

    private ArrayList<String> SearchUniName = new ArrayList<>();
    private ArrayList<String> SearchUniId = new ArrayList<>();
    private ArrayList<String> SearchUniStatus = new ArrayList<>();

    private ListView searchList;
    private ImageButton searchBack;

    private SearchAdapter searchAdapter;
    private String userId;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search);

        SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(getApplicationContext());
        userId = preferences.getString("userId", "N/A");

        searchBack = findViewById(R.id.search_back);
        searchList = findViewById(R.id.search_list);

        fill();

        searchList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                action(SearchUniId.get(position), userId, SearchUniStatus.get(position));
            }
        });

        searchBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Search.super.onBackPressed();
                overridePendingTransition(R.anim.geri1,R.anim.geri2);
            }
        });
    }

    private void action(String uniId, String userId, final String status)
    {
        Response.Listener<String> responselistener = new Response.Listener<String>()
        {
            @Override
            public void onResponse(String response) {
                try {

                    JSONObject jsonresponse = new JSONObject(response);
                    Log.i("yaz",response);
                    Integer error = jsonresponse.getInt("error");

                    if (error == 0)
                    {
                        fill();
                    }

                }
                catch (JSONException e)
                {
                    e.printStackTrace();
                }
            }

        };

        SearchActionJSON loginrequest = new SearchActionJSON(userId,uniId,responselistener);
        RequestQueue queue = Volley.newRequestQueue(Search.this);
        queue.add(loginrequest);
    }

    private void fill()
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

                    searchAdapter = new SearchAdapter(Search.this, SearchUniName, SearchUniStatus);
                    searchList.setAdapter(searchAdapter);
                }
                catch (JSONException e)
                {
                    e.printStackTrace();
                }
            }

        };

        SearchJSON loginrequest = new SearchJSON(userId,responselistener);
        RequestQueue queue = Volley.newRequestQueue(Search.this);
        queue.add(loginrequest);
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        overridePendingTransition(R.anim.geri1,R.anim.geri2);
    }
}
