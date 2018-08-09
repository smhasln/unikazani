package com.beceriklimedya.unikazani.CustomAdapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.beceriklimedya.unikazani.PersonelList;
import com.beceriklimedya.unikazani.R;
import com.beceriklimedya.unikazani.Top10;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

import de.hdodenhof.circleimageview.CircleImageView;

public class PersonelAdapter extends BaseAdapter {

    ArrayList<String> names = new ArrayList<>();
    ArrayList<String> photos = new ArrayList<>();
    ArrayList<String> auths = new ArrayList<>();
    ArrayList<String> unis = new ArrayList<>();

    LayoutInflater layoutInflater = null;

    public PersonelAdapter(PersonelList personelList, ArrayList<String> name, ArrayList<String> img, ArrayList<String> auth, ArrayList<String> uni)
    {
        this.names = name;
        this.photos = img;
        this.auths = auth;
        this.unis = uni;

        layoutInflater = (LayoutInflater)personelList.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
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
        View RowView = layoutInflater.inflate(R.layout.custom_personel_list,null);

        TextView txtName = RowView.findViewById(R.id.personel_name);
        TextView txtUni = RowView.findViewById(R.id.personel_uni);
        TextView txtAuth = RowView.findViewById(R.id.personel_auth);

        CircleImageView image = RowView.findViewById(R.id.personel_img);

        txtName.setText(names.get(position));
        txtUni.setText(unis.get(position));

        txtAuth.setText(auths.get(position));


        Picasso.get().load("http://www.unikazani.com/json/upload/" + photos.get(position) + ".jpg").resize(100,100).into(image);

        return RowView;
    }


}
