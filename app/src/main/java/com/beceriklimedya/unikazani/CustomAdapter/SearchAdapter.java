package com.beceriklimedya.unikazani.CustomAdapter;

import android.content.Context;
import android.graphics.Color;
import android.support.v4.content.ContextCompat;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.beceriklimedya.unikazani.R;
import com.beceriklimedya.unikazani.Search;

import java.util.ArrayList;

public class SearchAdapter extends BaseAdapter {

    private ArrayList<String> searchUniName;
    private ArrayList<String> searchUniStatus;
    private ArrayList<String> searchUniId;

    LayoutInflater layoutInflater = null;


    public SearchAdapter(Search search, ArrayList<String> name, ArrayList<String> status, ArrayList<String> searchUniId)
    {
        this.searchUniName = name;
        this.searchUniStatus = status;
        this.searchUniId = searchUniId;

        layoutInflater = (LayoutInflater)search.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    }

    @Override
    public int getCount() {
        return searchUniName.size();
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
    public View getView(final int position, View convertView, ViewGroup viewGroup) {
        View RowView = layoutInflater.inflate(R.layout.row_search,null);

        TextView txtName = RowView.findViewById(R.id.search_name);
        TextView txtText = RowView.findViewById(R.id.search_text);

        if (searchUniStatus.get(position).equals("0"))
        {
            txtText.setText("Gönderileri görmek için dokun ve takibe al!");
            txtText.setTextColor(Color.parseColor("#FF4B2EDA"));
        }
        else
        {
            txtText.setText("Gönderileri görmek istemiyorsan dokun ve takipten çık!");
            txtText.setTextColor(Color.parseColor("#FFFF4081"));
        }

        txtName.setText(searchUniName.get(position));

        return RowView;
    }


}
