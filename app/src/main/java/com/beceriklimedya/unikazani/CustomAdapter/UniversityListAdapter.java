package com.beceriklimedya.unikazani.CustomAdapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.beceriklimedya.unikazani.MainScreen;
import com.beceriklimedya.unikazani.R;
import com.beceriklimedya.unikazani.UniversityProfile;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

import de.hdodenhof.circleimageview.CircleImageView;

public class UniversityListAdapter extends BaseAdapter {

    private ArrayList<String> MainArrayName;
    private ArrayList<String> MainArrayCategory;
    private ArrayList<String> MainArrayTime;
    private ArrayList<String> MainArrayText;
    private ArrayList<String> MainArrayImage;
    private ArrayList<String> MainArrayProfile;
    private ArrayList<String> MainArrayLike;
    private ArrayList<String> MainArrayHashTag;

    LayoutInflater layoutInflater = null;


    public UniversityListAdapter(UniversityProfile universityProfile,
                                 ArrayList<String> name,
                                 ArrayList<String> category,
                                 ArrayList<String> time,
                                 ArrayList<String> text,
                                 ArrayList<String> image,
                                 ArrayList<String> profile,
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
        this.MainArrayProfile = profile;


        layoutInflater = (LayoutInflater)universityProfile.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
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
        TextView txtCategory = RowView.findViewById(R.id.feed_item_category);
        TextView txtTime = RowView.findViewById(R.id.feed_item_time);
        TextView txtText = RowView.findViewById(R.id.feed_item_text);
        ImageView imgImage = RowView.findViewById(R.id.feed_item_image);
        TextView txtLike = RowView.findViewById(R.id.feed_item_like_count);
        TextView txtHashtag = RowView.findViewById(R.id.feed_item_hashtag);
        CircleImageView imgProfile = RowView.findViewById(R.id.feed_item_profile);

        txtName.setText(MainArrayName.get(position));
        txtCategory.setText(MainArrayCategory.get(position));
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
