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

public class MainList extends BaseAdapter {

    String arrayName[];
    String arrayCategory[];
    String arrayTime[];
    String arrayText[];
    String arrayImage[];
    String arrayProfile[];
    String arrayLike[];
    String arrayHashTag[];

    LayoutInflater layoutInflater = null;


    public MainList(MainScreen mainScreen,
                    String name[],
                    String category[],
                    String time[],
                    String text[],
                    String image[],
                    String profile[],
                    String like[],
                    String hashtag[])
    {
        this.arrayName = name;
        this.arrayCategory = category;
        this.arrayTime = time;
        this.arrayText = text;
        this.arrayImage = image;
        this.arrayLike = like;
        this.arrayHashTag = hashtag;
        this.arrayProfile = profile;


        layoutInflater = (LayoutInflater)mainScreen.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    }

    @Override
    public int getCount() {
        return arrayName.length;
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
        View RowView = layoutInflater.inflate(R.layout.feed_row,null);

        TextView txtName = RowView.findViewById(R.id.feed_item_name);
        TextView txtCategory = RowView.findViewById(R.id.feed_item_category);
        TextView txtTime = RowView.findViewById(R.id.feed_item_time);
        TextView txtText = RowView.findViewById(R.id.feed_item_text);
        ImageView imgImage = RowView.findViewById(R.id.feed_item_image);
        TextView txtLike = RowView.findViewById(R.id.feed_item_like_count);
        TextView txtHashtag = RowView.findViewById(R.id.feed_item_hashtag);
        ImageView imgProfile = RowView.findViewById(R.id.feed_item_profile);

        txtName.setText(arrayName[position]);
        txtCategory.setText(arrayCategory[position]);
        txtTime.setText(arrayTime[position]);
        txtText.setText(arrayText[position]);
        txtLike.setText(arrayLike[position]);
        txtHashtag.setText(arrayHashTag[position]);

        imgImage.setImageResource(R.drawable.deneme2);
        imgProfile.setImageResource(R.drawable.deneme);

        return RowView;
    }
}
