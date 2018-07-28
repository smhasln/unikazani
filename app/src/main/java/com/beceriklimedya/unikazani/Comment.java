package com.beceriklimedya.unikazani;

import android.content.Intent;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;

import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.toolbox.Volley;
import com.beceriklimedya.unikazani.CustomAdapter.CommentAdapter;
import com.beceriklimedya.unikazani.JSON.CommentAddJSON;
import com.beceriklimedya.unikazani.JSON.CommentJSON;
import com.squareup.picasso.Picasso;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

import de.hdodenhof.circleimageview.CircleImageView;

public class Comment extends AppCompatActivity {

    private ArrayList<String> CommentName = new ArrayList<>();
    private ArrayList<String> CommentUserId = new ArrayList<>();
    private ArrayList<String> CommentTitle = new ArrayList<>();
    private ArrayList<String> CommentTime = new ArrayList<>();
    private ArrayList<String> CommentPhoto = new ArrayList<>();

    private EditText commentMsg;
    private Button commentSend;
    private ListView commentList;

    private String feedId;
    private String userId;

    private CommentAdapter commentAdapter;


    CircleImageView commentphoto;
    TextView commentname;
    TextView commenttext;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.comment_pop_up);

        SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(getApplicationContext());
        userId = preferences.getString("userId", "0");

        Intent git = getIntent();
        feedId = git.getStringExtra("feedId");
        String feedPhoto = git.getStringExtra("feedPhoto");
        String feedName = git.getStringExtra("feedName");
        String feedText = git.getStringExtra("feedText");

        commentphoto = findViewById(R.id.commentPhoto);
        commentname = findViewById(R.id.commentname);
        commenttext = findViewById(R.id.commenttext);

        Picasso.get()
                .load("http://www.unikazani.com/json/upload/" + feedPhoto + ".jpg")
                .into(commentphoto);

        commentname.setText(feedName);
        commenttext.setText(feedText);

        commentSend = findViewById(R.id.comment_send);
        commentMsg = findViewById(R.id.comment_msg);
        commentList = findViewById(R.id.comment_list);

        commentSend.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                send();
            }
        });

        fill();
    }

    private void send()
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
                        commentMsg.setText("");

                        fill();
                    }
                }
                catch (JSONException e)
                {
                    e.printStackTrace();
                }
            }

        };

        CommentAddJSON loginrequest = new CommentAddJSON(feedId,userId,commentMsg.getText().toString(),responselistener);
        RequestQueue queue = Volley.newRequestQueue(Comment.this);
        queue.add(loginrequest);
    }

    private void fill()
    {
        CommentName.clear();
        CommentUserId.clear();
        CommentTitle.clear();
        CommentTime.clear();
        CommentPhoto.clear();

        Response.Listener<String> responselistener = new Response.Listener<String>()
        {
            @Override
            public void onResponse(String response) {
                try {

                    JSONObject jsonresponse = new JSONObject(response);

                    Log.i("yaz",response);
                    JSONArray names = jsonresponse.getJSONArray("names");
                    JSONArray user_id = jsonresponse.getJSONArray("user_id");
                    JSONArray comment = jsonresponse.getJSONArray("comment");
                    JSONArray comment_date = jsonresponse.getJSONArray("comment_date");
                    JSONArray comment_photo = jsonresponse.getJSONArray("photo");

                    for (int i = 0; i < names.length(); i++)
                    {
                        CommentName.add(i, names.get(i).toString());
                        CommentUserId.add(i, user_id.get(i).toString());
                        CommentTitle.add(i, comment.get(i).toString());
                        CommentTime.add(i, comment_date.get(i).toString());
                        CommentPhoto.add(i, comment_photo.get(i).toString());
                    }
                    commentAdapter = new CommentAdapter(Comment.this, CommentName ,CommentTitle, CommentTime, CommentPhoto);
                    commentList.setAdapter(commentAdapter);
                }
                catch (JSONException e)
                {
                    e.printStackTrace();
                }
            }

        };

        CommentJSON loginrequest = new CommentJSON(feedId,responselistener);
        RequestQueue queue = Volley.newRequestQueue(Comment.this);
        queue.add(loginrequest);


    }
}
