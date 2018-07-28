package com.beceriklimedya.unikazani.CustomAdapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.beceriklimedya.unikazani.Comment;
import com.beceriklimedya.unikazani.R;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

import de.hdodenhof.circleimageview.CircleImageView;

public class CommentAdapter extends BaseAdapter {

    private ArrayList<String> CommentName;
    private ArrayList<String> CommentTitle;
    private ArrayList<String> CommentTime;
    private ArrayList<String> CommentPhoto;

    LayoutInflater layoutInflater = null;


    public CommentAdapter(Comment comment, ArrayList<String> name, ArrayList<String> title, ArrayList<String> time, ArrayList<String> photo)
    {
        this.CommentTitle = title;
        this.CommentTime = time;
        this.CommentName = name;
        this.CommentPhoto = photo;

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

        TextView name = RowView.findViewById(R.id.commentName);
        TextView desc = RowView.findViewById(R.id.commentDesc);
        TextView time = RowView.findViewById(R.id.commentTime);
        CircleImageView photo = RowView.findViewById(R.id.commentPhoto);

        name.setText(CommentName.get(position));
        desc.setText(CommentTitle.get(position));
        time.setText(CommentTime.get(position));

        Picasso.get()
                .load("http://www.unikazani.com/json/upload/" + CommentPhoto.get(position) + ".jpg")
                .into(photo);

        return RowView;
    }
}
