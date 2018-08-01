package com.beceriklimedya.unikazani;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ListView;

import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.toolbox.Volley;
import com.beceriklimedya.unikazani.CustomAdapter.PersonelAdapter;
import com.beceriklimedya.unikazani.CustomAdapter.Top10Adapter;
import com.beceriklimedya.unikazani.JSON.PersonelJSON;
import com.beceriklimedya.unikazani.JSON.Top10JSON;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

public class PersonelList extends AppCompatActivity {

    ArrayList<String> names = new ArrayList<>();
    ArrayList<String> photos = new ArrayList<>();
    ArrayList<String> auths = new ArrayList<>();
    ArrayList<String> unis = new ArrayList<>();

    private ListView listView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_personel_list);

        listView = findViewById(R.id.personel_list);

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

                    JSONArray name = jsonresponse.getJSONArray("username");
                    JSONArray photo = jsonresponse.getJSONArray("image");
                    JSONArray auth = jsonresponse.getJSONArray("auth");
                    JSONArray uni = jsonresponse.getJSONArray("university_name");

                    for (int i = 0; i < name.length(); i++)
                    {
                        names.add(i, name.get(i).toString());
                        photos.add(i, photo.get(i).toString());
                        auths.add(i, auth.get(i).toString());
                        unis.add(i, uni.get(i).toString());
                    }

                    listView.setAdapter(new PersonelAdapter(PersonelList.this, names,photos,auths,unis));
                }
                catch (JSONException e)
                {
                    e.printStackTrace();
                }
            }

        };

        PersonelJSON loginrequest = new PersonelJSON(responselistener);
        RequestQueue queue = Volley.newRequestQueue(PersonelList.this);
        queue.add(loginrequest);
    }

}
