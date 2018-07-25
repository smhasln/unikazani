package com.beceriklimedya.unikazani;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;
import android.widget.ListView;

import com.beceriklimedya.unikazani.CustomAdapter.SearchAdapter;

import java.util.ArrayList;

public class Search extends AppCompatActivity {

    private ArrayList<String> SearchUniName = new ArrayList<>();
    private ArrayList<String> SearchUniId = new ArrayList<>();

    private ListView searchList;
    private ImageButton searchBack;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search);

        searchBack = findViewById(R.id.search_back);
        searchList = findViewById(R.id.search_list);

        fill();

        searchBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Search.super.onBackPressed();
                overridePendingTransition(R.anim.geri1,R.anim.geri2);
            }
        });
    }

    void fill()
    {
        SearchUniName.add("Ege Üniversitesi");
        SearchUniName.add("ODTÜ Üniversitesi");
        SearchUniName.add("Bilkent Üniversitesi");
        SearchUniName.add("Eskişehir Anadolu Üniversitesi");

        SearchUniId.add("1");
        SearchUniId.add("2");
        SearchUniId.add("3");
        SearchUniId.add("4");

        searchList.setAdapter(new SearchAdapter(Search.this, SearchUniName, SearchUniId));
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        overridePendingTransition(R.anim.geri1,R.anim.geri2);
    }
}
