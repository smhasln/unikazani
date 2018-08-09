package com.beceriklimedya.unikazani.CustomAdapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.beceriklimedya.unikazani.MainScreen;
import com.beceriklimedya.unikazani.Profile;
import com.beceriklimedya.unikazani.R;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

public class ProfileListAdapter extends BaseAdapter {

    ArrayList<String> MainArrayName;
    ArrayList<String> MainArrayCategory;
    ArrayList<String> MainArrayTime;
    ArrayList<String> MainArrayText;
    ArrayList<String> MainArrayImage;
    ArrayList<String> MainArrayProfile;
    ArrayList<String> MainArrayLike;
    ArrayList<String> MainArrayHashTag;

    LayoutInflater layoutInflater = null;


    public ProfileListAdapter(Profile profile,
                       ArrayList<String> name,
                       ArrayList<String> category,
                       ArrayList<String> time,
                       ArrayList<String> text,
                       ArrayList<String> image,
                       ArrayList<String> profil,
                       ArrayList<String> like,
                       ArrayList<String> hashtag)
    {
        this.MainArrayName = name;
        this.MainArrayCategory = category;
        this.MainArrayTime = time;
        this.MainArrayText = text;
        this.MainArrayImage = image;
        this.MainArrayLike = like;
        this.MainArrayHashTag = hashtag;
        this.MainArrayProfile = profil;


        layoutInflater = (LayoutInflater)profile.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    }

    @Override
    public int getCount() {
        return MainArrayName.size();
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
        View RowView = layoutInflater.inflate(R.layout.row_main,null);

        TextView txtName = RowView.findViewById(R.id.feed_item_name);
        TextView txtTime = RowView.findViewById(R.id.feed_item_time);
        TextView txtText = RowView.findViewById(R.id.feed_item_text);
        ImageView imgImage = RowView.findViewById(R.id.feed_item_image);
        TextView txtLike = RowView.findViewById(R.id.feed_item_like_count);
        TextView txtHashtag = RowView.findViewById(R.id.feed_item_hashtag);
        ImageView imgProfile = RowView.findViewById(R.id.feed_item_profile);

        txtName.setText(MainArrayName.get(position));
        txtTime.setText(MainArrayTime.get(position));
        txtText.setText(MainArrayText.get(position));
        txtLike.setText(MainArrayLike.get(position));
        txtHashtag.setText(MainArrayHashTag.get(position));

        Picasso.get()
                .load("http://www.unikazani.com/json/upload/" + MainArrayProfile.get(position) + ".jpg")
                .into(imgProfile);

        if (MainArrayImage.get(position).equals("0"))
        {
            imgImage.setVisibility(View.GONE);

        }
        else
        {
            imgImage.setVisibility(View.VISIBLE);
            Picasso.get()
                    .load("http://www.unikazani.com/json/upload/" + MainArrayImage.get(position) + ".jpg")
                    .into(imgImage);
        }




        return RowView;
    }
}
