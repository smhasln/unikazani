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
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.toolbox.Volley;
import com.beceriklimedya.unikazani.CustomAdapter.CommentAdapter;
import com.beceriklimedya.unikazani.JSON.CommentAddJSON;
import com.beceriklimedya.unikazani.JSON.CommentJSON;
import com.beceriklimedya.unikazani.JSON.LikeJSON;
import com.beceriklimedya.unikazani.JSON.NotificationSend;
import com.kaopiz.kprogresshud.KProgressHUD;
import com.squareup.picasso.Picasso;
import com.varunest.sparkbutton.SparkButton;
import com.varunest.sparkbutton.SparkEventListener;

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

    private Integer like;

    private SparkButton sparkButton;
    private CommentAdapter commentAdapter;

    CircleImageView commentphoto;
    TextView commentname;
    TextView commenttext;

    String feedUserId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.comment_pop_up);

        SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(getApplicationContext());
        userId = preferences.getString("userId", "0");

        Intent git = getIntent();
        feedId = git.getStringExtra("feedId");
        feedUserId = git.getStringExtra("feedUserId");

        String feedPhoto = git.getStringExtra("feedPhoto");
        String feedName = git.getStringExtra("feedName");
        String feedText = git.getStringExtra("feedText");

        Button goProfile = findViewById(R.id.commentbutton);
        goProfile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent go = new Intent(Comment.this,Profile.class);
                go.putExtra("feedUserId", feedUserId);
                startActivity(go);

            }
        });

        sparkButton = findViewById(R.id.spark_button);
        commentphoto = findViewById(R.id.commentPhoto);
        commentname = findViewById(R.id.commentname);
        commenttext = findViewById(R.id.commenttext);

        if (feedName.equals("Anonim"))
        {
            goProfile.setVisibility(View.GONE);
        }

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


        if (feedUserId.equals(userId))
        {
            sparkButton.setVisibility(View.GONE);
        }

        sparkButton.setEventListener(new SparkEventListener() {
            @Override
            public void onEvent(ImageView button, boolean buttonState) {
                if (buttonState)
                {
                    like_appy();
                }
                else
                {
                    like_appy();
                }
            }

            @Override
            public void onEventAnimationEnd(ImageView button, boolean buttonState) {

            }

            @Override
            public void onEventAnimationStart(ImageView button, boolean buttonState) {

            }
        });


    }

    private void like_appy()
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

                    hud.dismiss();

                    if (like == 1)
                    {
                        sparkButton.setChecked(true);
                        sparkButton.playAnimation();
                    }
                    else
                    {
                        sparkButton.setChecked(false);
                        sparkButton.playAnimation();
                    }

                }
                catch (JSONException e)
                {
                    e.printStackTrace();
                }
            }

        };

        LikeJSON loginrequest = new LikeJSON(userId,feedId,responselistener);
        RequestQueue queue = Volley.newRequestQueue(Comment.this);
        queue.add(loginrequest);
    }

    private void notification_send()
    {
        Response.Listener<String> responselistener = new Response.Listener<String>()
        {
            @Override
            public void onResponse(String response) {
                try {

                    JSONObject jsonresponse = new JSONObject(response);
                }
                catch (JSONException e)
                {
                    e.printStackTrace();
                }
            }

        };

        NotificationSend loginrequest = new NotificationSend(feedUserId,userId,commentMsg.getText().toString(),responselistener);
        RequestQueue queue = Volley.newRequestQueue(Comment.this);
        queue.add(loginrequest);
    }

    private void send()
    {
        final KProgressHUD hud = KProgressHUD.create(this)
                .setStyle(KProgressHUD.Style.SPIN_INDETERMINATE)
                .setCancellable(false)
                .setLabel("Paylaşılıyor");

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
                        notification_send();
                        fill();
                        commentMsg.setText("");

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
        final KProgressHUD hud = KProgressHUD.create(this)
            .setStyle(KProgressHUD.Style.SPIN_INDETERMINATE)
            .setCancellable(false)
            .setLabel("Yükleniyor")
            .setDetailsLabel("Paylaşımlar getiriliyor...");

        hud.show();

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

                    JSONArray names = jsonresponse.getJSONArray("names");
                    JSONArray user_id = jsonresponse.getJSONArray("user_id");
                    JSONArray comment = jsonresponse.getJSONArray("comment");
                    JSONArray comment_date = jsonresponse.getJSONArray("comment_date");
                    JSONArray comment_photo = jsonresponse.getJSONArray("photo");
                    like = jsonresponse.getInt("like");

                    if (like == 1)
                    {
                        sparkButton.setChecked(true);
                        sparkButton.playAnimation();
                    }
                    else
                    {
                        sparkButton.setChecked(false);
                        sparkButton.playAnimation();
                    }

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

                    hud.dismiss();
                }
                catch (JSONException e)
                {
                    e.printStackTrace();
                }
            }

        };

        CommentJSON loginrequest = new CommentJSON(feedId,userId,responselistener);
        RequestQueue queue = Volley.newRequestQueue(Comment.this);
        queue.add(loginrequest);


    }
}
