package com.beceriklimedya.unikazani.CustomAdapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.beceriklimedya.unikazani.Chat;
import com.beceriklimedya.unikazani.Comment;
import com.beceriklimedya.unikazani.R;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

import de.hdodenhof.circleimageview.CircleImageView;

public class ChatAdapter extends BaseAdapter {

    private ArrayList<String> ChatImg;
    private ArrayList<String> ChatMsg;
    private ArrayList<String> ChatState;

    LayoutInflater layoutInflater = null;


    public ChatAdapter(Chat chat, ArrayList<String> img, ArrayList<String> msg, ArrayList<String> state)
    {
        this.ChatImg = img;
        this.ChatMsg = msg;
        this.ChatState = state;

        layoutInflater = (LayoutInflater)chat.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    }

    @Override
    public int getCount() {
        return ChatMsg.size();
    }

    @Override
    public Object getItem(int i) {
        return null;
    }

    @Override
    public long getItemId(int i) {
        return 0;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup viewGroup) {
        View RowView = layoutInflater.inflate(R.layout.chat_balon,null);

        CircleImageView gelenImg = RowView.findViewById(R.id.receiveImg);
        TextView gelenMsg = RowView.findViewById(R.id.receiveMsg);

        CircleImageView gidenImg = RowView.findViewById(R.id.sendImg);
        TextView gidenMsg = RowView.findViewById(R.id.sendMsg);

        if (ChatState.get(position).equals("0"))
        {

            gidenMsg.setText(ChatMsg.get(position));
            Picasso.get()
                    .load("http://www.unikazani.com/json/upload/" + ChatImg.get(position) + ".jpg")
                    .into(gidenImg);

            gelenImg.setVisibility(View.GONE);
            gelenMsg.setVisibility(View.GONE);
        }
        else
        {
            gelenMsg.setText(ChatMsg.get(position));
            Picasso.get()
                    .load("http://www.unikazani.com/json/upload/" + ChatImg.get(position) + ".jpg")
                    .into(gelenImg);

            gidenImg.setVisibility(View.GONE);
            gidenMsg.setVisibility(View.GONE);
        }


        return RowView;
    }
}
