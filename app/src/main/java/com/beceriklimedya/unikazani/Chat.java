package com.beceriklimedya.unikazani;

import android.content.SharedPreferences;
import android.preference.PreferenceManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;

import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.toolbox.Volley;
import com.beceriklimedya.unikazani.CustomAdapter.ChatAdapter;
import com.beceriklimedya.unikazani.CustomAdapter.Top10Adapter;
import com.beceriklimedya.unikazani.JSON.Top10JSON;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

public class Chat extends AppCompatActivity {

    private ArrayList<String> ChatImg = new ArrayList<>();
    private ArrayList<String> ChatMsg = new ArrayList<>();
    private ArrayList<String> ChatState = new ArrayList<>();

    private TextView title;
    private EditText msg;
    private Button send;
    private ListView listView;

    private String userId;

    private ChatAdapter chatAdapter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chat);

        listView = findViewById(R.id.chat_list);
        title = findViewById(R.id.chat_title);
        msg = findViewById(R.id.chat_message);
        send = findViewById(R.id.chat_sendbutton);

        SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(getApplicationContext());
        userId = preferences.getString("userId", "N/A");

        getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_HIDDEN);

        send.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                add(msg.getText().toString().trim());
            }
        });


        fill();
    }

    private void add(String message)
    {

        ChatMsg.add(message);
        ChatState.add("0");
        ChatImg.add("78845366443");

        chatAdapter.notifyDataSetChanged();

        msg.setText("");

        listView.post(new Runnable(){
            public void run() {
                listView.setSelection(listView.getCount() - 1);}});
    }


    private void fill()
    {
        ChatMsg.add("Selam");
        ChatMsg.add("adjsfasdfjhadsjkfhadsfsdjfadjskfdasf");
        ChatMsg.add("Selam");
        ChatMsg.add("Selasdfasdfadsfjadhsfkadsfam");
        ChatMsg.add("Selam");
        ChatMsg.add("Selam");

        ChatState.add("0");
        ChatState.add("1");
        ChatState.add("1");
        ChatState.add("0");
        ChatState.add("1");
        ChatState.add("0");

        ChatImg.add("78845366443");
        ChatImg.add("78845366443");
        ChatImg.add("78845366443");
        ChatImg.add("78845366443");
        ChatImg.add("78845366443");
        ChatImg.add("78845366443");

        chatAdapter = new ChatAdapter(this,ChatImg,ChatMsg,ChatState);
        listView.setAdapter(chatAdapter);

        listView.post(new Runnable(){
            public void run() {
                listView.setSelection(listView.getCount() - 1);}});


    }

}
