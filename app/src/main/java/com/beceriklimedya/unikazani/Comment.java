package com.beceriklimedya.unikazani;

import android.app.ProgressDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;

import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.toolbox.Volley;
import com.beceriklimedya.unikazani.CustomAdapter.CommentAdapter;
import com.beceriklimedya.unikazani.CustomAdapter.NotificationAdapter;
import com.beceriklimedya.unikazani.JSON.LoginJSON;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

public class Comment extends AppCompatActivity {


    private ArrayList<String> CommentTitle = new ArrayList<>();
    private ArrayList<String> CommentTime = new ArrayList<>();

    private EditText commentMsg;
    private Button commentSend;
    private ListView commentList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.comment_pop_up);

        commentSend = findViewById(R.id.comment_send);
        commentMsg = findViewById(R.id.comment_msg);
        commentList = findViewById(R.id.comment_list);

        fill();

        DisplayMetrics dm = new DisplayMetrics();
        getWindowManager().getDefaultDisplay().getMetrics(dm);

        int width = dm.widthPixels;
        int height = dm.heightPixels;

        getWindow().setLayout((int)(width*.8),(int)(height*.6));
    }

    private void fill()
    {
        CommentTitle.add("Merhaba arkadaşlar");
        CommentTitle.add("Merhaba arkadaşlar");
        CommentTitle.add("Merhaba arkadaşlar");
        CommentTitle.add("Merhaba arkadaşlar");

        CommentTime.add("10:10");
        CommentTime.add("10:10");
        CommentTime.add("10:10");
        CommentTime.add("10:10");


        commentList.setAdapter(new CommentAdapter(Comment.this, CommentTitle, CommentTime));

    }
}
