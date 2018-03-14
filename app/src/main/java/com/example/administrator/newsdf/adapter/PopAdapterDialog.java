package com.example.administrator.newsdf.adapter;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.LinearLayout;
import android.widget.ListAdapter;
import android.widget.TextView;

import com.example.administrator.newsdf.R;
import com.example.administrator.newsdf.bean.CasePointsBean;

import java.util.ArrayList;

/**
 * @author winelx
 *         Created by Administrator on 2018/3/6 0006.
 */

public class PopAdapterDialog extends BaseAdapter implements ListAdapter {
    private ArrayList<CasePointsBean> mData;
    private Context mContext;
    private LayoutInflater inflater;
    private String preconditions;
    public PopAdapterDialog(ArrayList<CasePointsBean> mData, Context mContext,String preconditions) {
        this.mData = mData;
        this.mContext = mContext;
        this.inflater = LayoutInflater.from(mContext);
        this.preconditions = preconditions;
    }

    @Override
    public int getCount() {
        return mData.size();
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
        if (convertView == null) {
            convertView = inflater.inflate(R.layout.item_pop_duty, null);
        }
        LinearLayout chek_Lin = convertView.findViewById(R.id.chek_Lin);
        TextView taskCheck = (TextView) convertView.findViewById(R.id.task_check);
        TextView taskContent = (TextView) convertView.findViewById(R.id.task_content);

            String  str=mData.get(position).getID();
            if (preconditions.equals(str)){
                taskCheck.setText(mData.get(position).getLabel());
                taskContent.setText(mData.get(position).getContent());
                taskCheck.setTextColor(mContext.getResources().getColor(R.color.Orange));
                taskContent.setTextColor(mContext.getResources().getColor(R.color.Orange));
            }else {
                taskCheck.setText(mData.get(position).getLabel());
                taskContent.setText(mData.get(position).getContent());
                taskCheck.setTextColor(mContext.getResources().getColor(R.color.textViwe));
                taskContent.setTextColor(mContext.getResources().getColor(R.color.textViwe));
            }


        return convertView;
    }
}
