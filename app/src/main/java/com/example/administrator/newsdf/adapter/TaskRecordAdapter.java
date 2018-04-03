package com.example.administrator.newsdf.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.example.administrator.newsdf.R;
import com.example.administrator.newsdf.bean.Tenanceview;

import java.util.ArrayList;

/**
 * Created by Administrator on 2018/4/3 0003.
 */

public class TaskRecordAdapter extends BaseAdapter {
    private Context mContext;
    private ArrayList<Tenanceview> list;

    public TaskRecordAdapter(Context mContext) {
        this.mContext = mContext;
        list = new ArrayList<>();
    }

    @Override
    public int getCount() {
        return list.size();
    }

    @Override
    public Object getItem(int position) {
        return position;
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder holder = null;
        if (convertView == null) {
            convertView = LayoutInflater.from(mContext).inflate(R.layout.taskrecord_item, parent, false);
            holder = new ViewHolder();
            holder.item_name = convertView.findViewById(R.id.task_cord_name);
            holder.item_number = convertView.findViewById(R.id.task_cord_number);
            convertView.setTag(holder);
        } else {
            holder = (ViewHolder) convertView.getTag();
        }
        holder.item_name.setText(list.get(position).getName());
        holder.item_number.setText(list.get(position).getUnmber());
        return convertView;
    }


    static class ViewHolder {
        TextView item_name;
        TextView item_number;

    }

    public void getData(ArrayList<Tenanceview> list) {
        this.list = list;
        notifyDataSetChanged();
    }
}
