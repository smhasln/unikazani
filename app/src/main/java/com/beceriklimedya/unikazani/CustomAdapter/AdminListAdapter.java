package com.beceriklimedya.unikazani.CustomAdapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.beceriklimedya.unikazani.Admin;
import com.beceriklimedya.unikazani.R;
import com.beceriklimedya.unikazani.Top10;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

import de.hdodenhof.circleimageview.CircleImageView;

public class AdminListAdapter extends BaseAdapter {

    private ArrayList<String> names = new ArrayList<>();
    private ArrayList<String> profiles = new ArrayList<>();
    private ArrayList<String> auths = new ArrayList<>();

    LayoutInflater layoutInflater = null;

    public AdminListAdapter(Admin admin, ArrayList<String> name, ArrayList<String> profile, ArrayList<String> auth)
    {
        this.names = name;
        this.profiles = profile;
        this.auths = auth;

        layoutInflater = (LayoutInflater)admin.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
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
        View RowView = layoutInflater.inflate(R.layout.custom_admin_list,null);

        TextView txtName = RowView.findViewById(R.id.admin_name);
        TextView txtAuth = RowView.findViewById(R.id.admin_auth);
        CircleImageView image = RowView.findViewById(R.id.admin_img);

        txtName.setText(names.get(position));
        txtAuth.setText(auths.get(position));

        Picasso.get().load(profiles.get(position)).resize(100,100).into(image);

        return RowView;
    }


}
