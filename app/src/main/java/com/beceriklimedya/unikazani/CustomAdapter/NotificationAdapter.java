package com.beceriklimedya.unikazani.CustomAdapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.beceriklimedya.unikazani.Notifications;
import com.beceriklimedya.unikazani.R;

import java.sql.Time;
import java.util.ArrayList;

public class NotificationAdapter extends BaseAdapter {

    private ArrayList<String> NotificationArrayTitle;
    private ArrayList<String> NotificationArrayTime;

    LayoutInflater layoutInflater = null;


    public NotificationAdapter(Notifications notifications, ArrayList<String> name, ArrayList<String> time)
    {
        this.NotificationArrayTitle = name;
        this.NotificationArrayTime = time;

        layoutInflater = (LayoutInflater)notifications.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    }

    @Override
    public int getCount() {
        return NotificationArrayTime.size();
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
        View RowView = layoutInflater.inflate(R.layout.row_notifications,null);

        TextView title = RowView.findViewById(R.id.notificationTitle);
        TextView time = RowView.findViewById(R.id.notificationTime);

        title.setText(NotificationArrayTitle.get(position));
        time.setText(NotificationArrayTime.get(position));

        return RowView;
    }
}
