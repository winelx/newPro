package com.example.administrator.newsdf.adapter;

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
 * @author winelx
 *         Created by Administrator on 2018/3/6 0006.
 */

public class PopAdapterDialog extends BaseAdapter implements ListAdapter {
    private ArrayList<String> mData;
    private Context mContext;
    private LayoutInflater inflater;

    public PopAdapterDialog(ArrayList<String> mData, Context mContext) {
        this.mData = mData;
        this.mContext = mContext;
    }

    @Override
    public int getCount() {
        return 0;
    }

    @Override
    public Object getItem(int position) {
        return null;
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        if (convertView == null) {
            convertView = inflater.inflate(R.layout.item_pop_duty, null);
        }
        TextView taskCheck = (TextView) convertView.findViewById(R.id.task_check);
        TextView taskContent = (TextView) convertView.findViewById(R.id.task_content);
        return convertView;
    }
}
