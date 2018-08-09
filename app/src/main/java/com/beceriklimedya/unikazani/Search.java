package com.beceriklimedya.unikazani;

import android.content.Intent;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ListView;

import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.toolbox.Volley;
import com.beceriklimedya.unikazani.CustomAdapter.SearchAdapter;
import com.beceriklimedya.unikazani.JSON.SearchJSON;
import com.kaopiz.kprogresshud.KProgressHUD;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

public class Search extends AppCompatActivity{

    public static String data;

    private ArrayList<String> SearchUniName = new ArrayList<>();
    private ArrayList<String> SearchUniId = new ArrayList<>();
    private ArrayList<String> SearchUniStatus = new ArrayList<>();
    private ArrayList<String> SearchUniCounts = new ArrayList<>();

    private ListView searchList;
    private ImageButton searchBack;

    private SearchAdapter searchAdapter;
    private String userId;

    private EditText search;

    private String aranan = "99";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search);

        SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(getApplicationContext());
        userId = preferences.getString("userId", "N/A");

        search = findViewById(R.id.txt_search);
        searchBack = findViewById(R.id.search_back);
        searchList = findViewById(R.id.search_list);

        fill();

        searchList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                String sec = SearchUniId.get(position);
                String sec2 = SearchUniStatus.get(position);
                String sec3 = SearchUniName.get(position);

                Intent follow = new Intent(Search.this,UniversityProfile.class);
                follow.putExtra("searchId",sec);
                follow.putExtra("searchStatus",sec2);
                follow.putExtra("searchName",sec3);
                startActivity(follow);

            }
        });

        searchBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Search.super.onBackPressed();

                overridePendingTransition(R.anim.geri1,R.anim.geri2);
            }
        });

        search.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if (search.getText().toString().trim().length() == 0)
                {
                    aranan = "99";
                    fill();
                }else {

                    aranan = search.getText().toString();
                    fill();
                }

            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });
    }

    @Override
    public void onResume()
    {  // After a pause OR at startup
        super.onResume();
        fill();

    }

    private void fill()
    {

        SearchUniName.clear();
        SearchUniId.clear();
        SearchUniStatus.clear();

        Response.Listener<String> responselistener = new Response.Listener<String>()
        {
            @Override
            public void onResponse(String response) {
                try {

                    JSONObject jsonresponse = new JSONObject(response);

                    JSONArray university_id = jsonresponse.getJSONArray("university_id");
                    JSONArray university_name = jsonresponse.getJSONArray("university_name");
                    JSONArray status = jsonresponse.getJSONArray("status");
                    JSONArray counts = jsonresponse.getJSONArray("share_counts");

                    for (int i = 0; i < university_id.length(); i++){
                        SearchUniId.add(i, university_id.get(i).toString());
                        SearchUniName.add(i, university_name.get(i).toString());
                        SearchUniStatus.add(i, status.get(i).toString());
                        SearchUniCounts.add(i, counts.get(i).toString());
                    }

                    searchAdapter = new SearchAdapter(Search.this, SearchUniName, SearchUniCounts);
                    searchList.setAdapter(searchAdapter);

                    searchAdapter.notifyDataSetChanged();

                }
                catch (JSONException e)
                {
                    e.printStackTrace();
                }
            }

        };

        SearchJSON loginrequest = new SearchJSON(userId,"10",aranan,responselistener);
        RequestQueue queue = Volley.newRequestQueue(Search.this);
        queue.add(loginrequest);
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        overridePendingTransition(R.anim.geri1,R.anim.geri2);
    }


}
