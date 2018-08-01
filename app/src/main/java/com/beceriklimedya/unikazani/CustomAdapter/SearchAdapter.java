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

    LayoutInflater layoutInflater = null;


    public SearchAdapter(Search search, ArrayList<String> name)
    {
        this.searchUniName = name;

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

        txtText.setText("Üniversite profiline gitmek için tıkla");

        txtName.setText(searchUniName.get(position));

        return RowView;
    }


}
