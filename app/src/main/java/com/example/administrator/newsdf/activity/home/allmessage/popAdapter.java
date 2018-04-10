package com.example.administrator.newsdf.activity.home.allmessage;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ListAdapter;
import android.widget.TextView;

import com.example.administrator.newsdf.R;

import java.util.ArrayList;

/**
 * Created by Administrator on 2018/4/10 0010.
 */

public class popAdapter extends BaseAdapter implements ListAdapter {


    private ArrayList<String> list;
    private LayoutInflater inflater;

    public popAdapter(ArrayList<String> list, Context context) {
        super();
        this.list = list;
        this.inflater = LayoutInflater.from(context);
    }

    @Override
    public int getCount() {
        return list.size();
    }


    @Override
    public Object getItem(int position) {
        return list.get(position);
    }


    @Override
    public long getItemId(int position) {
        return position;
    }


    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        if (convertView == null) {
            convertView = inflater.inflate(R.layout.item_pop, null);
        }
        TextView tv_item = (TextView) convertView.findViewById(R.id.tv_item);
        tv_item.setText(list.get(position));

        return convertView;
    }
}