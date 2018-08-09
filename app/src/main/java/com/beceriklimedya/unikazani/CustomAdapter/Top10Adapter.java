package com.beceriklimedya.unikazani.CustomAdapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.beceriklimedya.unikazani.R;
import com.beceriklimedya.unikazani.Search;
import com.beceriklimedya.unikazani.Top10;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

import de.hdodenhof.circleimageview.CircleImageView;

public class Top10Adapter extends BaseAdapter {


    private ArrayList<String> names = new ArrayList<>();
    private ArrayList<String> profiles = new ArrayList<>();
    private ArrayList<String> ranks = new ArrayList<>();
    private ArrayList<String> scores = new ArrayList<>();

    LayoutInflater layoutInflater = null;

    public Top10Adapter(Top10 top10, ArrayList<String> name, ArrayList<String> profile, ArrayList<String> rank, ArrayList<String> score)
    {
        this.names = name;
        this.profiles = profile;
        this.ranks = rank;
        this.scores = score;

        layoutInflater = (LayoutInflater)top10.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    }

    @Override
    public int getCount() {
        return names.size();
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
        View RowView = layoutInflater.inflate(R.layout.custom_rank_list,null);

        TextView txtName = RowView.findViewById(R.id.rank_name);
        TextView txtRank = RowView.findViewById(R.id.rank_rank);
        TextView txtScore = RowView.findViewById(R.id.rank_score);

        CircleImageView image = RowView.findViewById(R.id.rank_image);

        txtName.setText(names.get(position));
        txtRank.setText(ranks.get(position));
        txtScore.setText(scores.get(position));

        Picasso.get().load("http://www.unikazani.com/json/upload/" + profiles.get(position) + ".jpg").resize(100,100).into(image);

        return RowView;
    }


}
