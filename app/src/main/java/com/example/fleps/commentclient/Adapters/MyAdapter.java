package com.example.fleps.commentclient.Adapters;

import android.app.Activity;
import android.content.ContentResolver;
import android.content.Context;
import android.graphics.Color;
import android.provider.Settings;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.BaseAdapter;
import android.widget.CheckBox;
import android.widget.TextView;

import com.example.fleps.commentclient.Model.Comment;
import com.example.fleps.commentclient.R;

import java.util.ArrayList;

/**
 * Created by Fleps_000 on 04.07.2015.
 */
public class MyAdapter extends ArrayAdapter<Comment> {

    private final ArrayList<Comment> comments;
    private final Activity context;

    public MyAdapter(Activity context, ArrayList<Comment> comments) {
        super(context, R.layout.activity_all_comments, comments);
        this.comments = comments;
        this.context = context;
    }

    static class ViewHolder {
        protected TextView nameTW, surnameTW, commentTW;
    }

    @Override
    public View getView(int i, View convertView, ViewGroup viewGroup) {
        View rowView = convertView;
        final ViewHolder holder;
        if (rowView == null) {
            LayoutInflater inflater = context.getLayoutInflater();
            rowView = inflater.inflate(R.layout.list_view_item, null, true);
            holder = new ViewHolder();
            holder.nameTW = (TextView) rowView.findViewById(R.id.nameTW);
            holder.surnameTW = (TextView) rowView.findViewById(R.id.surnameTW);
            holder.commentTW = (TextView) rowView.findViewById(R.id.commentTW);
            rowView.setTag(holder);
        } else {
            holder = (ViewHolder) rowView.getTag();
        }

        holder.nameTW.setText(comments.get(i).getName());
        holder.surnameTW.setText(comments.get(i).getSurname());
        holder.commentTW.setText(comments.get(i).getComment());
        String id = Settings.Secure.getString(context.getContentResolver(), Settings.Secure.ANDROID_ID);
        //выделяем свои комментарии светло-синим, а чужие - темным
        if (id.equals(comments.get(i).getDeviceID())) rowView.setBackgroundColor(Color.parseColor("#ddf5fc"));
        else rowView.setBackgroundColor(Color.parseColor("#32A1CC"));
        return rowView;
    }
}
