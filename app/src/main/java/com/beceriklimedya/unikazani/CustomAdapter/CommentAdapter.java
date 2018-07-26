package com.beceriklimedya.unikazani.CustomAdapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;

import com.beceriklimedya.unikazani.Comment;
import com.beceriklimedya.unikazani.Notifications;
import com.beceriklimedya.unikazani.R;

import java.util.ArrayList;

public class CommentAdapter extends BaseAdapter {

    private ArrayList<String> CommentTitle;
    private ArrayList<String> CommentTime;

    LayoutInflater layoutInflater = null;


    public CommentAdapter(Comment comment, ArrayList<String> title, ArrayList<String> time)
    {
        this.CommentTitle = title;
        this.CommentTime = time;

        layoutInflater = (LayoutInflater)comment.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    }

    @Override
    public int getCount() {
        return CommentTime.size();
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
        View RowView = layoutInflater.inflate(R.layout.row_comment,null);

        return RowView;
    }
}
