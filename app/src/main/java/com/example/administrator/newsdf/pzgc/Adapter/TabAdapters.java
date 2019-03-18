package com.example.administrator.newsdf.pzgc.Adapter;

import android.content.Context;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.example.administrator.newsdf.R;
import com.example.administrator.newsdf.pzgc.bean.Tab_fragment_item;
import com.example.administrator.newsdf.pzgc.utils.Dates;

import java.text.ParseException;
import java.util.ArrayList;

/**
 * 任务管理界面的适配器
 * Created by Administrator on 2017/12/12 0012.
 */

public class TabAdapters extends BaseAdapter {
    private Context mContext;
    private ArrayList<Tab_fragment_item> mData;
    private Dates dates = new Dates();
    String stsus;

    public TabAdapters(Context mContext) {
        this.mContext = mContext;
        mData = new ArrayList<>();
    }

    @Override
    public int getCount() {
        return mData.size();
    }

    @Override
    public Object getItem(int position) {
        return null;
    }

    @Override
    public long getItemId(int mData) {
        return mData;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder holder = null;
        if (convertView == null) {
            convertView = LayoutInflater.from(mContext).inflate(R.layout.item_grid_icon, parent, false);
            holder = new ViewHolder();
            //标段
            holder.tab_str = (TextView) convertView.findViewById(R.id.tab_str);
            holder.tab_content = (TextView) convertView.findViewById(R.id.tab_content);
            holder.tab_user = (TextView) convertView.findViewById(R.id.tab_user);
            holder.tab_time = (TextView) convertView.findViewById(R.id.tab_time);
            holder.tabFixedTiem = (TextView) convertView.findViewById(R.id.tab_fixed_tiem);
            holder.tab_view = (TextView) convertView.findViewById(R.id.tab_view);
            //将Holder存储到convertView中
            convertView.setTag(holder);
        } else {
            holder = (ViewHolder) convertView.getTag();
        }
        /**
         *
         *
         * 有状态未添加
         */
        //判断是否有消息
        if (mData.get(position).getStr().length() != 0) {
            holder.tab_str.setText(mData.get(position).getStr());
        } else {
            holder.tab_str.setText("主动上传任务");
        }
        holder.tab_content.setText(mData.get(position).getContent());
        holder.tab_user.setText("责任人：" + mData.get(position).getUser());
        String time = null;
        try {
            time = Dates.datato(mData.get(position).getTime());
        } catch (ParseException e) {
            e.printStackTrace();
        }
        holder.tab_view.setText(mData.get(position).getStatus1());
        stsus = mData.get(position).getStatus1();
        switch (stsus) {
            case "0":
                holder.tab_view.setText("未完成");
                holder.tab_view.setTextColor(Color.parseColor("#a6a6a6"));
                holder.tab_view.setBackgroundResource(R.drawable.tab_item_gray);
                holder.tab_time.setText("已推送： " + time);
                break;
            case "1":
                holder.tab_view.setText("已完成");
                holder.tab_view.setTextColor(Color.parseColor("#28c26A"));
                holder.tab_view.setBackgroundResource(R.drawable.tab_item_green);
                holder.tab_time.setText("时间：" + mData.get(position).getTime());
                break;
            case "2":
                holder.tab_view.setText("已完成");
                holder.tab_view.setTextColor(Color.parseColor("#28c26A"));
                holder.tab_view.setBackgroundResource(R.drawable.tab_item_green);
                holder.tab_time.setText("时间：" + mData.get(position).getTime());
                break;
            case "3":
                holder.tab_view.setText("打回");
                holder.tab_view.setTextColor(Color.parseColor("#FE0000"));
                holder.tab_view.setBackgroundResource(R.drawable.tab_item_red);
                holder.tab_time.setText("时间：" + mData.get(position).getTime());
                break;
            default:
                break;
        }
        return convertView;
    }

    static class ViewHolder {
        TextView tab_str;
        TextView tab_content;
        TextView tab_user;
        TextView tab_time;
        TextView tabFixedTiem;
        TextView tab_view;
    }

    public void getDate(ArrayList<Tab_fragment_item> mDate) {
        this.mData = mDate;
        notifyDataSetChanged();
    }

}
