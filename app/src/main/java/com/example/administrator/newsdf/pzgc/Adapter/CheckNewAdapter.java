package com.example.administrator.newsdf.pzgc.Adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.administrator.newsdf.R;

import java.util.ArrayList;

/**
 * Created by Administrator on 2018/8/6 0006.
 */

public class CheckNewAdapter extends BaseAdapter {

    private LayoutInflater inflater;
    private Context mContext;
    private ArrayList<String> imagePaths;

    public CheckNewAdapter(Context mContext, ArrayList<String> imagePaths) {
        this.mContext = mContext;
        this.imagePaths= imagePaths;
        this.inflater = LayoutInflater.from(mContext);
    }

    @Override
    public int getCount() {
        return imagePaths.size();
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
    public View getView(final int position, View convertView, ViewGroup parent) {
        ViewHolder holder = null;
        if (convertView == null) {
            convertView = inflater.inflate(R.layout.check_new_grid_item, null);
            holder = new ViewHolder();
            holder.pop_tast_item = convertView.findViewById(R.id.text_item);
            holder.LabelView = convertView.findViewById(R.id.angleofthe);
            //将Holder存储到convertView中
            convertView.setTag(holder);
        } else {
            holder = (ViewHolder) convertView.getTag();
        }
        holder.pop_tast_item.setText(imagePaths.get(position));

        return convertView;
    }

    class ViewHolder {
        TextView pop_tast_item;
        ImageView LabelView;
    }

}
