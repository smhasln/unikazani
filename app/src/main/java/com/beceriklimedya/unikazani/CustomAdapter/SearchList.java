package com.beceriklimedya.unikazani.CustomAdapter;

import android.content.Context;
import android.media.Image;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.beceriklimedya.unikazani.MainScreen;
import com.beceriklimedya.unikazani.R;

public class SearchList extends BaseAdapter {

    String arrayName[];
    String arrayImage[];

    LayoutInflater layoutInflater = null;


    public SearchList(MainScreen mainScreen, String name[], String image[])
    {
        this.arrayName = name;
        this.arrayImage = image;

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
        View RowView = layoutInflater.inflate(R.layout.search_row,null);

        TextView txtName = RowView.findViewById(R.id.search_name);
        ImageView img = RowView.findViewById(R.id.search_img);

        txtName.setText(arrayName[position]);
        img.setImageResource(R.drawable.deneme2);

        return RowView;
    }
}
